package com.lion.dubbo.util;

public class ExtendDataUtil {
    public static void setExtendData(){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        CurrentUserUtil.setCurrentUser();
        CurrentTenantIdUtil.setTenantId();
//        TarceIdUtil.setTarceId();
    }
}
