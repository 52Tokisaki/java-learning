package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class MyAspect7 {
    @Before("execution(* com.lesama.service.impl.EmpServiceImpl.*(..))")
    public void before(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        log.info("目标对象：{}", target);
        String className = joinPoint.getTarget().getClass().getName();
        log.info("类名：{}", className);
        String methodName = joinPoint.getSignature().getName();
        log.info("方法名：{}", methodName);
        Object[] args = joinPoint.getArgs();
        log.info("参数：{}", Arrays.toString(args));
        log.info("MyAspect7 before");
    }
}
