package com.presidium.analyzer.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import com.presidium.analyzer.actors.protocol.RuleActorCommand;
import com.presidium.analyzer.analysis.AnalysisCache;
import com.presidium.analyzer.rule.Rule;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands.AnalysisResult;
import static com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands.StartAnalysing;

@Slf4j
public class DataAnalysisActor extends AbstractBehavior<DataAnalysisActorCommands> {

    private static final EnumSet<Rule> rules = EnumSet.allOf(Rule.class);
    private Map<String, AnalysisCache> cache = new HashMap<>();
    private ActorRef<RuleActorCommand> ruleWorkers;

    public static Behavior<DataAnalysisActorCommands> create() {
        return Behaviors.setup(DataAnalysisActor::new);
    }

    public DataAnalysisActor(ActorContext<DataAnalysisActorCommands> context) {
        super(context);
        log.info(getClass().getSimpleName() + " started");
        PoolRouter<RuleActorCommand> pool = Routers.pool(3, RuleActor.create());
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
        AnalysisCache analysisCache = cache.get(msg.getId());
        analysisCache.getAnalysisResult().put(msg.getRuleName(), msg.getResult());
        if (analysisCache.getAnalysisResult().keySet().containsAll(Arrays.asList(Rule.values()))) {
            //todo finish
        } else {
            //todo continue
        }

        return this;
    }

    private Behavior<DataAnalysisActorCommands> startAnalysis(StartAnalysing msg) {
        log.info("Received data from file " + msg.getFilename());
        //todo add to cache
        ruleWorkers.tell(new RuleActorCommand.AnalyzeMessage("todo", msg.getText(), getContext().getSelf(), Rule.LETTER_A_OCCURRENCE));
        return this;
    }
}
