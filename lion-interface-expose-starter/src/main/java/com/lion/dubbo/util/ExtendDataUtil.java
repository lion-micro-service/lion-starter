package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.Invocation;

import java.util.Objects;

public class ExtendDataUtil {
    public static void setExtendData(Invocation invocation){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        Object username = invocation.getObjectAttachments().get(DubboConstant.USERNAME);
        if (Objects.nonNull(username)) {
            CurrentUserUtil.setCurrentUser((String) username);
        }

        Object tenantId = com.lion.utils.CurrentUserUtil.tenant.get();
        if (Objects.isNull(tenantId)) {
            tenantId =  invocation.getObjectAttachments().get(DubboConstant.TENANT_ID);
        }
        if (Objects.isNull(tenantId)) {
            CurrentTenantIdUtil.setTenantId((Long) tenantId);
        }
        if(Objects.nonNull(tenantId)){
            com.lion.utils.CurrentUserUtil.tenant.set((Long) tenantId);
        }
//        TarceIdUtil.setTarceId();
    }
}
