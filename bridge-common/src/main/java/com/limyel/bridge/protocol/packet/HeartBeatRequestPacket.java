package com.limyel.bridge.protocol.packet;

import com.limyel.bridge.protocol.Command;

/**
 * @author limyel
 * @since 2023-02-09 15:44
 */
public class HeartBeatRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.HEART_BEAT_REQUEST;
    }
}
