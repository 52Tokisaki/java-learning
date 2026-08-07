package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
//@Aspect
@Component
public class RecordTimeAspect {
    @Around("execution(* com.lesama.service.impl.*.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        log.info("记录时间");
        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        log.info("{}方法耗时：{}ms", pjp.getSignature(), end - start);
        return result;
    }
}
