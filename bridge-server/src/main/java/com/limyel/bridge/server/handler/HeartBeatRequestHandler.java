package com.limyel.bridge.server.handler;

import com.limyel.bridge.common.protocol.request.HeartBeatRequestPacket;
import com.limyel.bridge.common.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    private static volatile HeartBeatRequestHandler INSTANCE;

    private HeartBeatRequestHandler() {

    }

    public static HeartBeatRequestHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (HeartBeatRequestHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HeartBeatRequestHandler();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

}
