package com.presidium.analyzer.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.analyzer.actors.protocol.LoadingActorCommands;
import com.presidium.analyzer.actors.protocol.LoadingOverlordActorCommands;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Slf4j
public class LoadingActor extends AbstractBehavior<LoadingActorCommands> {


    public static Behavior<LoadingActorCommands> create() {
        return Behaviors.setup(LoadingActor::new);
    }

    public LoadingActor(ActorContext<LoadingActorCommands> context) {
        super(context);
        log.info("LoadingActor started");
    }

    @Override
    public Receive<LoadingActorCommands> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadingActorCommands.StartLoading.class, this::startLoading)
                .build();
    }

    private Behavior<LoadingActorCommands> startLoading(LoadingActorCommands.StartLoading msg) {
        log.info("Received" + msg);
        String fileName = msg.getFileToLoad();
        try {
            String fullText = String.join("\n", Files.readAllLines(Paths.get("src/main/resources/" + fileName)));
            msg.getParent().tell(new LoadingOverlordActorCommands.LoadedData(fileName, fullText));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
}
