package com.limyel.bridge.server.handler;

import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import com.limyel.bridge.server.handler.proxy.DataHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.util.Date;

public class RegisterRequestHandler extends SimpleChannelInboundHandler<RegisterRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestPacket requestPacket) throws Exception {
        ServerBootstrap proxyServerBootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        proxyServerBootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();

                                pipeline.addLast(new ByteArrayDecoder());
                                pipeline.addLast(new ByteArrayEncoder());
                                pipeline.addLast(new DataHandler());

                                ChannelUtil.getInstance().getChannelGroup().add(ch);
                            }
                        });
                    }
                }).bind(requestPacket.getRemotePort()).addListener(future -> {
                    if (future.isSuccess()) {
                        System.out.println(new Date() + ": 端口[" + requestPacket.getRemotePort() + "]绑定成功！");
                    }
                }).sync();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
