package com.limyel.bridge.common.protocol.response;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;

public class HeartBeatResponsePacket extends AbstractPacket {
    @Override
    public Byte getCommand() {
        return CommandConstant.HEART_BEAT_RESPONSE;
    }
}
