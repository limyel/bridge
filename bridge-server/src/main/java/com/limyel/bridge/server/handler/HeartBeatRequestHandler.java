package com.limyel.bridge.server.handler;

import com.limyel.bridge.protocol.packet.HeartBeatRequestPacket;
import com.limyel.bridge.protocol.packet.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author limyel
 * @since 2023-02-09 15:50
 */
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
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {
        ctx.writeAndFlush(new HeartBeatResponsePacket());
    }
}
