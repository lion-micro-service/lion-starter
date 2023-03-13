package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.Invocation;

import java.util.Objects;

public class ExtendDataUtil {
    public static void setExtendData(Invocation invocation){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        Object username = invocation.getObjectAttachments().get(DubboConstant.USERNAME);
        CurrentUserUtil.setCurrentUser((String) username);

        Object tenantId = com.lion.utils.CurrentUserUtil.tenant.get();
        if (Objects.isNull(tenantId)) {
            tenantId =  invocation.getObjectAttachments().get(DubboConstant.TENANT_ID);
        }
        if (Objects.isNull(tenantId)) {
            tenantId =  com.lion.utils.CurrentUserUtil.getCurrentUserTenantId();
        }
        if (Objects.nonNull(tenantId)) {
            CurrentTenantIdUtil.setTenantId((Long) tenantId);
        }
//        TarceIdUtil.setTarceId();
    }
}
