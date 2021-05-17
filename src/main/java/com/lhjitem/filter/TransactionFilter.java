package com.lhjitem.filter;

import com.lhjitem.utils.JdbcUtils;
import com.lhjitem.utils.WebUtils;

import javax.servlet.*;
import java.io.IOException;

public class TransactionFilter implements Filter {
    //这个filter类就是为了统一给所有service加上try-catch
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
            JdbcUtils.commitAndClose();//没有错就提交事务并关闭
        } catch (Exception e) {
            JdbcUtils.rollbackAndClose();//有异常就回滚事务并关闭
            e.printStackTrace();
            //将异常再抛给tomcat服务器，要不然服务器接收不到这个异常，也就进不了那个错误500的页面了
            throw new RuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
