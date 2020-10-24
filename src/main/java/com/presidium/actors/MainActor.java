package com.presidium.actors;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.Behaviors;

import java.util.List;

public class MainActor {



    public static class StartLoading{
        private List<String> filesToLoad;

        public StartLoading(List<String> filesToLoad) {
            this.filesToLoad = filesToLoad;
        }
    }

}
