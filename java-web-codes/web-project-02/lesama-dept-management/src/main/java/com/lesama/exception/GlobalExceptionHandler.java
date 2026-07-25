package com.lesama.exception;

import com.lesama.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public Result handle(Exception e) {
        log.error("执行出错：{}", e);
        return Result.error("程序异常，请联系管理员处理");
    }

    /**
     * 处理 DuplicateKeyException: Duplicate entry 'bbb' for key 'emp.username'
     * */
    @ExceptionHandler
    public Result handle(DuplicateKeyException e) {
        log.error("执行出错：{}", e);
        String errMsg = e.getMessage();
        int i = errMsg.indexOf("Duplicate entry");
        String[] parts = errMsg.substring(i).split(" ");
        return Result.error(parts[2] + "已存在");
    }
}
