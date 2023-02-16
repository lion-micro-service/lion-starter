package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import com.lion.utils.CurrentUserUtil;
import org.apache.dubbo.rpc.RpcContext;

import java.util.Objects;

/**
 * @author anders
 * @Description
 * @date 2023/2/16 11:36
 */
public class CurrentTenantIdUtil {

    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 获取租户id
     * @return
     */
    public static Long getTenantId(){
        Long tenantId = null;
        if(Objects.nonNull(threadLocal) && Objects.nonNull(threadLocal.get())){
            return threadLocal.get();
        }else {
            if(Objects.nonNull(CurrentUserUtil.tenant) && Objects.nonNull(CurrentUserUtil.tenant.get())){
                tenantId = CurrentUserUtil.tenant.get();
            }
            if(Objects.isNull(tenantId)){
                tenantId = CurrentUserUtil.getCurrentUserTenantId(true);
            }
            threadLocal.set(tenantId);
            return tenantId;
        }
    }

    public static void setTenantId(){
        Long tenantId = getTenantId();
        if(Objects.nonNull(tenantId)){
            RpcContext.getServiceContext().setAttachment(DubboConstant.TENANT_ID,tenantId);
        }
    }

    public static void cleanThreadLocal(){
        threadLocal.remove();
    }

}