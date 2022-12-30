package com.limyel.bridge.server;

import com.limyel.bridge.common.codec.PacketCodecHandler;
import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.common.utils.ChannelUtil;
import com.limyel.bridge.server.config.ServerConfig;
import com.limyel.bridge.server.handler.DataHandler;
import com.limyel.bridge.server.handler.DisconnectRequestHandler;
import com.limyel.bridge.server.handler.HeartBeatRequestHandler;
import com.limyel.bridge.server.handler.RegisterRequestHandler;
import com.limyel.bridge.server.net.BridgeServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class Server {

    public void start(ServerConfig config) {
        int port = config.getPort();
        BridgeServer bridgeServer = new BridgeServer();
        bridgeServer.bind(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelUtil.getInstance().setParentChannel(ch);

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IMIdleStateHandler());
                pipeline.addLast(new Spliter());
                pipeline.addLast(PacketCodecHandler.getInstance());

                pipeline.addLast(new RegisterRequestHandler());
                pipeline.addLast(new DataHandler());
                pipeline.addLast(new DisconnectRequestHandler());

                pipeline.addLast(HeartBeatRequestHandler.getInstance());
            }
        });
    }

}
