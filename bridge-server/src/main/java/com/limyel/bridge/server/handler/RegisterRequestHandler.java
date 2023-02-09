package com.limyel.bridge.server.handler;

import com.limyel.bridge.entity.ProxyInfo;
import com.limyel.bridge.protocol.packet.RegisterRequestPacket;
import com.limyel.bridge.protocol.packet.RegisterResponsePacket;
import com.limyel.bridge.server.net.BeidgeServer;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * @author limyel
 * @since 2023-02-07 22:54
 */
public class RegisterRequestHandler extends SimpleChannelInboundHandler<RegisterRequestPacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.getInstance().getChannelMap().put(ctx.channel().id().asLongText(), ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterRequestPacket msg) throws Exception {
        for (ProxyInfo proxyInfo: msg.getProxyInfoList()) {
            System.out.println(proxyInfo.getUri());
            BeidgeServer proxyServer = new BeidgeServer();
            proxyServer.bind(proxyInfo.getRemotePort(), new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ByteArrayDecoder());
                    ch.pipeline().addLast(new ByteArrayEncoder());
                    ch.pipeline().addLast(new ProxyHandler(proxyInfo.getUri(), ctx.channel().id().asLongText()));

                    ChannelUtil.getInstance().getChannelGroup().add(ch);
                }
            });
        }
    }
}
