package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.Invocation;

public class ExtendDataUtil {
    public static void setExtendData(Invocation invocation){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        Object username = invocation.getObjectAttachments().get(DubboConstant.USERNAME);
        CurrentUserUtil.setCurrentUser((String) username);
        Object tenantId = invocation.getObjectAttachments().get(DubboConstant.TENANT_ID);
        CurrentTenantIdUtil.setTenantId((Long) tenantId);
//        TarceIdUtil.setTarceId();
    }
}
