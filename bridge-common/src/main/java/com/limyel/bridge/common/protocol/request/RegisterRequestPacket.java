package com.limyel.bridge.common.protocol.request;

import com.limyel.bridge.common.constant.CommandConstant;
import com.limyel.bridge.common.protocol.AbstractPacket;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RegisterRequestPacket extends AbstractPacket {

    private List<RegisterItem> registerItemList;

    @Override
    public Byte getCommand() {
        return CommandConstant.REGISTER_REQUEST;
    }
}
