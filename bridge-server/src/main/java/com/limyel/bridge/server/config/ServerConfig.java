package com.limyel.bridge.server.config;

import com.limyel.bridge.common.config.BridgeConfig;
import com.limyel.bridge.server.constant.ConfigConstant;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class ServerConfig extends BridgeConfig {

    public ServerConfig() {
        this(DefaultServerConfig.CONFIG_PATH);
    }

    public ServerConfig(String path) {
        load(path);
    }

    public int getPort() {
        String port = Optional.ofNullable(properties.getProperty(ConfigConstant.PORT))
                .orElse(DefaultServerConfig.PORT);
        return Integer.parseInt(port);
    }

}
