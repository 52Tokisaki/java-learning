package com.lesama.interceptor;

import com.lesama.utils.CurrentHolder;
import com.lesama.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 获取请求路径，若包含/login 则放行
        // 在注册拦截器的时候将login路径排除了，所以这里不需要判断login路径
//        String requestURI = request.getRequestURI();
//        if (requestURI.contains("/login")){
//            log.info("登录请求");
//            return true;
//        }
        // 2. 获取请求头中的token
        String token = request.getHeader("token");

        // 3. 若token为空，则返回401状态码
        if (token == null || token.isEmpty()) {
            response.setStatus(401);
            return false;
        }

        // 4. 解析 token， 若token解析失败，则返回401状态码
        try {
            Claims claims = JwtUtil.parseToken(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
        } catch (Exception e) {
            log.error("token解析失败");
            response.setStatus(401);
            return false;
        }

        // 5. 解析token成功， 则放行
        return true;
    }
}
