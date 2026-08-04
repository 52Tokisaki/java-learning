package com.lesama;

import com.lesama.controller.DeptController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringbootWebTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testSingleTon() {
        for (int i = 0; i < 1000; i++) {
            // 默认情况下 SpringBoot会创建单例对象， 并且是在程序运行时创建，
            // 使用Lazy注解后，SpringBoot在启动时并不会创建对象，而是第一次使用时创建（懒汉模式）
            DeptController deptController = applicationContext.getBean(DeptController.class);
            System.out.println(deptController);
        }
    }
}
