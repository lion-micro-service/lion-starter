package com.lion.dubbo.util;

import com.lion.aop.log.SystemLogDataUtil;
import com.lion.constant.DubboConstant;
import com.lion.utils.id.SnowflakeUtil;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.web.embedded.tomcat.TomcatEmbeddedWebappClassLoader;

import java.util.Objects;

/**
 * 设置调用链id
 */
public class TarceIdUtil {
    public static void setTarceId(RpcContext rpcContext, Invocation invocation){
        Thread thread = Thread.currentThread();
        Long trackId = null;
        if (thread.getContextClassLoader() instanceof TomcatEmbeddedWebappClassLoader){
            trackId = SystemLogDataUtil.get().getTrackId();
        }else {
            if (invocation.getAttachments().containsKey(DubboConstant.TRACE_ID) && Objects.nonNull(invocation.get(DubboConstant.TRACE_ID))) {
                trackId = (Long) invocation.get(DubboConstant.TRACE_ID);
            }else if (Objects.nonNull(rpcContext.getAttachment(DubboConstant.TRACE_ID))){
                trackId = Long.valueOf(rpcContext.getAttachment(DubboConstant.TRACE_ID));
            }
        }
        if (Objects.isNull(trackId)){
            trackId = SnowflakeUtil.getId();
        }
        invocation.setAttachmentIfAbsent(DubboConstant.TRACE_ID,trackId);
        rpcContext.setAttachment(DubboConstant.TRACE_ID, trackId);
    }
}
