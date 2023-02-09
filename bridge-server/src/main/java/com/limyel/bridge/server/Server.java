package com.limyel.bridge.server;

import com.limyel.bridge.codec.PacketCodecHandler;
import com.limyel.bridge.codec.Spliter;
import com.limyel.bridge.handler.BridgeIdleStateHandler;
import com.limyel.bridge.handler.ExceptionHandler;
import com.limyel.bridge.server.config.ServerConfig;
import com.limyel.bridge.server.handler.*;
import com.limyel.bridge.server.net.BeidgeServer;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author limyel
 * @since 2023-02-07 14:49
 */
public class Server {

    public static void main(String[] args) {
        ServerConfig serverConfig;
        if (args.length == 1) {
            serverConfig = new ServerConfig(args[0]);
        } else {
            serverConfig = new ServerConfig();
        }

        start(serverConfig);
    }

    public static void start(ServerConfig serverConfig) {
        int port = serverConfig.getPort();
        BeidgeServer beidgeServer = new BeidgeServer();
        beidgeServer.bind(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new BridgeIdleStateHandler());
                pipeline.addLast(new Spliter());
                pipeline.addLast(PacketCodecHandler.getInstance());

                pipeline.addLast(new ProxyDataRequestHandler());
                pipeline.addLast(new RegisterRequestHandler(serverConfig.getPassword()));
                pipeline.addLast(new InactiveRequestHandler());

                pipeline.addLast(HeartBeatRequestHandler.getInstance());
                pipeline.addLast(new ExceptionHandler());

            }
        });
    }

}
