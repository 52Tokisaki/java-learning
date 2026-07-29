package com.lesama.filter;

import com.lesama.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        // 1. 获取请求路径，若包含/login 则放行
        String requestURI = httpServletRequest.getRequestURI();
        if (requestURI.contains("/login")){
            log.info("登录请求");
            chain.doFilter(request, response);
            return;
        }

        // 2. 获取请求头中的token
        String token = httpServletRequest.getHeader("token");
        log.info("token: {}",token);

        // 3. 若token为空，则返回401状态码
        if (token == null || token.isEmpty()) {
            httpServletResponse.setStatus(401);
            return;
        }

        // 4. 解析 token， 若token解析失败，则返回401状态码
        try {
            JwtUtil.parseToken(token);
        } catch (Exception e) {
            log.error("token解析失败");
            httpServletResponse.setStatus(401);
            return;
        }

        // 5. 解析token成功， 则放行
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
