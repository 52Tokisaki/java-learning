package com.lesama.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lesama.mapper.ClazzMapper;
import com.lesama.pojo.Clazz;
import com.lesama.pojo.ClazzQuery;
import com.lesama.pojo.PageResult;
import com.lesama.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzMapper clazzMapper;
    @Override
    public PageResult<Clazz> list(ClazzQuery clazzQuery) {
        PageHelper.startPage(clazzQuery.getPage(), clazzQuery.getPageSize());
        List<Clazz> list = clazzMapper.list(clazzQuery);
        Page<Clazz> p = (Page<Clazz>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void save(Clazz clazz) {
        clazz.setCreateTime(LocalDateTime.now());
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.save(clazz);
    }

    @Override
    public void delete(Integer id) {
        clazzMapper.delete(id);
    }

    @Override
    public Clazz get(Integer id) {
        return clazzMapper.getById(id);
    }

    @Override
    public void update(Clazz clazz) {
        clazz.setUpdateTime(LocalDateTime.now());
        clazzMapper.updateById(clazz);
    }

    @Override
    public List<Clazz> queryAllClazz() {
        return clazzMapper.queryAllClazz();
    }
}
