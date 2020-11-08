package com.presidium.analyzer.actors.protocol;

import akka.actor.typed.ActorRef;
import lombok.Value;

import java.util.List;

public interface LoadingOverlordActorCommands {

    @Value
    class LoadingTrigger implements LoadingOverlordActorCommands {
        List<String> filenamesToLoad;
        ActorRef<MainActorCommand> parent;
    }

    @Value
    class LoadedData implements LoadingOverlordActorCommands {
        String fileName;
        String loadedText;
    }

}
