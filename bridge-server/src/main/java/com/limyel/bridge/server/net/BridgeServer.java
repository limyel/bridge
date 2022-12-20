package com.limyel.bridge.server.net;

import com.limyel.bridge.common.config.BridgeConfig;
import com.limyel.bridge.server.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

public class BridgeServer {

    private Channel channel;

    private void init(ServerConfig config) {

    }

    public void bind(ServerConfig config, ChannelInitializer<SocketChannel> channelInitializer) {
        int port = config.getPort();

        // 监听端口
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            channel = serverBootstrap
                    .bind(port)
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + ": 端口[" + port + "]绑定成功！");
                        } else {
                            System.out.println(new Date() + ": 端口[" + port + "]绑定失败！");
                            throw new RuntimeException();
                        }
                    }).sync().channel();

            channel.closeFuture().addListener(future -> {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            });

        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            throw new RuntimeException(e);
        }
    }

    public void close() {
        if (channel != null) {
            channel.close();
        }
    }

}
