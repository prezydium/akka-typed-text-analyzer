package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;
import com.presidium.actors.message.load.StartLoading;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;



@Slf4j
public class LoadingActor extends AbstractBehavior<StartLoading> {

    public static Behavior<StartLoading> create() {
        return Behaviors.setup(LoadingActor::new);
    }

    public LoadingActor(ActorContext<StartLoading> context) {
        super(context);
        log.info("LoadingActor started");
    }

    @Override
    public Receive<StartLoading> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartLoading.class, this::startLoading)
                .build();
    }

    private Behavior<StartLoading> startLoading(StartLoading msg) {
        log.info("Received" + msg);
        String fileName = msg.getFileToLoad();
        try {
            String fullText = String.join("\n", Files.readAllLines(Paths.get("src/main/resources/" + fileName)));
            LoadedData loadedDataMessage = new LoadedData(fullText);
            //todo add receptionist
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }


    @Value
    public static class LoadedData {
        String loadedData;
    }
}
