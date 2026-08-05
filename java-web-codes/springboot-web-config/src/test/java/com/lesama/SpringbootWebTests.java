package com.lesama;

import cn.hutool.core.io.FileUtil;
import com.example.HeaderConfig;
import com.example.HeaderGenerator;
import com.example.HeaderParser;
import com.example.TokenParser;
import com.google.gson.Gson;
import com.lesama.controller.DeptController;
import com.lesama.pojo.Result;
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

    @Autowired
    private Gson gson;
    // 测试自动配置
    @Test
    void testGson() {
        System.out.println(gson.toJson(Result.success("hello gson")));
    }

    @Autowired
    private TokenParser tokenParser;
    @Test
    void testTokenParser() {
        System.out.println(applicationContext.getBean(TokenParser.class));
    }

    @Autowired
    private HeaderParser headerParser;

    @Autowired
    private HeaderGenerator headerGenerator;
    @Test
    void testHeader() {
        System.out.println(applicationContext.getBean(HeaderParser.class));
        System.out.println(applicationContext.getBean(HeaderGenerator.class));
    }

    @Autowired
    private HeaderConfig headerConfig;
    @Test
    void testHeaderConfig() {
        System.out.println(applicationContext.getBean(HeaderConfig.class));
    }
}
