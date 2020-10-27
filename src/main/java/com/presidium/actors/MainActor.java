package com.presidium.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.message.load.LoadedData;
import com.presidium.actors.message.load.MainActorCommand;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MainActor extends AbstractBehavior<MainActorCommand> {

    private ActorRef<LoadedData> dataAnalysisActorRef;

    public static Behavior<MainActorCommand> create(List<String> fileNames) {
        return Behaviors.setup(ctx -> new MainActor(ctx, fileNames));
    }

    private MainActor(ActorContext<MainActorCommand> context, List<String> filenames) {
        super(context);
        log.info("MainActor Started");
        ActorRef<LoadingTrigger> loadingOverlord = context.spawn(LoadingOverlord.create(), "loadingOverlord");
        dataAnalysisActorRef = context.spawn(DataAnalysisActor.create(), "dataAnalysis");
        loadingOverlord.tell(new LoadingTrigger(filenames));
    }

    @Override
    public Receive<MainActorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadedData.class, this::forwardForAnalysis)
                .build();
    }

    private Behavior<MainActorCommand> forwardForAnalysis(LoadedData msg) {
        dataAnalysisActorRef.tell(msg);
        return this;
    }


    @Value
    public static class LoadingTrigger {
        List<String> filenamesToLoad;
    }

}
