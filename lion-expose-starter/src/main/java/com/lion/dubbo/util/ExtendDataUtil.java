package com.lion.dubbo.util;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;

public class ExtendDataUtil {
    public static void setExtendData(){
        ClientRemoteAddressUtil.setClientRemoteAddress();
        CurrentUserUtil.setCurrentUser();
//        TarceIdUtil.setTarceId();
    }
}
