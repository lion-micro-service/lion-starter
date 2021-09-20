package com.lion.dubbo.util;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.RpcContext;

public class ExtendDataUtil {
    public static void setExtendData(RpcContext rpcContext, Invocation invocation){
//        ClientRemoteAddressUtil.setClientRemoteAddress(rpcContext,invocation);
//        TarceIdUtil.setTarceId(rpcContext,invocation);
    }
    public static void removeAttachment(RpcContext rpcContext, String... keys){
        for (String key : keys){
            rpcContext.removeAttachment(key);
        }
    }
}
