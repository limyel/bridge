package com.limyel.bridge.common.protocol.response;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;

public class RegisterResponsePacket extends AbstractPacket {
    @Override
    public Byte getCommand() {
        return CommandConstant.REGISTER_RESPONSE;
    }
}
