package com.limyel.bridge.server.handler;

import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import com.limyel.bridge.server.handler.proxy.DataHandler;
import com.limyel.bridge.server.net.BridgeServer;
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
        requestPacket.getRegisterItemList().forEach(registerItem -> {
            BridgeServer proxyServer = new BridgeServer();
            proxyServer.bind(registerItem.getRemotePort(), new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();

                    pipeline.addLast(new ByteArrayDecoder());
                    pipeline.addLast(new ByteArrayEncoder());
                    pipeline.addLast(new DataHandler());

                    ChannelUtil.getInstance().getChannelGroup().add(ch);
                    ChannelUtil.getInstance().getMap().put(ch.id().asLongText(), registerItem.getUri());
                }
            });
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
