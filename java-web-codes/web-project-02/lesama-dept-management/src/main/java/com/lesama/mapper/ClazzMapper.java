package com.lesama.mapper;

import com.lesama.pojo.Clazz;
import com.lesama.pojo.ClazzQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ClazzMapper {
    List<Clazz> list(ClazzQuery clazzQuery);

    @Insert("insert into clazz (name, room, begin_date, end_date, master_id, subject, create_time, update_time) values" +
            "(#{name}, #{room}, #{beginDate}, #{endDate}, #{masterId}, #{subject}, #{createTime}, #{updateTime})")
    void save(Clazz clazz);

    @Delete("delete from clazz where id = #{id}")
    void delete(Integer id);

    @Select("select * from clazz where id = #{id}")
    Clazz getById(Integer id);

    void updateById(Clazz clazz);

    List<Clazz> queryAllClazz();
}
