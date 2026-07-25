package com.lesama.service;

import com.lesama.pojo.Clazz;
import com.lesama.pojo.ClazzQuery;
import com.lesama.pojo.PageResult;

import java.util.List;

public interface ClazzService {
    PageResult<Clazz> list(ClazzQuery clazzQuery);

    void save(Clazz clazz);

    void delete(Integer id);

    Clazz get(Integer id);

    void update(Clazz clazz);

    List<Clazz> queryAllClazz();
}
