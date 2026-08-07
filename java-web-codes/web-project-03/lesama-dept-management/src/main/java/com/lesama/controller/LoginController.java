package com.lesama.controller;

import com.lesama.pojo.Emp;
import com.lesama.pojo.LoginInfo;
import com.lesama.pojo.Result;
import com.lesama.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private EmpService empService;

    @PostMapping
    public Result login(@RequestBody Emp emp) {
        log.info("员工登录，员工数据：{}", emp);
        LoginInfo loginInfo = empService.login(emp);
        if (loginInfo == null) {
            return Result.error("用户名或密码错误");
        }
        return Result.success(loginInfo);
    }
}
