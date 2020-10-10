package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: mr.liu
 * @create: 2020-10-10 15:05
 **/
public class ClientRemoteAddressUtil {

    /**
     * 获取客户端IP
     * @return
     */
    public static String getClientRemoteAddress(){
        RpcContext rpcContext = RpcContext.getContext();
        Thread thread = Thread.currentThread();
        if (thread.getContextClassLoader() instanceof TomcatEmbeddedWebappClassLoader){
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request.getRemoteAddr();
        }else {
            String ip = String.valueOf(rpcContext.get(DubboConstant.CLIENT_REMOTE_ADDRESS));
            if (StringUtils.hasText(ip)){
                return ip;
            }
        }
        return "";
    }
}
