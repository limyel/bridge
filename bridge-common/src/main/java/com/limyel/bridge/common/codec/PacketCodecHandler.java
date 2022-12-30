package com.limyel.bridge.common.codec;

import com.limyel.bridge.common.protocol.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, AbstractPacket> {

    private static volatile PacketCodecHandler INSTANCE = new PacketCodecHandler();

    private PacketCodecHandler() {

    }

    public static PacketCodecHandler getInstance() {
        if (INSTANCE == null) {
            synchronized (PacketCodecHandler.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PacketCodecHandler();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, AbstractPacket packet, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.channel().alloc().ioBuffer();
        PacketCodec.getInstance().encode(byteBuf, packet);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodec.getInstance().decode(byteBuf));
    }
}
