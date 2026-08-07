package com.lesama.controller;

import com.lesama.pojo.OperateLog;
import com.lesama.pojo.PageResult;
import com.lesama.pojo.Result;
import com.lesama.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping("/log/page")
    public Result logPage(Integer page, Integer pageSize) {
        log.info("日志分页查询接口, {}, {}", page, pageSize);
        PageResult<OperateLog> result = logService.page(page, pageSize);
        return Result.success(result);
    }
}
