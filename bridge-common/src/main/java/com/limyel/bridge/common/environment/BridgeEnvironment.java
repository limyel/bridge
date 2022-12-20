package com.limyel.bridge.common.environment;

public class BridgeEnvironment {

    private static BridgeEnvironment INSTANCE;

    private String clientConfigPath;

    private BridgeEnvironment() {

    }

    public static BridgeEnvironment getInstance() {
        if (INSTANCE == null) {
            synchronized (BridgeEnvironment.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BridgeEnvironment();
                }
            }
        }
        return INSTANCE;
    }

    public void setClientConfigPath(String path) {
        this.clientConfigPath = path;
    }
    public String getClientConfigPath() {
        return this.clientConfigPath;
    }
}
