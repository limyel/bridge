package com.limyel.bridge.client;

import com.limyel.bridge.client.config.ClientConfig;

public class ClientStarter {
    public static void main(String[] args) {

        // todo 简陋的命令行参数
        ClientConfig config;
        if (args.length == 1) {
            config = ClientConfig.getInstance(args[0]);
        } else {
            config = ClientConfig.getInstance();
        }

        Client client = new Client();
        client.start(config);
    }
}
