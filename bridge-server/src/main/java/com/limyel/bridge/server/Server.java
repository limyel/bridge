package com.limyel.bridge.server;

import com.limyel.bridge.codec.PacketCodecHandler;
import com.limyel.bridge.codec.Spliter;
import com.limyel.bridge.server.handler.InactiveRequestHandler;
import com.limyel.bridge.server.handler.ProxyDataRequestHandler;
import com.limyel.bridge.server.handler.RegisterRequestHandler;
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
        start();
    }

    public static void start() {
        int port = 9876;
        BeidgeServer beidgeServer = new BeidgeServer();
        beidgeServer.bind(port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new Spliter());
                pipeline.addLast(PacketCodecHandler.getInstance());

                pipeline.addLast(new ProxyDataRequestHandler());
                pipeline.addLast(new RegisterRequestHandler());
                pipeline.addLast(new InactiveRequestHandler());

                ChannelUtil.getInstance().setParentChannel(ch);
            }
        });
    }

}
