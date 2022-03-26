package com.lion.dubbo.util;

import com.lion.constant.DubboConstant;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class CurrentUserUtil {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    private static String getCurrentUser(){
        String username = null;
        if (Objects.isNull(threadLocal.get())) {
            username = com.lion.utils.CurrentUserUtil.getCurrentUserUsername();
        }
        if (!StringUtils.hasText(username)) {
            username = threadLocal.get();
        }
        if (StringUtils.hasText(username) && Objects.isNull(threadLocal.get())) {
            threadLocal.set(username);
        }
        return username;
    }

    /**
     * 设置当前登录用户
     */
    public static void setCurrentUser(){
        String username = getCurrentUser();
        if (StringUtils.hasText(username)) {
            RpcContext.getServiceContext().setAttachment(DubboConstant.USERNAME,username);
        }
    }
}