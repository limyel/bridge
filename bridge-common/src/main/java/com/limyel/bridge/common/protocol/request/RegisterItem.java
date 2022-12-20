package com.limyel.bridge.common.protocol.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterItem {

    /**
     * 被代理的地址
     */
    private String localProxyHost;

    /**
     * 被代理的端口
     */
    private int localProxyPort;

    /**
     * 远程暴露的端口
     */
    private int remotePort;

    @JsonIgnore
    public String getUri() {
        return localProxyHost + ":" + localProxyPort;
    }

}
