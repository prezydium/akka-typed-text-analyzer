package com.presidium.actors.protocol;

import lombok.Value;

public interface MainActorCommand {

    @Value
    class LoadedData implements MainActorCommand {
        String fileName;
        String loadedText;
    }

}
