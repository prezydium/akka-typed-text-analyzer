package com.presidium.actors.message.load;

public class StartLoading implements DataLoading{

    private final String fileToLoad;


    public StartLoading(String fileToLoad) {
        this.fileToLoad = fileToLoad;
    }

    @Override
    public String getFileName() {
        return fileToLoad;
    }
}
