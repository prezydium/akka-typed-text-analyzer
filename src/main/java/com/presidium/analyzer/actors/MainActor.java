package com.presidium.analyzer.actors;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import com.presidium.analyzer.actors.protocol.LoadingOverlordActorCommands;
import com.presidium.analyzer.actors.protocol.MainActorCommand;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.presidium.analyzer.actors.protocol.MainActorCommand.LoadedData;

@Slf4j
public class MainActor extends AbstractBehavior<MainActorCommand> {

    private final ActorRef<DataAnalysisActorCommands> dataAnalysisActorRef;

    public static Behavior<MainActorCommand> create(List<String> fileNames) {
        return Behaviors.setup(ctx -> new MainActor(ctx, fileNames));
    }

    private MainActor(ActorContext<MainActorCommand> context, List<String> filenames) {
        super(context);
        log.info("MainActor Started");
        ActorRef<LoadingOverlordActorCommands> loadingOverlord =
                context.spawn(LoadingOverlord.create(context.getSelf()), "loadingOverlord");
        dataAnalysisActorRef = context.spawn(DataAnalysisActor.create(), "dataAnalysis");
        loadingOverlord.tell(new LoadingOverlordActorCommands.LoadingTrigger(filenames, context.getSelf()));
    }

    @Override
    public Receive<MainActorCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadedData.class, this::forwardForAnalysis)
                .build();
    }

    private Behavior<MainActorCommand> forwardForAnalysis(LoadedData msg) {
        dataAnalysisActorRef.tell(new DataAnalysisActorCommands.StartAnalysing(msg.getFileName(), msg.getLoadedText()));
        return this;
    }

}
