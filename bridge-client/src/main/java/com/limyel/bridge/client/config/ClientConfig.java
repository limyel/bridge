package com.limyel.bridge.client.config;

import com.limyel.bridge.client.constant.ConfigConstant;
import com.limyel.bridge.common.config.BridgeConfig;

import java.util.Optional;

public class ClientConfig extends BridgeConfig {

    public ClientConfig() {
        this(DefaultClientConfig.CONFIG_PATH);
    }

    public ClientConfig(String path) {
        load(path);
    }

    public String getServerHost() {
        return Optional.ofNullable(properties.getProperty(ConfigConstant.SERVER_HOST))
                .orElse(DefaultClientConfig.SERVER_HOST);
    }

    public int getServerPort() {
        String port = Optional.ofNullable(properties.getProperty(ConfigConstant.SERVER_PORT))
                .orElse(DefaultClientConfig.SERVER_PORT);
        return Integer.parseInt(port);
    }

}
