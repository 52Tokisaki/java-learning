package com.lesama;

import com.lesama.mapper.EmpMapper;
import com.lesama.pojo.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LesamaDeptManagementApplicationTests {
    @Autowired
    private EmpMapper empMapper;

    @Test
    void testEmpMapper() {
        List<Emp> list = empMapper.list();
        list.forEach(System.out::println);
    }

}
