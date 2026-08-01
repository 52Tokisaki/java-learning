package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Order(1)
// 演示切面类的五种通知类型
public class MyAspect4 {

    @Before("execution(* com.lesama.service.impl.*.*(..))")
    public void before() {
        log.info("MyAspect4 before");
    }

    @After("execution(* com.lesama.service.impl.*.*(..))")
    public void after() {
        log.info("MyAspect4 after");
    }

}
