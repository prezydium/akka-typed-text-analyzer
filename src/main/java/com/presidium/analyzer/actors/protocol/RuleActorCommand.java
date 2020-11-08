package com.presidium.analyzer.actors.protocol;

import akka.actor.typed.ActorRef;
import com.presidium.analyzer.rule.Rule;
import lombok.Value;

public interface RuleActorCommand {

    @Value
    class AnalyzeMessage implements RuleActorCommand {
        String id;
        String text;
        ActorRef<DataAnalysisActorCommands> replyTo;
        Rule rule;
    }


}
