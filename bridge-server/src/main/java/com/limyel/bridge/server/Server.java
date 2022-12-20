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
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

import java.util.Date;

public class Server {

    private BridgeServer bridgeServer;

    public void start(ServerConfig config) {
        BridgeServer bridgeServer = new BridgeServer();
        bridgeServer.bind(config, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IMIdleStateHandler());
                pipeline.addLast(new Spliter());
                pipeline.addLast(new PacketDecoder());

                pipeline.addLast(new RegisterRequestHandler());
                pipeline.addLast(new DataHandler());

                pipeline.addLast(new PacketEncoder());
                pipeline.addLast(HeartBeatRequestHandler.getInstance());

                ChannelUtil.getInstance().setServerChannel(ch);
            }
        });
    }

}
