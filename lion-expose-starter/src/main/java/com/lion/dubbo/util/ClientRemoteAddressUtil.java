package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
    public static String getClientRemoteAddress(RpcContext rpcContext, Invocation invocation){
        Thread thread = Thread.currentThread();
        if (thread.getContextClassLoader() instanceof TomcatEmbeddedWebappClassLoader){
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request.getRemoteAddr();
        }else {
            String ip = null;
            if (invocation.getAttachments().containsKey(DubboConstant.CLIENT_REMOTE_ADDRESS) && Objects.nonNull(invocation.get(DubboConstant.CLIENT_REMOTE_ADDRESS))) {
                ip = String.valueOf(invocation.get(DubboConstant.CLIENT_REMOTE_ADDRESS));
            }else if (Objects.nonNull(rpcContext.get(DubboConstant.CLIENT_REMOTE_ADDRESS))){
                ip = String.valueOf(invocation.get(DubboConstant.CLIENT_REMOTE_ADDRESS));
            }
            if (StringUtils.hasText(ip)){
                return ip;
            }
        }
        return "";
    }

    /**
     * 设置客户端ip到调用扩展参数
     * @param rpcContext
     * @param invocation
     */
    public static void setClientRemoteAddress(RpcContext rpcContext, Invocation invocation){
        String ip = getClientRemoteAddress(rpcContext, invocation);
        invocation.setAttachmentIfAbsent(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
        rpcContext.setAttachment(DubboConstant.CLIENT_REMOTE_ADDRESS,ip);
    }
}
