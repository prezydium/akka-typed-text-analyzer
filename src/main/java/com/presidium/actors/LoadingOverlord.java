package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

public class LoadingOverlord extends AbstractBehavior<MainActor.LoadingTrigger> {


    private LoadingOverlord(ActorContext<MainActor.LoadingTrigger> context) {
        super(context);
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
        return this;
    }

    public static class LoadedText {

    }


}
