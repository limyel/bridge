package com.limyel.bridge.common.protocol.request;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;

public class HeartBeatRequestPacket extends AbstractPacket {

    @Override
    public Byte getCommand() {
        return CommandConstant.HEART_BEAT_REQUEST;
    }
}
