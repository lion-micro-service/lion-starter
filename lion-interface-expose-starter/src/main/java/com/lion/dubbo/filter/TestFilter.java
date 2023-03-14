package com.lion.dubbo.filter;

import com.lion.dubbo.util.CurrentTenantIdUtil;
import com.lion.utils.CurrentUserUtil;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author anders
 * @Description
 * @date 2023/3/10 15:47
 */
@Component
public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest,servletResponse);
        RpcContext.getServiceContext().clearAttachments();
        RpcContext.getServerContext().clearAttachments();
        RpcContext.getCurrentServiceContext().clearAttachments();
        com.lion.utils.CurrentUserUtil.tenant.remove();
        com.lion.utils.CurrentUserUtil.usernameThreadLocal.remove();
        com.lion.dubbo.util.CurrentUserUtil.cleanThreadLocal();
        CurrentTenantIdUtil.cleanThreadLocal();
    }
}
