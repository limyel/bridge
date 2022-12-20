package com.limyel.bridge.server;

import com.limyel.bridge.server.config.ServerConfig;

public class ServerStarter {
    public static void main(String[] args) {

        ServerConfig config = new ServerConfig();

        Server server = new Server();
        server.start(config);

    }
}
