package com.lion.dubbo.filter;

import com.lion.dubbo.util.CurrentTenantIdUtil;
import com.lion.dubbo.util.CurrentUserUtil;
import com.lion.dubbo.util.ExtendDataUtil;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:07
 **/
@Activate
public class ExtendDataProviderFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ExtendDataUtil.setExtendData(invocation);
        Result result = invoker.invoke(invocation);
        RpcContext.getServiceContext().clearAttachments();
        RpcContext.getServerContext().clearAttachments();
        RpcContext.getCurrentServiceContext().clearAttachments();
        com.lion.utils.CurrentUserUtil.tenant.remove();
        com.lion.utils.CurrentUserUtil.usernameThreadLocal.remove();
        CurrentUserUtil.cleanThreadLocal();
        CurrentTenantIdUtil.cleanThreadLocal();
        return result;
    }
}
