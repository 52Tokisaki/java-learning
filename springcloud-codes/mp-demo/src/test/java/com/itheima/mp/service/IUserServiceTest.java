package com.itheima.mp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IUserServiceTest {
    @Autowired
    private IAddressService addressService;

    @Test
    void testDeleteAndQuery() {
        addressService.removeById(59L);
        System.out.println(addressService.getById(59L));
    }
}