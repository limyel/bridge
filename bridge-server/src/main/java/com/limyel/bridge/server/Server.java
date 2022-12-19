package com.limyel.bridge.server;

import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.server.handler.DataHandler;
import com.limyel.bridge.server.handler.HeartBeatRequestHandler;
import com.limyel.bridge.server.handler.RegisterRequestHandler;
import com.limyel.bridge.server.utils.ProxyChannelGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
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
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast(new IMIdleStateHandler());
                                pipeline.addLast(new Spliter());
                                pipeline.addLast(new PacketDecoder());

                                pipeline.addLast(new RegisterRequestHandler());
                                pipeline.addLast(new DataHandler());

                                pipeline.addLast(new PacketEncoder());
                                pipeline.addLast(HeartBeatRequestHandler.INSTANCE);

                                ProxyChannelGroup.INSTANCE.serverChannel = ch;
                            }
                        });
                    }
                });
    }

    public void start() {
        bind(PORT);
    }

    private void bind(final int port) {
        try {
            serverBootstrap.bind(port).addListener(future -> {
                if (future.isSuccess()) {
                    System.out.println(new Date() + ": 端口[" + port + "]绑定成功！");
                } else {
                    System.err.println("端口[" + port + "]绑定失败，尝试下一个端口。");
                    bind(port + 1);
                }
            }).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
