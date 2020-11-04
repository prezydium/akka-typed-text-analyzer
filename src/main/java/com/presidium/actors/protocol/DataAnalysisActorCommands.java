package com.presidium.actors.protocol;

import lombok.Value;

public interface DataAnalysisActorCommands {

    @Value
    class StartAnalysing implements DataAnalysisActorCommands {
        String filename;
        String text;
    }

    @Value
    class AnalysisResult implements DataAnalysisActorCommands {
        String id;
        String ruleName;
        Double result;
    }
}
