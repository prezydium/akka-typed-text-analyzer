package com.presidium.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainActor extends AbstractBehavior<Void> {

    public static Behavior<Void> create(List<String> fileNames) {
        return Behaviors.setup(ctx -> new MainActor(ctx, fileNames));
    }

    private MainActor(ActorContext<Void> context, List<String> filenames) {
        super(context);
        log.info("MainActor Started");
        ActorRef<LoadingTrigger> loadingOverlord = context.spawn(LoadingOverlord.create(), "loadingOverlord");
        loadingOverlord.tell(new LoadingTrigger(filenames));
    }

    @Override
    public Receive<Void> createReceive() {
        throw new UnsupportedOperationException();
    }


    @Value
    public static class LoadingTrigger {

        List<String> filenamesToLoad;

    }

}
