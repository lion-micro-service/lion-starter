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
        String developmentIpRange = getLionLoadBalanceMetadate().getDevelopmentIpRange();
        String[] ips = null;
        if (Objects.nonNull(developmentIpRange) ) {
            if ( developmentIpRange.indexOf("-")>-1) {
                ips = developmentIpRange.split("-");
            }
        }
        if (Objects.nonNull(developmentIpRange) ) {
            for (Invoker<T> invoker1 : invokers) {
                if (Objects.nonNull(ips) && ips.length == 2) {
                    if (ipExistsInRange(invoker1.getUrl().getHost(), ips[0], ips[1])) {
                        return invoker1;
                    }
                } else {
                    if (invoker1.getUrl().getHost().equals(developmentIpRange)) {
                        return invoker1;
                    }
                }
            }
        }

        return invokers.get(new Random().nextInt(invokers.size()-1));
    }

    private long getIp2long(String ip) {
        ip = ip.trim();
        String[] ips = ip.split("\\.");
        long ip1 = Integer.parseInt(ips[0]);
        long ip2 = Integer.parseInt(ips[1]);
        long ip3 = Integer.parseInt(ips[2]);
        long ip4 = Integer.parseInt(ips[3]);
        long ip2long =1L* ip1 * 256 * 256 * 256 + ip2 * 256 * 256 + ip3 * 256 + ip4;
        return ip2long;
    }

    private boolean ipExistsInRange(String ip, String startIP, String endIP) {
        return (getIp2long(startIP)<=getIp2long(ip)) && (getIp2long(ip)<=getIp2long(endIP));
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
