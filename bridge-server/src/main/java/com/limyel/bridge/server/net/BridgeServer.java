package com.limyel.bridge.server.net;

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

    public void bind(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        // 1. 声明线程池
        // 处理Accept事件
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理读写事件
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 创建服务端引导类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    // 3. 设置线程池
                    .group(bossGroup, workerGroup)
                    // 4. 设置 ServerSocketChannel 类型
                    .channel(NioServerSocketChannel.class)
                    // 5. 设置参数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 6. 设置Handler（略）
                    // 7. 设置子Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(channelInitializer);
                        }
                    });

            channel = serverBootstrap
                    // 8. 绑定端口
                    .bind(port)
                    .addListener(future -> {
                        if (future.isSuccess()) {
                            System.out.println(new Date() + ": 端口[" + port + "]绑定成功！");
                        } else {
                            System.out.println(new Date() + ": 端口[" + port + "]绑定失败！");
                            throw new RuntimeException();
                        }
                    }).sync().channel();

            // 9. 等待服务端口关闭
            channel.closeFuture().addListener(future -> {
                // 10. 关闭线程池
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
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
