package com.hmall.gateway.filters;

import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;

import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    
    private final AuthProperties authProperties;
    private final JwtTool jwtTool;
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest(); // 获取request
        if (isExclude(request.getPath().value())) {
            return chain.filter(exchange); // 无需校验，直接放行
        }
        // 获取请求头
        List<String> authorization = request.getHeaders().get("authorization");
        String token = null;
        if (CollUtil.isNotEmpty(authorization)) {
            token = authorization.get(0);
        }
        
        Long userId = null; // 解析用户id
        try {
            userId = jwtTool.parseToken(token);
        } catch (Exception e) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete(); // 设置完成，返回响应
        }
        // 将用户id传递下去
        String userInfo = userId.toString();
        ServerWebExchange swe = exchange.mutate()
                .request(builder -> builder.header("user-info", userInfo).build())
                .build();
        // 放行, 将携带了用户信息的请求放行下去
        return chain.filter(swe);
    }

    private boolean isExclude(String path) {
        List<String> excludePaths = authProperties.getExcludePaths();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String excludePath : excludePaths) {
            if (antPathMatcher.match(excludePath, path)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
