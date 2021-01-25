package com.lion.dubbo.filter;

import com.lion.constant.DubboConstant;
import com.lion.dubbo.util.ExtendDataUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.util.UUID;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:09
 **/
public class ExtendDataProvideFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        ExtendDataUtil.setExtendData(rpcContext,invocation);
        Result result = invoker.invoke(invocation);
        ExtendDataUtil.removeAttachment(rpcContext,DubboConstant.CLIENT_REMOTE_ADDRESS,DubboConstant.TRACE_ID);
        return result;
    }
}
