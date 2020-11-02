package com.presidium.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.protocol.LoadingActorCommands;
import com.presidium.actors.protocol.LoadingOverlordActorCommands;
import com.presidium.actors.protocol.MainActorCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.presidium.actors.protocol.LoadingActorCommands.*;
import static com.presidium.actors.protocol.LoadingOverlordActorCommands.LoadedData;
import static com.presidium.actors.protocol.LoadingOverlordActorCommands.LoadingTrigger;

@Slf4j
public class LoadingOverlord extends AbstractBehavior<LoadingOverlordActorCommands> {


    public static Behavior<LoadingOverlordActorCommands> create(ActorRef<MainActorCommand> mainActor) {
        return Behaviors.setup(ctx -> new LoadingOverlord(ctx, mainActor));
    }

    private ActorRef<MainActorCommand> mainActor;

    private LoadingOverlord(
            ActorContext<LoadingOverlordActorCommands> context,
            ActorRef<MainActorCommand> mainActor) {
        super(context);
        this.mainActor = mainActor;
        log.info("LoadingOverlord created");
    }

    @Override
    public Receive<LoadingOverlordActorCommands> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadingTrigger.class, this::createChildrenAndStartLoading)
                .onMessage(LoadedData.class, this::pushLoadedDataToMainActor)
                .build();
    }

    private Behavior<LoadingOverlordActorCommands> pushLoadedDataToMainActor(LoadedData loadedData) {
        log.info("Received loaded data for " + loadedData.getFileName());
        mainActor.tell(new MainActorCommand.LoadedData(loadedData.getFileName(), loadedData.getLoadedText()));
        return Behaviors.same();
    }


    private Behavior<LoadingOverlordActorCommands> createChildrenAndStartLoading(LoadingTrigger trigger) {
        log.info("Received" + trigger.toString());
        List<String> filenamesToLoad = trigger.getFilenamesToLoad();
        filenamesToLoad.forEach(fileName -> {
            ActorRef<LoadingActorCommands> ref = getContext().spawn(LoadingActor.create(), fileName + "Loader");
            ref.tell(new StartLoading(fileName, getContext().getSelf()));
        });
        return this;
    }

}
