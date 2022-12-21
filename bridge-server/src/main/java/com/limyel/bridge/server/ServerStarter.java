package com.limyel.bridge.server;

import com.limyel.bridge.server.config.ServerConfig;

import java.util.Arrays;

public class ServerStarter {
    public static void main(String[] args) {

        // todo 简陋的命令行参数
        ServerConfig config;
        if (args.length == 1) {
            config = new ServerConfig(args[0]);
        } else {
            config = new ServerConfig();
        }

        Server server = new Server();
        server.start(config);

    }
}
