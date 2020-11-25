package com.presidium.analyzer.actors;

import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.actor.testkit.typed.javadsl.TestInbox;
import com.presidium.analyzer.actors.protocol.DataAnalysisActorCommands;
import org.junit.jupiter.api.Test;

class DataAnalysisActorTest {

    @Test
    void testAnalysis() throws InterruptedException {
        //given
        BehaviorTestKit<DataAnalysisActorCommands> dataAnalysisActor = BehaviorTestKit.create(DataAnalysisActor.create());
        TestInbox<DataAnalysisActorCommands> inbox = TestInbox.create();
        DataAnalysisActorCommands.StartAnalysing start = new DataAnalysisActorCommands.StartAnalysing("test.data", "A Lorem Ipsum");
        //when
        dataAnalysisActor.run(start);
        Thread.sleep(1000);
    }
}