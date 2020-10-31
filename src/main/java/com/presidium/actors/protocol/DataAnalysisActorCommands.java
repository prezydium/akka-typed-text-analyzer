package com.presidium.actors.protocol;

import lombok.Value;

public interface DataAnalysisActorCommands {

    @Value
    class StartAnalysing implements DataAnalysisActorCommands {
        String filename;
        String text;
    }
}
