package com.lesama;

import com.example.EnableHeaderConfig;
import com.example.HeaderConfig;
import com.example.MyImportSelector;
import com.example.TokenParser;
import com.lesama.utils.AliyunOSSOperator;
import com.lesama.utils.AliyunOSSProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


//@ComponentScan(basePackages = {"com.lesama", "com.example"})
@Import({TokenParser.class, HeaderConfig.class, MyImportSelector.class}) // 使用Import注解导入普通类实现自动装配
@EnableHeaderConfig // 使用EnableHeaderConfig注解导入配置类实现自动装配, 由第三方来决定哪些类实现自动装配
@SpringBootApplication
public class SpringbootWebConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfigApplication.class, args);
    }

}
