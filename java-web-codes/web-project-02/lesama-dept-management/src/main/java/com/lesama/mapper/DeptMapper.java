package com.lesama.mapper;

import com.lesama.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper

public interface DeptMapper {

//    1. 使用注解方式映射
//    @Results({
//            @Result(column = "create_time", property = "createTime"),
//            @Result(column = "update_time", property = "updateTime")
//    })

//    2. 使用起别名方式映射
//    @Select("select id, name, create_time createTime, update_time updateTime from dept order by update_time")
    @Select("select id, name, create_time, update_time from dept order by update_time")
    List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime})")
    void insert(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept findById(Integer id);

    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void updateById(Dept dept);
}
