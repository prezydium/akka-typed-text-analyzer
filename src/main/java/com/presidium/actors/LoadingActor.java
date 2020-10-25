package com.presidium.actors;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;

public class LoadingActor extends AbstractBehavior<MainActor.LoadingTrigger> {
    public LoadingActor(ActorContext context) {
        super(context);
    }

    @Override
    public Receive createReceive() {
        return null;
    }


    private static class LoadedData{

        private String loadedData;

        public LoadedData(String loadedData) {
            this.loadedData = loadedData;
        }
    }
}
