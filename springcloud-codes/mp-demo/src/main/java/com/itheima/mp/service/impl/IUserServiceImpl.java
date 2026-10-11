package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

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

        // userMapper.deductMoneyById(id, money);
        int remainBalance = user.getBalance() - money;
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 2) // 若扣减后余额为0，则用户状态冻结
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) // 乐观锁
                .update();
    }

    @Override
    public List<User> queryUsers(String username, Integer status, Integer minBalance, Integer maxBalance) {
        return lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .list();
    }

    @Override
    public UserVO queryUserAndAddressById(Long id) {
        User user = getById(id);
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();
        userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        return userVO;
    }
}
