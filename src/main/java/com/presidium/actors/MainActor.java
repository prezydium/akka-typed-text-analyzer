package com.presidium.actors;

import akka.actor.Terminated;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.List;

public class MainActor extends AbstractBehavior<Void> {


    public MainActor(ActorContext<Void> context) {
        super(context);
    }

    @Override
    public Receive<Void> createReceive() {
        return null;
    }

    public static class LoadingTrigger {
        private List<String> filenamesToLoad;

        public LoadingTrigger(List<String> filenamesToLoad) {
            this.filenamesToLoad = filenamesToLoad;
        }
    }

}
