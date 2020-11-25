package com.presidium.analyzer.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import com.presidium.analyzer.actors.protocol.RuleActorCommand;
import com.presidium.analyzer.rule.Rule;
import lombok.extern.slf4j.Slf4j;

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
                .onMessage(RuleActorCommand.AnalyzeMessage.class, this::analyze)
                .build();
    }

    private Behavior<RuleActorCommand> analyze(RuleActorCommand.AnalyzeMessage message) {
        log.info("Starting to analyze " + message.getRule().getName());
        Rule rule = message.getRule();
        Function<String, Double> analysisFormula = rule.getAnalysisFunction();
        Double result = analysisFormula.apply(message.getText());
        message.getReplyTo().tell(new DataAnalysisActorCommands.AnalysisResult(message.getId(), rule.getName(), result));
        return this;
    }


}
