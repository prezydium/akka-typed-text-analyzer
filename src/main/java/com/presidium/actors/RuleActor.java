package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.protocol.DataAnalysisActorCommands;
import com.presidium.actors.protocol.RuleActorCommand;

import java.util.function.Function;

public class RuleActor extends AbstractBehavior<RuleActorCommand> {

    public RuleActor(ActorContext<RuleActorCommand> context) {
        super(context);
    }

    @Override
    public Receive<RuleActorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(RuleActorCommand.AnalyzeMessage.class, this::analyze)
                .build();
    }

    private Behavior<RuleActorCommand> analyze(RuleActorCommand.AnalyzeMessage message) {
        Function<String, Double> analysisFormula = message.getAnalysisFormula();
        Double result = analysisFormula.apply(message.getText());
        message.getReplyTo().tell(new DataAnalysisActorCommands.AnalysisResult(message.getId(), result));
        return this;
    }


}
