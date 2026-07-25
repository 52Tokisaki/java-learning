package com.lesama.service;

import com.lesama.pojo.PageResult;
import com.lesama.pojo.Student;
import com.lesama.pojo.StudentQuery;

public interface StudentService {
    PageResult<Student> page(StudentQuery studentQuery);

    void save(Student student);

    void delete(Integer id);

    Student getById(Integer id);

    void update(Student student);
}
