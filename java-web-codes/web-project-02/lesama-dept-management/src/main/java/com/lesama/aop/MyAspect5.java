package com.lesama.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
@Component
@Slf4j
public class MyAspect5 {

    // 切入点表达式结构： 访问修饰符 返回值类型 包名.类名.方法名(参数列表)
    // 通配符： * 表示任意单个符号， .. 表示任意多个符号
    // 通常不建议省略包名.类名， 因为这样会扩大切入点匹配的范围， 导致性能较低
    @Before("execution(public * com.lesama.service.impl.EmpServiceImpl.getById(java.lang.Integer))")
//    @Before("execution(* com.lesama.service.impl.*.*(..))")
    public void before() {
        log.info("MyAspect5 before");
    }
}
