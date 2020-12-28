package com.presidium.analyzer.actors.protocol;

import akka.actor.typed.ActorRef;
import com.presidium.analyzer.analysis.AnalysisCache;
import com.presidium.analyzer.rule.DependentRule;
import com.presidium.analyzer.rule.IndependentRule;
import lombok.Value;

public interface RuleActorCommand {

    @Value
    class AnalyzeIndependentRuleMessage implements RuleActorCommand {
        String id;
        String text;
        ActorRef<DataAnalysisActorCommands> replyTo;
        IndependentRule rule;
    }

    @Value
    class AnalyzeDependentRuleMessage implements RuleActorCommand {
        String id;
        ActorRef<DataAnalysisActorCommands> replyTo;
        AnalysisCache analysisCache;
        DependentRule rule;
    }

}
