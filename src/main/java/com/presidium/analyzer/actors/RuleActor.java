package com.presidium.analyzer.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import com.presidium.analyzer.actors.protocol.RuleActorCommand;
import com.presidium.analyzer.rule.DependentRule;
import com.presidium.analyzer.rule.IndependentRule;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Slf4j
public class RuleActor extends AbstractBehavior<RuleActorCommand> {

    public static Behavior<RuleActorCommand> create(){
        return Behaviors.setup(RuleActor::new);
    }

    private RuleActor(ActorContext<RuleActorCommand> context) {
        super(context);
        log.info("Created " + getClass().getSimpleName());
    }

    @Override
    public Receive<RuleActorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleActorCommand.AnalyzeIndependentRuleMessage.class, this::analyzeIndependent)
                .onMessage(RuleActorCommand.AnalyzeDependentRuleMessage.class, this::analyzeDependent)
                .build();
    }

    private Behavior<RuleActorCommand> analyzeDependent(RuleActorCommand.AnalyzeDependentRuleMessage message) {
        log.info("Starting to analyze " + message.getRule().getName());
        DependentRule rule = message.getRule();
        BiFunction<String, Map<String, Double>, Double> analysisFunction = rule.getAnalysisFunction();
        Double result = analysisFunction.apply(message.getText(), message.getAnalysisCache().getAnalysisResult());
        message.getReplyTo().tell(new DataAnalysisActorCommands.AnalysisResult(message.getId(), rule.getName(), result, message.getText()));
        return this;
    }

    private Behavior<RuleActorCommand> analyzeIndependent(RuleActorCommand.AnalyzeIndependentRuleMessage message) {
        log.info("Starting to analyze " + message.getRule().getName());
        IndependentRule rule = message.getRule();
        Function<String, Double> analysisFormula = rule.getAnalysisFunction();
        Double result = analysisFormula.apply(message.getText());
        message.getReplyTo().tell(new DataAnalysisActorCommands.AnalysisResult(message.getId(), rule.getName(), result, message.getText()));
        return this;
    }


}
