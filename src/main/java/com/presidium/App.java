package com.presidium;



import akka.actor.typed.ActorSystem;
import com.presidium.example.HelloWorldMain;

public class App {
    public static void main(String[] args) {
        final ActorSystem<HelloWorldMain.SayHello> system =
                ActorSystem.create(HelloWorldMain.create(), "textAnalyzer");

    }
}
