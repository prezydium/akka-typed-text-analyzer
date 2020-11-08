package com.presidium.analyzer;


import akka.actor.typed.ActorSystem;
import com.presidium.analyzer.actors.MainActor;

import java.util.List;

public class App {
    public static void main(String[] args) {
        List<String> filesToLad = List.of("pride.txt", "prince.txt");
        ActorSystem.create(MainActor.create(filesToLad), "textAnalyzer");

    }
}
