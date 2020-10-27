package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;
import akka.actor.typed.receptionist.ServiceKey;
import com.presidium.actors.message.load.LoadedData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataAnalysisActor extends AbstractBehavior<LoadedData> {

    public static final ServiceKey<LoadedData> SERVICE_KEY = ServiceKey.create(LoadedData.class, "data_analysis");

    public static Behavior<LoadedData> create() {
        return Behaviors.setup(ctx -> {
            ctx
                    .getSystem()
                    .receptionist()
                    .tell(Receptionist.register(SERVICE_KEY, ctx.getSelf()));
            return new DataAnalysisActor(ctx).createReceive();
        });
    }

    public DataAnalysisActor(ActorContext<LoadedData> context) {
        super(context);
        log.info(getClass().getSimpleName() + " started");
    }

    @Override
    public Receive<LoadedData> createReceive() {
        return newReceiveBuilder()
                .onMessage(LoadedData.class, this::startAnalysis)
                .build();
    }

    private Behavior<LoadedData> startAnalysis(LoadedData msg) {
        log.info("Received data from file " + msg.getLoadedFilename());
        log.info(msg.getLoadedText());
        return this;
    }
}
