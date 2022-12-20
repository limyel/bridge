package com.limyel.bridge.server;

import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.server.config.ServerConfig;
import com.limyel.bridge.server.handler.DataHandler;
import com.limyel.bridge.server.handler.HeartBeatRequestHandler;
import com.limyel.bridge.server.handler.RegisterRequestHandler;
import com.limyel.bridge.server.net.BridgeServer;
import com.limyel.bridge.server.utils.ChannelUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerStarter {
    public static void main(String[] args) {

        ServerConfig config = new ServerConfig();

        Server server = new Server();
        server.start(config);

    }
}
