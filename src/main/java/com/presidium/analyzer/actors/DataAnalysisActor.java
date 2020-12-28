package com.presidium.analyzer.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import com.presidium.analyzer.actors.protocol.RuleActorCommand;
import com.presidium.analyzer.analysis.AnalysisCache;
import com.presidium.analyzer.rule.DependentRule;
import com.presidium.analyzer.rule.IndependentRule;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands.AnalysisResult;
import static com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands.StartAnalysing;

@Slf4j
public class DataAnalysisActor extends AbstractBehavior<DataAnalysisActorCommands> {

    private final static Integer independentRulesSize = IndependentRule.values().length;
    private final static Integer allRulesSize = IndependentRule.values().length  + DependentRule.values().length;
    private final Map<String, AnalysisCache> results = new HashMap<>();
    private final ActorRef<RuleActorCommand> ruleWorkers;

    public static Behavior<DataAnalysisActorCommands> create() {
        return Behaviors.setup(DataAnalysisActor::new);
    }

    public DataAnalysisActor(ActorContext<DataAnalysisActorCommands> context) {
        super(context);
        log.info(getClass().getSimpleName() + " started");
        PoolRouter<RuleActorCommand> pool = Routers.pool(6, RuleActor.create());
        ruleWorkers = context.spawn(pool, "ruleWorkers");
    }

    @Override
    public Receive<DataAnalysisActorCommands> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartAnalysing.class, this::startAnalysis)
                .onMessage(AnalysisResult.class, this::processAnalysisResults)
                .build();
    }

    private Behavior<DataAnalysisActorCommands> processAnalysisResults(AnalysisResult msg) {
        AnalysisCache analysisCache = this.results.get(msg.getFileName());
        analysisCache.getAnalysisResult().put(msg.getRuleName(), msg.getResult());
        int resultSize = analysisCache.getAnalysisResult().keySet().size();
        if (resultSize < independentRulesSize) {
            String notProcessedRuleName = IndependentRule.getListOfRuleNames().stream()
                    .filter(x -> !analysisCache.getAnalysisResult().containsKey(x))
                    .findAny()
                    .orElseThrow();
            IndependentRule nextRule = IndependentRule.valueOf(notProcessedRuleName);
            ruleWorkers.tell(new RuleActorCommand.AnalyzeIndependentRuleMessage(msg.getFileName(), analysisCache.getText(), getContext().getSelf(), nextRule));
        } else if (resultSize < allRulesSize){
            String notProcessedRuleName = DependentRule.getListOfRuleNames().stream()
                    .filter(x -> !analysisCache.getAnalysisResult().containsKey(x))
                    .findAny()
                    .orElseThrow();
            DependentRule nextRule = DependentRule.valueOf(notProcessedRuleName);

            ruleWorkers.tell(new RuleActorCommand.AnalyzeDependentRuleMessage(msg.getFileName(), getContext().getSelf(), analysisCache, nextRule));

        } else {
            log.info("finished analysis for {} with results: \n {}", msg.getFileName(), analysisCache.getAnalysisResult());
        }

        return this;
    }

    private Behavior<DataAnalysisActorCommands> startAnalysis(StartAnalysing msg) {
        log.info("Received data from file " + msg.getFilename());
        results.put(msg.getFilename(), new AnalysisCache(msg.getText(), new HashMap<>()));
        ruleWorkers.tell(new RuleActorCommand.AnalyzeIndependentRuleMessage(msg.getFilename(), msg.getText(), getContext().getSelf(), IndependentRule.LETTER_A_OCCURRENCE));
        return this;
    }
}
