package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {
    @Autowired
    private IAddressService addressService;

    @Autowired
    private IUserService userService;

    @Test
    void testDeleteAndQuery() {
        addressService.removeById(59L);
        System.out.println(addressService.getById(59L));
    }

    @Test
    void testPage() {
        int pageNo = 1, pageSize = 3;
        // 1. 设置分页参数和排序参数
        Page<User> page = new Page<>(pageNo, pageSize);
        page.addOrder(new OrderItem("balance", true));
        page.addOrder(new OrderItem("id", true));
        // 2. 执行分页查询
        Page<User> p = userService.page(page);
        System.out.println("total" + p.getTotal());
        System.out.println("pages" + p.getPages());
        List<User> users = p.getRecords();
        users.forEach(System.out::println);
    }
}