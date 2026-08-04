package com.lesama;

import cn.hutool.core.io.FileUtil;
import com.lesama.controller.DeptController;
import com.lesama.utils.AliyunOSSOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.File;
import java.util.logging.Filter;

@SpringBootTest
class SpringbootWebTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @Test
    void testSingleTon() {
        for (int i = 0; i < 1000; i++) {
            // 默认情况下 SpringBoot会创建单例对象， 并且是在程序运行时创建，
            // 使用Lazy注解后，SpringBoot在启动时并不会创建对象，而是第一次使用时创建（懒汉模式）
            DeptController deptController = applicationContext.getBean(DeptController.class);
            System.out.println(deptController);
        }
    }

    @Test
    void testUpload() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Pictures\\2\\daniya.png");
        byte[] bytes = FileUtil.readBytes(file);
        aliyunOSSOperator.upload(bytes, "daniya.png");
    }
}
