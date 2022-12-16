package com.limyel.bridge.client.handler;

import com.limyel.bridge.client.handler.local.Datahandler;
import com.limyel.bridge.client.utils.LocalChannelGroup;
import com.limyel.bridge.common.codec.PacketDecoder;
import com.limyel.bridge.common.codec.PacketEncoder;
import com.limyel.bridge.common.handler.IMIdleStateHandler;
import com.limyel.bridge.common.protocol.request.ConnectRequestPacket;
import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import org.omg.CORBA.portable.ResponseHandler;

public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterRequestPacket requestPacket = new RegisterRequestPacket("192.168.31.32", 8000, 7001);
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
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(new ByteArrayDecoder());
                        pipeline.addLast(new Datahandler());
                        pipeline.addLast(new ByteArrayEncoder());

                        pipeline.addLast(new ByteArrayEncoder());

                        LocalChannelGroup.INSTANCE.localChannel = ch;
                    }
                }).connect("192.168.31.32", 8000);
    }
}
