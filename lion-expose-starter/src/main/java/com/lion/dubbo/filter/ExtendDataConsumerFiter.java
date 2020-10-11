package com.lion.dubbo.filter;

import com.lion.constant.DubboConstant;
import com.lion.dubbo.util.ClientRemoteAddressUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @description: 传递其它信息（客户端ip地址，调用链标识码）
 * @author: mr.liu
 * @create: 2020-10-10 14:07
 **/
public class ExtendDataConsumerFiter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        if(!invocation.getAttachments().containsKey(DubboConstant.CLIENT_REMOTE_ADDRESS)){
            String ip = ClientRemoteAddressUtil.getClientRemoteAddress();
            invocation.setAttachmentIfAbsent(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
            rpcContext.set(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
        }
        Result result = invoker.invoke(invocation);
        rpcContext.remove(DubboConstant.CLIENT_REMOTE_ADDRESS);
        return result;
    }
}
