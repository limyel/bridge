package com.limyel.bridge.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

public class BridgeClient {

    public ChannelFuture connect(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(channelInitializer);
            Channel channel = bootstrap
                    .connect(host, port)
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + " 连接服务端 " + host + ":" + port + " 成功");
                        } else {
                            future.cause().printStackTrace();
                            System.out.println(new Date() + " 连接服务端 " + host + ":" + port + " 失败");
                        }
                    })
                    .sync().channel();
            return channel.closeFuture().addListener(future -> workerGroup.shutdownGracefully());
        } catch (InterruptedException e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
            throw new RuntimeException(e);
        }
    }

}
