package com.presidium.analyzer.actors;

import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import akka.actor.testkit.typed.javadsl.TestInbox;
import com.presidium.analyzer.actors.protocol.LoadingActorCommands;
import com.presidium.analyzer.actors.protocol.LoadingOverlordActorCommands;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadingActorTest {


    @Test
    public void shouldSendReplyWithLoadedData() {
        //given
        BehaviorTestKit<LoadingActorCommands> testActor = BehaviorTestKit.create(LoadingActor.create());
        TestInbox<LoadingOverlordActorCommands> inbox = TestInbox.create();
        //when
        testActor.run(new LoadingActorCommands.StartLoading("test.txt", inbox.getRef()));
        //then
        List<LoadingOverlordActorCommands> allReceivedReplies = inbox.getAllReceived();
        assertEquals(1, allReceivedReplies.size());
        LoadingOverlordActorCommands.LoadedData loadedData = (LoadingOverlordActorCommands.LoadedData) allReceivedReplies.get(0);
        assertEquals("this is a test file", loadedData.getLoadedText());
    }

}