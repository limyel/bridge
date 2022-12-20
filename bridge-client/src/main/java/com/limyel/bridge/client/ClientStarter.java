package com.limyel.bridge.client;

import com.limyel.bridge.client.config.ClientConfig;

public class ClientStarter {
    public static void main(String[] args) {

        ClientConfig config = new ClientConfig();

        Client client = new Client();
        client.start(config);
    }
}
