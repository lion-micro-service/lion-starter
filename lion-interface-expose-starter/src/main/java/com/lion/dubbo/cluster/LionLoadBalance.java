package com.lion.dubbo.cluster;

import com.lion.constant.DubboConstant;
import com.lion.dubbo.util.ClientRemoteAddressUtil;
import com.lion.utils.SpringContextUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.cluster.loadbalance.AbstractLoadBalance;
import org.apache.dubbo.rpc.cluster.loadbalance.RandomLoadBalance;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @description: 自定义负载均衡（用于解决开发环境服务乱串问题-优先调用本地开发机服务）
 * @author: mr.liu
 * @create: 2020-10-10 15:26
 **/
@SPI(LionLoadBalance.NAME)
@Log4j2
public class LionLoadBalance extends AbstractLoadBalance {

    public static final String NAME = "lionLoadBalance";

    private static volatile LionLoadBalanceMetadate lionLoadBalanceMetadate;

    @Override
    protected <T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        String ip = ClientRemoteAddressUtil.getClientRemoteAddress();
        if (StringUtils.hasText(ip)){
            for (Invoker<T> invoker1 : invokers){
                if (invoker1.getUrl().getHost().equals(ip)){
                    return invoker1;
                }
            }
        }
        for (Invoker<T> invoker1 : invokers){
            if (invoker1.getUrl().getHost().equals(getLionLoadBalanceMetadate().getDevelopmentIp())){
                return invoker1;
            }
        }
        return invokers.get(new Random().nextInt(invokers.size()-1));
    }

    private LionLoadBalanceMetadate getLionLoadBalanceMetadate(){
        synchronized (LionLoadBalance.class){
            if (Objects.isNull(lionLoadBalanceMetadate)){
                synchronized (LionLoadBalance.class) {
                    lionLoadBalanceMetadate = (LionLoadBalanceMetadate) SpringContextUtil.getBean("lionLoadBalanceMetadate");
                }
            }
        }
        return lionLoadBalanceMetadate;
    }

}
