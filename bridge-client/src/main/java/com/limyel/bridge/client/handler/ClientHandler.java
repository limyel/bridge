package com.limyel.bridge.client.handler;

import com.limyel.bridge.entity.ProxyInfo;
import com.limyel.bridge.protocol.packet.RegisterRequestPacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;
import java.util.List;

/**
 * @author limyel
 * @since 2023-02-07 22:49
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    private List<ProxyInfo> proxyInfoList;

    private String password;

    public ClientHandler(List<ProxyInfo> proxyInfoList, String password) {
        this.proxyInfoList = proxyInfoList;
        this.password = password;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        RegisterRequestPacket packet = new RegisterRequestPacket();
        packet.setProxyInfoList(proxyInfoList);
        packet.setPassword(password);

        System.out.println(new Date() + "ClientHandler " + ctx.channel().id().asLongText());

        ctx.writeAndFlush(packet);

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelUtil.getInstance().getParentChannelMap().remove(ctx.channel().id().asLongText());
        System.out.println(new Date() + "Client Inactive " + ctx.channel().id().asLongText());
    }

}
