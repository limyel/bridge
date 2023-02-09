package com.limyel.bridge.server.config;

import com.limyel.bridge.config.BridgeConfig;

import java.io.IOException;
import java.util.Optional;

/**
 * @author limyel
 * @since 2023-02-09 11:04
 */
public class ServerConfig extends BridgeConfig {

    public static final String DEFAULT_CONFIG_PATH = "./server.properties";

    public ServerConfig() {
        this(DEFAULT_CONFIG_PATH);
    }

    public ServerConfig(String path) {
        load(path);
    }

    public int getPort() {
        String port = properties.getProperty(ConfigConstant.PORT);
        return Integer.parseInt(port);
    }

}
