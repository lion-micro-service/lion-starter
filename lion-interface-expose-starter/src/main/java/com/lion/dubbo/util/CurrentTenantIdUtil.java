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

    public static ThreadLocal<Boolean> isGetTenantId = new ThreadLocal<>();

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

            if(Objects.isNull(tenantId) ){
                RpcContext context = RpcContext.getServiceContext();
                Object obj = context.getObjectAttachment(DubboConstant.TENANT_ID);
                if (Objects.nonNull(obj)) {
                    tenantId = (Long)obj;
                    threadLocal.set(tenantId);
                    return tenantId;
                }

            }
            if(Objects.isNull(tenantId) && (Objects.isNull(isGetTenantId.get()) || Objects.equals(false,isGetTenantId.get()))){
                tenantId = CurrentUserUtil.getCurrentUserTenantId(true);
                isGetTenantId.set(true);
            }
            if (Objects.nonNull(tenantId)) {
                threadLocal.set(tenantId);
            }
            return tenantId;
        }
    }

    public static void setTenantId(Long tenantId){
        if (Objects.isNull(tenantId)) {
            tenantId = getTenantId();
        }
        if(Objects.nonNull(tenantId)){
            threadLocal.set((Long)tenantId);
            RpcContext.getServiceContext().setObjectAttachment(DubboConstant.TENANT_ID,(Long)tenantId);
            com.lion.utils.CurrentUserUtil.tenant.set((Long) tenantId);
        }
    }

    public static void cleanThreadLocal(){
        threadLocal.remove();
        isGetTenantId.remove();
    }

}
