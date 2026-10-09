package com.itheima.mp.service;

import com.itheima.mp.domain.po.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class UserServiceTest { 
    @Autowired
    private IUserService userService;
    
    @Test
    void testSaveUser() {
        User user = new User();
        // user.setId(5L);
        user.setUsername("Alpha");
        user.setPassword("123");
        user.setPhone("18688990321");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"金发精灵\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userService.save(user);
    }
}