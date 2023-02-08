package com.limyel.bridge.client.handler;

import com.limyel.bridge.client.net.BridgeClient;
import com.limyel.bridge.protocol.packet.RegisterResponsePacket;
import com.limyel.bridge.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author limyel
 * @since 2023-02-08 09:40
 */
public class RegisterResponseHandler extends SimpleChannelInboundHandler<RegisterResponsePacket> {

    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterResponsePacket msg) throws Exception {
        // todo 校验是否注册成功
        BridgeClient proxy = new BridgeClient();
        proxy.connect("192.168.31.98", Integer.parseInt(msg.getUri().split(":")[1]), new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ByteArrayDecoder());
                ch.pipeline().addLast(new ByteArrayEncoder());
                ch.pipeline().addLast(new ProxyHandler(msg.getUri(), msg.getChannelId()));

                ChannelUtil.getInstance().getChannelMap().put(msg.getChannelId(), ch);
                channelGroup.add(ch);
            }
        });
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        channelGroup.close();
    }
}
