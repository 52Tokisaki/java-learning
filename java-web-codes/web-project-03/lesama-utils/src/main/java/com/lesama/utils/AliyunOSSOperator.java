package com.lesama.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.lesama.pojo.AliyunOssProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AliyunOSSOperator {
/*    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    String region = "cn-beijing";
    String bucketName = "lesama-java-web";*/

    //方式一: 通过@Value注解一个属性一个属性的注入
/*    @Value("${aliyun.oss.endpoint}")
    String endpoint;
    @Value("${aliyun.oss.region}")
    String region;
    @Value("${aliyun.oss.bucketName}")
    String bucketName;*/

    @Autowired
    private AliyunOssProperties aliyunOssProperties;
    public String upload(byte[] content, String originalFileName) throws Exception {
        String endpoint = aliyunOssProperties.getEndpoint();
        String region = aliyunOssProperties.getRegion();
        String bucketName = aliyunOssProperties.getBucketName();
        // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();

        // 填写Object完整路径，例如202406/1.png。Object完整路径中不能包含Bucket名称。
        //获取当前系统日期的字符串,格式为 yyyy/MM
        String dir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String newFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf("."));
        String objectName = dir + "/" + newFileName;

        // 创建OSSClient实例。
        ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
        clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
        OSS ossClient = OSSClientBuilder.create()
                .endpoint(endpoint)
                .credentialsProvider(credentialsProvider)
                .clientConfiguration(clientBuilderConfiguration)
                .region(region)
                .build();
        try {
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content));
        } finally {
            ossClient.shutdown();
        }

        return endpoint.split("//")[0] + "//" + bucketName + "." + endpoint.split("//")[1] + "/" + objectName;
    }
}
