package com.lesama.mapper;

import com.lesama.pojo.Student;
import com.lesama.pojo.StudentQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {
    List<Student> list(StudentQuery studentQuery);

    void save(Student student);

    @Delete("delete from student where id = #{id}")
    void delete(Integer id);

    @Select("select * from student where id = #{id}")
    Student getById(Integer id);

    void updateById(Student student);
}
