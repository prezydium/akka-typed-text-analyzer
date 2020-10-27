package com.presidium.actors.message.load;

import lombok.Value;

@Value
public class StartLoading implements DataLoading{

    String fileToLoad;

}
