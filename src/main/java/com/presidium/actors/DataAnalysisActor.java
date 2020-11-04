package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.presidium.actors.analysis.AnalysisCache;
import com.presidium.actors.protocol.DataAnalysisActorCommands;
import com.presidium.actors.rule.Rule;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.presidium.actors.protocol.DataAnalysisActorCommands.*;

@Slf4j
public class DataAnalysisActor extends AbstractBehavior<DataAnalysisActorCommands> {

    private static final EnumSet<Rule> rules = EnumSet.allOf(Rule.class);
    private Map<String, AnalysisCache> cache = new HashMap<>();

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
                .onMessage(StartAnalysing.class, this::startAnalysis)
                .onMessage(AnalysisResult.class, this::processAnalysisResults)
                .build();
    }

    private Behavior<DataAnalysisActorCommands> processAnalysisResults(AnalysisResult msg) {
        AnalysisCache analysisCache = cache.get(msg.getId());
        analysisCache.getAnalysisResult().put(msg.getRuleName(), msg.getResult());
        if (analysisCache.getAnalysisResult().keySet().containsAll(Arrays.asList(Rule.values()))){
            //todo finish
        } else {
            //todo continue
        }

        return this;
    }

    private Behavior<DataAnalysisActorCommands> startAnalysis(StartAnalysing msg) {
        log.info("Received data from file " + msg.getFilename());

        return this;
    }
}
