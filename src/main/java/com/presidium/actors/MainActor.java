package com.presidium.actors;

import akka.actor.Terminated;
import akka.actor.typed.ActorRef;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.List;

public class MainActor extends AbstractBehavior<Void> {


    public MainActor(ActorContext<Void> context, List<String> filenames) {
        super(context);
        ActorRef<LoadingTrigger> loadingOverlord = context.spawn(LoadingOverlord.create(), "loadingOverlord");
        loadingOverlord.tell(new LoadingTrigger(filenames));
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
