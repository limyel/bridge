package com.limyel.bridge.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

/**
 * @author limyel
 * @since 2023-02-07 14:58
 */
public class BridgeClient {

    public ChannelFuture connect(String host, int port, ChannelInitializer<SocketChannel> channelInitializer) {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(channelInitializer);
                        }
                    });
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
