package com.presidium.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;
import com.presidium.actors.message.load.StartLoading;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class LoadingOverlord extends AbstractBehavior<MainActor.LoadingTrigger> {


    private LoadingOverlord(ActorContext<MainActor.LoadingTrigger> context) {
        super(context);
        log.info("LoadingOverlord created");
    }

    public static Behavior<MainActor.LoadingTrigger> create(){
        return Behaviors.setup(LoadingOverlord::new);
    }

    @Override
    public Receive<MainActor.LoadingTrigger> createReceive() {
        ReceiveBuilder<MainActor.LoadingTrigger> loadingTriggerReceiveBuilder = newReceiveBuilder();
        loadingTriggerReceiveBuilder.onMessage(MainActor.LoadingTrigger.class, this::createChildrenAndStartLoading);
       return loadingTriggerReceiveBuilder.build();
    }


    private Behavior<MainActor.LoadingTrigger> createChildrenAndStartLoading(MainActor.LoadingTrigger trigger) {
        log.info("Received" + trigger.toString());
        List<String> filenamesToLoad = trigger.getFilenamesToLoad();
        filenamesToLoad.forEach( fileName -> {
            ActorRef<StartLoading> ref = getContext().spawn(LoadingActor.create(), fileName + "Loader");
            ref.tell(new StartLoading(fileName));
        });
        return this;
    }

    public static class LoadedText {

    }


}
