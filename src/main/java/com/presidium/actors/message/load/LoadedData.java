package com.presidium.actors.message.load;

import lombok.Value;

@Value
public class LoadedData implements MainActorCommand {
    String loadedFilename;
    String loadedText;
}
