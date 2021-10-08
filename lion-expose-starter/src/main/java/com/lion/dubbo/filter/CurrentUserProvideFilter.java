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
@Activate
public class CurrentUserProvideFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext rpcContext = RpcContext.getServiceContext();
        String username = invocation.getAttachments().get(DubboConstant.USERNAME);
        if(StringUtils.hasText(username)){
            rpcContext.setAttachment(DubboConstant.USERNAME,username);
        }else {
            rpcContext.removeAttachment(DubboConstant.USERNAME);
        }
        Result result = invoker.invoke(invocation);
        return result;
    }
}