package com.presidium.actors;

import java.util.List;

public class MainActor {



    public static class StartLoading{
        private List<String> filesToLoad;

        public StartLoading(List<String> filesToLoad) {
            this.filesToLoad = filesToLoad;
        }
    }

}
