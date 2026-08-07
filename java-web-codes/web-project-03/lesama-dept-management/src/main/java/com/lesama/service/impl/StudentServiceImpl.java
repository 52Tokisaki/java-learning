package com.lesama.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lesama.mapper.StudentMapper;
import com.lesama.pojo.PageResult;
import com.lesama.pojo.Student;
import com.lesama.pojo.StudentQuery;
import com.lesama.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public PageResult<Student> page(StudentQuery studentQuery) {
        PageHelper.startPage(studentQuery.getPage(), studentQuery.getPageSize());
        List<Student> list = studentMapper.list(studentQuery);
        Page<Student> p = (Page<Student>) list;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Override
    public void save(Student student) {
        student.setCreateTime(LocalDateTime.now());
        student.setUpdateTime(LocalDateTime.now());
        student.setViolationCount((short) 0);
        student.setViolationScore((short) 0);
        studentMapper.save(student);
    }

    @Override
    public void delete(Integer id) {
        studentMapper.delete(id);
    }

    @Override
    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }

    @Override
    public void update(Student student) {
        student.setUpdateTime(LocalDateTime.now());
        studentMapper.updateById(student);
    }
}
