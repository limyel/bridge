package com.limyel.bridge.common.protocol.common;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DataPacket extends AbstractPacket {

    private byte[] data;

    private String channelId;

    @Override
    public Byte getCommand() {
        return CommandConstant.DATA;
    }
}
