package com.lesama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 添加此注解后，会自动扫描Servlet、Filter、Listener相关组件
@SpringBootApplication
public class LesamaDeptManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(LesamaDeptManagementApplication.class, args);
    }

}
