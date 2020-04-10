package com.lnmj.common.filter;

/**
 * @Auther: panlin
 * @Date: 2019/6/14 12:30
 * @Description:
 */

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Cache-Control","no-cache");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}