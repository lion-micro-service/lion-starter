package com.lion.dubbo.filter;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:09
 **/
@Activate(group = {CommonConstants.PROVIDER},order = Integer.MIN_VALUE)
public class ExtendDataProvideFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if(invocation.getAttachments().containsKey(DubboConstant.CLIENT_REMOTE_ADDRESS)){
            String ip = invocation.getAttachments().get(DubboConstant.CLIENT_REMOTE_ADDRESS);
            RpcContext.getContext().set(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
        }
        Result result = invoker.invoke(invocation);
        RpcContext.getContext().remove(DubboConstant.CLIENT_REMOTE_ADDRESS);
        return result;
    }
}
