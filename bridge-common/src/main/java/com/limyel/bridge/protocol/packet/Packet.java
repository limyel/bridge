package com.limyel.bridge.protocol.packet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author limyel
 * @since 2023-02-07 16:57
 */
@Data
public abstract class Packet {

    private Byte version = 1;

    @JsonIgnore
    public abstract Byte getCommand();

}
