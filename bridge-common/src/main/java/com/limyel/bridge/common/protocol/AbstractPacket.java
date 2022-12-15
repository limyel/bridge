package com.limyel.bridge.common.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public abstract class AbstractPacket {

    protected Byte version = 1;

    @JsonIgnore
    public abstract Byte getCommand();
}
