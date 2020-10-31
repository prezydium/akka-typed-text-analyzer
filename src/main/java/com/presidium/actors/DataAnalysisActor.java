package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.protocol.DataAnalysisActorCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataAnalysisActor extends AbstractBehavior<DataAnalysisActorCommands> {


    public static Behavior<DataAnalysisActorCommands> create() {
        return Behaviors.setup(DataAnalysisActor::new);
    }

    public DataAnalysisActor(ActorContext<DataAnalysisActorCommands> context) {
        super(context);
        log.info(getClass().getSimpleName() + " started");
    }

    @Override
    public Receive<DataAnalysisActorCommands> createReceive() {
        return newReceiveBuilder()
                .onMessage(DataAnalysisActorCommands.StartAnalysing.class, this::startAnalysis)
                .build();
    }

    private Behavior<DataAnalysisActorCommands> startAnalysis(DataAnalysisActorCommands.StartAnalysing msg) {
/*        log.info("Received data from file " + msg.getLoadedFilename());
        log.info(msg.getLoadedText());*/
        return this;
    }
}
