package com.presidium.actors.protocol;

import akka.actor.typed.ActorRef;
import lombok.Value;

public interface LoadingActorCommands {

    @Value
    class StartLoading implements LoadingActorCommands {
        String fileToLoad;
        ActorRef<LoadingOverlordActorCommands> parent;
    }

}
