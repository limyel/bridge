package com.limyel.bridge.client.handler;

import com.limyel.bridge.client.config.ClientConfig;
import com.limyel.bridge.client.entity.ProxyInfo;
import com.limyel.bridge.client.handler.local.DataHandler;
import com.limyel.bridge.client.net.BridgeClient;
import com.limyel.bridge.common.protocol.request.RegisterItem;
import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import com.limyel.bridge.common.utils.ChannelUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        List<ProxyInfo> proxyInfoList = ClientConfig.getInstance().getProxyInfo();
        List<RegisterItem> registerItemList = proxyInfoList.stream().map(proxyInfo -> {
            RegisterItem item = new RegisterItem();
            item.setRemotePort(proxyInfo.getRemoteProxyPort());
            item.setLocalProxyHost(proxyInfo.getLocalProxyHost());
            item.setLocalProxyPort(proxyInfo.getLocalProxyPort());
            return item;
        }).collect(Collectors.toList());
        RegisterRequestPacket requestPacket = new RegisterRequestPacket(registerItemList);
        ctx.channel().writeAndFlush(requestPacket);
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponsePacket responsePacket) throws Exception {
        BridgeClient localClient = new BridgeClient();
        localClient.connect(responsePacket.getLocalProxyHost(), responsePacket.getLocalProxyPort(), new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelUtil.getInstance().getChannelGroup().add(ch);
                ChannelUtil.getInstance().getChannelMap().put(responsePacket.getChannelId(), ch);

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
                pipeline.addLast(new DataHandler(responsePacket.getChannelId()));
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
