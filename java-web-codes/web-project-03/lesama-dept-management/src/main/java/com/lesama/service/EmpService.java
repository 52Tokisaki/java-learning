package com.lesama.service;

import com.lesama.pojo.Emp;
import com.lesama.pojo.EmpQuery;
import com.lesama.pojo.LoginInfo;
import com.lesama.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
/*    PageResult<Emp> page(Integer page, Integer pageSize,
                         String name, Integer gender,
                         LocalDate begin, LocalDate end);*/
    PageResult<Emp> page(EmpQuery empQuery);

    void save(Emp emp);

    void delete(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);

    List<Emp> list();

    LoginInfo login(Emp emp);
}
