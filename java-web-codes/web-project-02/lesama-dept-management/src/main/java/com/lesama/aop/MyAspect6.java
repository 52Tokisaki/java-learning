package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class MyAspect6 {

    // 使用@annotation 指定切点
    @Before("@annotation(com.lesama.annotation.LogOperation)")
    public void before() {
        log.info("MyAspect6 before");
    }
}
