package com.lesama.mapper;

import com.lesama.pojo.Emp;
import com.lesama.pojo.EmpQuery;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EmpMapper {

    @Select("select e.id, e.username, e.password, e.name, e.gender, e.phone, e.job, e.salary, e.image, e.entry_date, e.dept_id, e.create_time, e.update_time, d.name" +
            " from emp e left join dept d on e.dept_id = d.id order by e.update_time desc ")
    List<Emp> list();

/*    @Select("select count(*) from emp")
    Long count();

    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id" +
            " order by e.update_time desc limit #{index}, #{pageSize}")
    List<Emp> listByPage(Integer index, Integer pageSize);*/

    // 使用pageHelper
//    @Select("select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id")
/*    List<Emp> listByPage(Integer index, Integer pageSize, String name, Integer gender,
                         LocalDate begin, LocalDate end);*/

    List<Emp> listByPage(EmpQuery empQuery);

    @Options(useGeneratedKeys = true, keyProperty = "id") // 插入成功后，将主键值回填到emp对象中
    @Insert("insert into emp(username, password, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
             "values(#{username}, #{password}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    void deleteByIds(List<Integer> ids);

    Emp getById(Integer id);

    void updateById(Emp emp);

    @Select("select * from emp")
    List<Emp> queryAll();

    @Select("select id, username, name from emp where username = #{username} and password = #{password}")
    Emp getByUsernameAndPassword(String username, String password);
}
