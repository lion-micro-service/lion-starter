package com.lion.dubbo.cluster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LionLoadBalanceMetadate {

    @Value("${dubbo.development_ip:''}")
    private String developmentIp;

    public String getDevelopmentIp() {
        return developmentIp;
    }
}
