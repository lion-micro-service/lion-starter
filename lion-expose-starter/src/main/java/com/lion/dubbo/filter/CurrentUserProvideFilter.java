package com.lion.dubbo.filter;

import com.lion.constant.DubboConstant;
import com.lion.utils.CurrentUserUtil;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.util.StringUtils;

/**
 * @description: dubbo传递当前登陆用户
 * @author: Mr.Liu
 * @create: 2020-02-17 19:08
 */
public class CurrentUserProvideFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getContext();
        String username = invocation.getAttachments().get(DubboConstant.USERNAME);
        if(!StringUtils.hasText(username)){
            username = CurrentUserUtil.getCurrentUserUsername();
        }
        if (StringUtils.hasText(username)){
            invocation.setAttachmentIfAbsent(DubboConstant.USERNAME,username);
            rpcContext.set(DubboConstant.USERNAME,username);
        }else {
            rpcContext.remove(DubboConstant.USERNAME);
        }
        Result result = invoker.invoke(invocation);
        rpcContext.remove(DubboConstant.USERNAME);
        return result;
    }
}