package com.lesama.service;

import com.lesama.pojo.OperateLog;
import com.lesama.pojo.PageResult;

public interface LogService {
    PageResult<OperateLog> page(Integer page, Integer pageSize);
}
