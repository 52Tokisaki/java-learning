package com.example;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderConfig {

    @Bean
    @ConditionalOnClass(name = "io.jsonwebtoken.Jwts") // 只有io.jsonwebtoken.Jwts类存在时，才会创建HeaderParser对象
    public HeaderParser headerParser(){
        return new HeaderParser();
    }

    @Bean
//    @ConditionalOnMissingBean // 只有HeaderGenerator类不存在时，才会创建HeaderGenerator对象
    @ConditionalOnProperty(name = "name", havingValue = "lesama") // 只有name属性的值为lesama时，才会创建HeaderGenerator对象
    public HeaderGenerator headerGenerator(){
        return new HeaderGenerator();
    }
}
