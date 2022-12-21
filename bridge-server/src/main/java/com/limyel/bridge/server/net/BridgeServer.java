package com.limyel.bridge.server.net;

import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.codec.Spliter;
import com.limyel.bridge.common.config.BridgeConfig;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.common.utils.ChannelUtil;
import com.limyel.bridge.server.config.ServerConfig;
import com.limyel.bridge.server.handler.DataHandler;
import com.limyel.bridge.server.handler.HeartBeatRequestHandler;
import com.limyel.bridge.server.handler.RegisterRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Date;

public class BridgeServer {

    private Channel channel;

    public void bind(int port, ChannelInitializer<SocketChannel> channelInitializer) {
        // 监听端口
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // 处理连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(channelInitializer);
                        }
                    });

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
