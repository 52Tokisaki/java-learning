package com.lesama.controller;

import com.lesama.pojo.Result;
import com.lesama.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
/*    @PostMapping
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("文件上传，参数：name={}, age={}, file={}", name, age, file);
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        UUID uuid = UUID.randomUUID();
        file.transferTo(new File( "F:/images/" + uuid + extension));
        return Result.success();
    }*/

    @PostMapping
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传，参数：file={}",  file);
        if (!file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            UUID uuid = UUID.randomUUID();
            String url = aliyunOSSOperator.upload(file.getBytes(), uuid + extension);
            return Result.success(url);
        }
        return Result.error("上传失败");
    }
}
