package com.presidium.actors.protocol;

import akka.actor.typed.ActorRef;
import lombok.Value;

import java.util.function.Function;

public interface RuleActorCommand {

    @Value
    class AnalyzeMessage implements RuleActorCommand {
        String id;
        String text;
        ActorRef<DataAnalysisActorCommands> replyTo;
        Function<String, Double> analysisFormula;
    }


}
