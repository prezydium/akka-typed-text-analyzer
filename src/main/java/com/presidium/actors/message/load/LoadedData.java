package com.presidium.actors.message.load;

public class LoadedData implements DataLoading {

    private final String loadedFilename;
    private final String loadedText;

    public LoadedData(String loadedFilename, String loadedText) {
        this.loadedFilename = loadedFilename;
        this.loadedText = loadedText;
    }

    @Override
    public String getFileName() {
        return loadedFilename;
    }
}
