package com.itheima.mp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public void deductMoneyById(Long id, Integer money) {
        User user = this.getById(id);

        if (user == null || user.getStatus() == 2) {
            throw new RuntimeException("用户状态非法");
        }

        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }

        userMapper.deductMoneyById(id, money);
    }
}
