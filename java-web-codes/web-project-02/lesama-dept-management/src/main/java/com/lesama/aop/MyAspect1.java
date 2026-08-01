package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
// 演示切面类的五种通知类型
public class MyAspect1 {

    @Pointcut("execution(* com.lesama.service.impl.*.*(..))")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void before() {
        log.info("MyAspect1 before");
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("MyAspect1 around before");
        Object result = pjp.proceed();
        log.info("MyAspect1 around after");
        return result;
    }

    @After("pointcut()")
    public void after() {
        log.info("MyAspect1 after");
    }

    @AfterReturning("pointcut()")
    public void afterReturning() {
        log.info("MyAspect1 afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing() {
        log.info("MyAspect1 afterThrowing");
    }
}
