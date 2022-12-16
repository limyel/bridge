package com.limyel.bridge.common.protocol.response;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterResponsePacket extends AbstractPacket {

    private String channelId;

    @Override
    public Byte getCommand() {
        return CommandConstant.REGISTER_RESPONSE;
    }
}
