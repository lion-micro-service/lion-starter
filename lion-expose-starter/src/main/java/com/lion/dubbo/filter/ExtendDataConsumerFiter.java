package com.lion.dubbo.filter;

import com.lion.constant.DubboConstant;
import com.lion.dubbo.util.ClientRemoteAddressUtil;
import com.lion.dubbo.util.ExtendDataUtil;
import com.lion.dubbo.util.TarceIdUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.scheduling.support.SimpleTriggerContext;

import javax.print.DocFlavor;
import java.util.Objects;
import java.util.UUID;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:07
 **/
@Activate
public class ExtendDataConsumerFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getServiceContext();
        ExtendDataUtil.setExtendData(rpcContext,invocation);
        Result result = invoker.invoke(invocation);
        ExtendDataUtil.removeAttachment(rpcContext,DubboConstant.CLIENT_REMOTE_ADDRESS,DubboConstant.TRACE_ID);
        return result;
    }


}
