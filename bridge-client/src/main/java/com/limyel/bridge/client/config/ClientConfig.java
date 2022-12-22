package com.limyel.bridge.client.config;

import com.limyel.bridge.client.constant.ConfigConstant;
import com.limyel.bridge.client.entity.ProxyInfo;
import com.limyel.bridge.common.config.BridgeConfig;
import com.limyel.bridge.common.environment.BridgeEnvironment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientConfig extends BridgeConfig {

    private static volatile ClientConfig INSTANCE;

    private ClientConfig(String path) {
        load(path);
    }

    public static ClientConfig getInstance() {
        if (INSTANCE == null) {
            synchronized (ClientConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClientConfig(DefaultClientConfig.CONFIG_PATH);
                }
            }
        }
        return INSTANCE;
    }

    public static ClientConfig getInstance(String path) {
        if (INSTANCE == null) {
            synchronized (ClientConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ClientConfig(path);
                }
            }
        }
        return INSTANCE;
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

    public List<ProxyInfo> getProxyInfo() {
        String s = properties.getProperty(ConfigConstant.PROXY);
        String[] items = s.split("}\\s*,\\s*\\{");
        return Arrays.stream(items).map(item -> {
            ProxyInfo proxyInfo = new ProxyInfo();
            item = item.replace("{", "").replace("}", "");
            String[] split = item.split(",");
            if (split.length != 2) {
                // todo 异常
            }
            for (String infoItem : split) {
                if (infoItem.contains(ConfigConstant.REMOTE_PROXY)) {
                    int port = Integer.parseInt(infoItem.split(":")[1]);
                    proxyInfo.setRemoteProxyPort(port);
                } else if (infoItem.contains(ConfigConstant.LOCAL_PROXY)) {
                    String host = infoItem.split(":")[1];
                    int port = Integer.parseInt(infoItem.split(":")[2]);
                    proxyInfo.setLocalProxyHost(host);
                    proxyInfo.setLocalProxyPort(port);
                } else {
                    // todo 异常
                }
            }
            return proxyInfo;
        }).collect(Collectors.toList());
    }

}
