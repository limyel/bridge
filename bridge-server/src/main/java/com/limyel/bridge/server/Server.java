package com.limyel.bridge.server;

import com.limyel.bridge.common.handler.IMIdleStateHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;

public class Server {

    private final int PORT = 9876;

    private ServerBootstrap serverBootstrap;

    public Server() {
        serverBootstrap = new ServerBootstrap();

        // 监听端口
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        serverBootstrap
                .group(bossGroup, workerGroup)
                // 指定 IO 模型
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new IMIdleStateHandler());
                            }
                        });
                    }
                });
    }

    public void start() {
        bind(PORT);
    }

    private void bind(final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 端口[" + port + "]绑定成功！");
            } else {
                System.err.println("端口[" + port + "]绑定失败，尝试下一个端口。");
                bind(port + 1);
            }
        });
    }

}
