package com.lion.dubbo.filter;

import com.lion.dubbo.util.ExtendDataUtil;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:07
 **/
@Activate
public class ExtendDataConsumerFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        ExtendDataUtil.setExtendData();
        Result result = invoker.invoke(invocation);
        RpcContext.getServiceContext().clearAttachments();
        return result;
    }


}
