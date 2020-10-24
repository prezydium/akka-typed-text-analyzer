package com.presidium;



import akka.actor.typed.ActorSystem;
import com.presidium.example.HelloWorldMain;

public class App {
    public static void main(String[] args) {
        final ActorSystem<HelloWorldMain.SayHello> system =
                ActorSystem.create(HelloWorldMain.create(), "hello");

        system.tell(new HelloWorldMain.SayHello("World"));
        system.tell(new HelloWorldMain.SayHello("Akka"));

    }
}
