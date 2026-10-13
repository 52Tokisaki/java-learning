package com.itheima.mp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.mp.domain.dto.PageDTO;
import com.itheima.mp.domain.po.Address;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.query.UserQuery;
import com.itheima.mp.domain.vo.AddressVO;
import com.itheima.mp.domain.vo.UserVO;
import com.itheima.mp.enums.UserStatus;
import com.itheima.mp.mapper.UserMapper;
import com.itheima.mp.service.IUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    @Override
    public void deductMoneyById(Long id, Integer money) {
        User user = this.getById(id);

        if (user == null || user.getStatus() == UserStatus.FROZEN) {
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
        if (user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常");
        }
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();
        userVO.setAddresses(BeanUtil.copyToList(addresses, AddressVO.class));
        return userVO;
    }

    @Override
    public List<UserVO> queryUsersByIds(List<Long> ids) {
        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)) {
            return List.of();
        }

        List<Address> addresses = Db.lambdaQuery(Address.class).in(Address::getUserId, ids).list();
        Map<Long, List<Address>> addressMap = new HashMap<>();
        if (CollUtil.isNotEmpty(addresses)) {
            addressMap = addresses.stream().collect(Collectors.groupingBy(Address::getUserId)); // 根据用户id分组
        }

        List<UserVO> userVOS = new ArrayList<>(users.size());
        for (User user : users) {
            UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
            userVO.setAddresses(BeanUtil.copyToList(addressMap.get(user.getId()), AddressVO.class));
            userVOS.add(userVO);
        }
        return userVOS;
    }

    @Override
    public List<AddressVO> queryUserAddressById(Long id) {
        User user = getById(id);
        if (user == null || user.getStatus() == UserStatus.FROZEN) {
            throw new RuntimeException("用户状态异常");
        }
        List<Address> addresses = Db.lambdaQuery(Address.class).eq(Address::getUserId, id).list();
        return BeanUtil.copyToList(addresses, AddressVO.class);
    }

    @Override
    public PageDTO<UserVO> queryUsersPage(UserQuery userQuery) {
        String username = userQuery.getUsername();
        Integer status = userQuery.getStatus();
        Integer minBalance = userQuery.getMinBalance();
        Integer maxBalance = userQuery.getMaxBalance();
        String sortBy = userQuery.getSortBy();
        Boolean isAsc = userQuery.getIsAsc();
        Page<User> page = userQuery.toMpPageDefaultUpdateTime();
        Page<User> p = lambdaQuery().like(username != null, User::getUsername, username)
                .eq(status != null, User::getStatus, status)
                .ge(minBalance != null, User::getBalance, minBalance)
                .le(maxBalance != null, User::getBalance, maxBalance)
                .page(page);
        // return PageDTO.of(p, UserVO.class);
        return PageDTO.of(p, user -> {
            // 处理基本属性
            UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
            // 处理特殊逻辑
            userVO.setUsername(userVO.getUsername().substring(0, userVO.getUsername().length() - 2) + "**");
            // 将处理结果返回
            return userVO;
        });
    }
}
