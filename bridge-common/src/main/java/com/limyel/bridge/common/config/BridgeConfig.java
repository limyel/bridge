package com.limyel.bridge.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public abstract class BridgeConfig {

    protected Properties properties = new Properties();

    protected void load(String path) {
        try {
            properties.load(Files.newInputStream(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
