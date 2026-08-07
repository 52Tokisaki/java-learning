package com.lesama.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lesama.mapper.OperateLogMapper;
import com.lesama.pojo.OperateLog;
import com.lesama.pojo.PageResult;
import com.lesama.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private OperateLogMapper operateLogMapper;

    @Override
    public PageResult<OperateLog> page(Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OperateLog> list =  operateLogMapper.list();
        Page<OperateLog> p = (Page<OperateLog>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }
}
