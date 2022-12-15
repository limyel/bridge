package com.limyel.bridge.common.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class AbstractPacket {

    /**
     * 协议版本
     */
    protected Byte version = 1;

    /**
     * 指令
     * @return
     */
    @JsonIgnore
    public abstract Byte getCommand();
}
