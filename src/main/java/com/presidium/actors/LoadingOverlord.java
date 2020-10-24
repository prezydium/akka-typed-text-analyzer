package com.presidium.actors;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public class LoadingOverlord extends AbstractBehavior<MainActor.StartLoading> {


    public LoadingOverlord(ActorContext<MainActor.StartLoading> context) {
        super(context);
    }

    @Override
    public Receive<MainActor.StartLoading> createReceive() {
        return null;
    }

    public static class LoadedText {

    }
}
