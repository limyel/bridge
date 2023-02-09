package com.limyel.bridge.client;

import com.limyel.bridge.client.handler.ClientHandler;
import com.limyel.bridge.client.handler.ProxyDataResponseHandler;
import com.limyel.bridge.client.handler.RegisterResponseHandler;
import com.limyel.bridge.client.net.BridgeClient;
import com.limyel.bridge.codec.PacketCodecHandler;
import com.limyel.bridge.codec.PacketDecoder;
import com.limyel.bridge.codec.PacketEncoder;
import com.limyel.bridge.codec.Spliter;
import com.limyel.bridge.handler.ExceptionHandler;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @author limyel
 * @since 2023-02-07 14:58
 */
public class Client {
    public static void main(String[] args) {
        start();
    }

    public static void start() {
        int port = 9876;
        String host = "106.55.131.8";
//        String host = "192.168.31.98";

        BridgeClient client = new BridgeClient();
        client.connect(host, port, new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new Spliter());
                pipeline.addLast(new PacketDecoder());
                pipeline.addLast(new PacketEncoder());

                pipeline.addLast(new ClientHandler());
                pipeline.addLast(new RegisterResponseHandler());
                pipeline.addLast(new ProxyDataResponseHandler());

                pipeline.addLast(new ExceptionHandler());
            }
        });
    }
}
