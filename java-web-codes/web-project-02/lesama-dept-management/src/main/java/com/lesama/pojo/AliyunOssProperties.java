package com.lesama.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss") // 实体类上添加@ConfigurationProperties注解,通过prefix添加前缀
public class AliyunOssProperties {
    private String endpoint;
    private String region;
    private String bucketName;
}
