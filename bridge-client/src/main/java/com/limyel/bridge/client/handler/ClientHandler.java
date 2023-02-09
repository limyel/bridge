package com.limyel.bridge.client.handler;

import com.limyel.bridge.entity.ProxyInfo;
import com.limyel.bridge.protocol.packet.RegisterRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author limyel
 * @since 2023-02-07 22:49
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ProxyInfo proxyInfo = new ProxyInfo();
        proxyInfo.setProxyHost("192.168.31.98");
        proxyInfo.setProxyPort(22);
        proxyInfo.setRemotePort(10022);

        RegisterRequestPacket packet = new RegisterRequestPacket();
        packet.setProxyInfo(proxyInfo);

        System.out.println("ClientHandler " + ctx.channel().id().asLongText());

        ctx.writeAndFlush(packet);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.getInstance().getParentChannelMap().remove(ctx.channel().id().asLongText());
        System.out.println("Client Inactive " + ctx.channel().id().asLongText());
    }

}
