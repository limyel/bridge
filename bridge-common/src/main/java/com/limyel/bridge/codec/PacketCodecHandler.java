package com.limyel.bridge.codec;

import com.limyel.bridge.protocol.PacketCodec;
import com.limyel.bridge.protocol.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * @author limyel
 * @since 2023-02-07 17:13
 */
@ChannelHandler.Sharable
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {

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
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, List<Object> list) throws Exception {
        ByteBuf byteBuf = channelHandlerContext.channel().alloc().ioBuffer();
        PacketCodec.getInstance().encode(byteBuf, packet);
        System.out.println(byteBuf.getInt(byteBuf.readerIndex()));
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        list.add(PacketCodec.getInstance().decode(byteBuf));
    }

}
