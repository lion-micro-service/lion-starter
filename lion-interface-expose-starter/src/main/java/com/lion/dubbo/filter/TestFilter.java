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
        CurrentUserUtil.tenant.remove();
        CurrentUserUtil.usernameThreadLocal.remove();
        CurrentUserUtil.tenant.remove();
        CurrentUserUtil.usernameThreadLocal.remove();
        CurrentTenantIdUtil.cleanThreadLocal();
        RpcContext.getServiceContext().clearAttachments();
        RpcContext.getServerContext().clearAttachments();
    }
}
