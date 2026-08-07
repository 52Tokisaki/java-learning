package com.lesama.service;

import com.lesama.pojo.Dept;

import java.util.List;

public interface DeptService {
    List<Dept> findAll();

    void deleteById(Integer id);

    void save(Dept dept);

    Object findById(Integer id);

    void updateById(Dept dept);
}
