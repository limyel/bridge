package com.limyel.bridge.client.handler;

import com.limyel.bridge.client.handler.local.DataHandler;
import com.limyel.bridge.client.utils.LocalChannelGroup;
import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterRequestPacket requestPacket = new RegisterRequestPacket("192.168.31.32", 5173, 25173);
        ctx.channel().writeAndFlush(requestPacket);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponsePacket responsePacket) throws Exception {
        LocalChannelGroup.INSTANCE.channelId = responsePacket.getChannelId();

        Bootstrap localBootstrap = new io.netty.bootstrap.Bootstrap();
        NioEventLoopGroup group = new NioEventLoopGroup();

        localBootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new ByteArrayDecoder());
                        pipeline.addLast(new ByteArrayEncoder());
                        pipeline.addLast(new DataHandler(responsePacket.getChannelId()));

                        LocalChannelGroup.INSTANCE.channelGroup.add(ch);
                        LocalChannelGroup.INSTANCE.channelMap.put(responsePacket.getChannelId(), ch);
                    }
                }).connect("192.168.31.32", 5173).addListener(future -> {
                    if (!future.isSuccess()) {
                        future.cause().printStackTrace();
                    }
                }).sync();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
