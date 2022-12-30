package com.limyel.bridge.common.codec;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.constant.SerializeAlgorithmConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import com.limyel.bridge.common.protocol.common.DataPacket;
import com.limyel.bridge.common.protocol.request.DisconnectRequestPacket;
import com.limyel.bridge.common.protocol.request.HeartBeatRequestPacket;
import com.limyel.bridge.common.protocol.request.RegisterRequestPacket;
import com.limyel.bridge.common.protocol.response.HeartBeatResponsePacket;
import com.limyel.bridge.common.protocol.response.RegisterResponsePacket;
import com.limyel.bridge.common.serializer.Serializer;
import com.limyel.bridge.common.serializer.impl.JSONSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;

import java.util.HashMap;
import java.util.Map;

@ChannelHandler.Sharable
public class PacketCodec {

    private static volatile PacketCodec INSTANCE = new PacketCodec();

    public static PacketCodec getInstance() {
        if (INSTANCE == null) {
            synchronized (PacketCodec.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PacketCodec();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 魔数
     */
    public static final int MAGIC_NUMBER = 0x12345678;

    private Map<Byte, Class<? extends AbstractPacket>> packetTypeMap;

    private Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(CommandConstant.REGISTER_REQUEST, RegisterRequestPacket.class);
        packetTypeMap.put(CommandConstant.REGISTER_RESPONSE, RegisterResponsePacket.class);
        packetTypeMap.put(CommandConstant.HEART_BEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(CommandConstant.HEART_BEAT_RESPONSE, HeartBeatResponsePacket.class);
        packetTypeMap.put(CommandConstant.DATA, DataPacket.class);
        packetTypeMap.put(CommandConstant.DISCONNECT_REQUEST, DisconnectRequestPacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(JSONSerializer.INSTANCE.getSerializeAlgorithm(), JSONSerializer.INSTANCE);
    }

    public void encode(ByteBuf byteBuf, AbstractPacket packet) {
        byte[] bytes = serializerMap.get(SerializeAlgorithmConstant.DEFAULT).serialize(packet);

        // 魔数
        byteBuf.writeInt(MAGIC_NUMBER);
        // 协议版本
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法
        byteBuf.writeByte(SerializeAlgorithmConstant.DEFAULT);
        // 指令
        byteBuf.writeByte(packet.getCommand());
        // 数据包长度
        byteBuf.writeInt(bytes.length);
        // 数据包
        byteBuf.writeBytes(bytes);
    }

    public AbstractPacket decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(4);
        // 跳过协议版本
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();
        // 指令
        byte command = byteBuf.readByte();
        // 数据包长度
        int length = byteBuf.readInt();
        // 数据包
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends AbstractPacket> packetType = getPacketType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);
        if (packetType != null && serializer != null) {
            return serializer.deserialize(packetType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte algorithm) {
        return serializerMap.get(algorithm);
    }

    private Class<? extends AbstractPacket> getPacketType(byte command) {
        return packetTypeMap.get(command);
    }

}
