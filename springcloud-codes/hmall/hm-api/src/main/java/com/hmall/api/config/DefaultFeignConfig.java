package com.hmall.api.config;

import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean // Feign 调用其他服务 传递userInfo信息
    public RequestInterceptor requestInterceptor() {
        return template -> {
            Long userInfo = UserContext.getUser();

            if (userInfo != null) {
                template.header("user-info", userInfo.toString());
            }
        };
    }
}
