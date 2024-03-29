package com.lion.dubbo.cluster;

import com.lion.constant.DubboConstant;
import com.lion.dubbo.util.ClientRemoteAddressUtil;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * @description: 自定义负载均衡（用于解决开发环境服务乱串问题-优先调用本地开发机服务）
 * @author: mr.liu
 * @create: 2020-10-10 15:26
 **/
@SPI(LionLoadBalance.NAME)
public class LionLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "lionLoadBalance";

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        RpcContext rpcContext = RpcContext.getServiceContext();
        Invoker<T> invoker = null;
        String ip = ClientRemoteAddressUtil.getClientRemoteAddress(rpcContext,invocation);
        if (StringUtils.hasText(ip)){
            invocation.setAttachmentIfAbsent(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
            rpcContext.setAttachment(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
            for (Invoker<T> invoker1 : invokers){
                if (invoker1.getUrl().getHost().equals(ip)){
                    invoker = invoker1;
                    break;
                }
            }
        }else {
            invoker = invokers.get(new Random().nextInt(invokers.size()-1)%invokers.size()-1);
        }
        return invoker;
    }

}
