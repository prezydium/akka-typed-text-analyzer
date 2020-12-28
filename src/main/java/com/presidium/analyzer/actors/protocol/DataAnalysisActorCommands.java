package com.presidium.analyzer.actors.protocol;

import lombok.Value;

public interface DataAnalysisActorCommands {

    @Value
    class StartAnalysing implements DataAnalysisActorCommands {
        String filename;
        String text;
    }

    @Value
    class AnalysisResult implements DataAnalysisActorCommands {
        String fileName;
        String ruleName;
        Double result;
    }
}
