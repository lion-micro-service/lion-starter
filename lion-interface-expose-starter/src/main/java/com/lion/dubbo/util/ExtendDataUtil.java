package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.Invocation;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class ExtendDataUtil {
    public static void setExtendData(Invocation invocation){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        Object username = invocation.getObjectAttachments().get(DubboConstant.USERNAME);
        if (Objects.nonNull(username) && StringUtils.hasText((String) username)) {
            CurrentUserUtil.setCurrentUser((String) username);
        }

        Object tenantId = invocation.getObjectAttachments().get(DubboConstant.TENANT_ID);
        if (Objects.nonNull(tenantId)) {
            CurrentTenantIdUtil.setTenantId((Long) tenantId);
        }
//        TarceIdUtil.setTarceId();
    }
}
