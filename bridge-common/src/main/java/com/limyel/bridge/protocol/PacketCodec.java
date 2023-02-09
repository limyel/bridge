package com.limyel.bridge.protocol;

import com.limyel.bridge.protocol.packet.*;
import com.limyel.bridge.serialize.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

/**
 * @author limyel
 * @since 2023-02-07 17:10
 */
public class PacketCodec {

    public static final int MAGIC_NUMBER = 0x594;

    private static volatile PacketCodec INSTANCE = new PacketCodec();

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.REGISTER_REQUEST, RegisterRequestPacket.class);
        packetTypeMap.put(Command.REGISTER_RESPONSE, RegisterResponsePacket.class);
        packetTypeMap.put(Command.PROXY_DATA_REQUEST, ProxyDataRequestPacket.class);
        packetTypeMap.put(Command.PROXY_DATA_RESPONSE, ProxyDataResponsePacket.class);
        packetTypeMap.put(Command.INACTIVE_REQUEST, InactiveRequestPacket.class);
        packetTypeMap.put(Command.CONNECTED, ConnectedPacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = Serializer.DEFAULT;
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

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

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    public void encode(ByteBuf byteBuf, Packet packet) {
        // 1. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeShort(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf byteBuf) {
        // 跳过魔数
        byteBuf.skipBytes(2);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> packetType = packetTypeMap.get(command);
        Serializer serializer = serializerMap.get(serializeAlgorithm);

        return serializer.deserialize(packetType, bytes);
    }

}
