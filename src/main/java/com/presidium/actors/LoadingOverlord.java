package com.presidium.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.protocol.LoadingOverlordActorCommands;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.presidium.actors.protocol.LoadingOverlordActorCommands.LoadedData;
import static com.presidium.actors.protocol.LoadingOverlordActorCommands.LoadingTrigger;

@Slf4j
public class LoadingOverlord extends AbstractBehavior<LoadingOverlordActorCommands> {


    private LoadingOverlord(ActorContext<LoadingOverlordActorCommands> context) {
        super(context);
        log.info("LoadingOverlord created");
    }

    public static Behavior<LoadingOverlordActorCommands> create() {
        return Behaviors.setup(LoadingOverlord::new);
    }

    @Override
    public Receive<LoadingOverlordActorCommands> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadingTrigger.class, this::createChildrenAndStartLoading)
                .onMessage(LoadedData.class, this::pushLoadedData)
                .build();
    }

    private Behavior<LoadingOverlordActorCommands> pushLoadedData(LoadedData a) {
        log.info("Received loaded data for " + a.getFileName());

        return Behaviors.same();
    }


    private Behavior<LoadingOverlordActorCommands> createChildrenAndStartLoading(LoadingTrigger trigger) {
        log.info("Received" + trigger.toString());
        List<String> filenamesToLoad = trigger.getFilenamesToLoad();
        filenamesToLoad.forEach(fileName -> {
            ActorRef<StartLoading> ref = getContext().spawn(LoadingActor.create(), fileName + "Loader");
            ref.tell(new StartLoading(fileName));
        });
        return this;
    }

}
