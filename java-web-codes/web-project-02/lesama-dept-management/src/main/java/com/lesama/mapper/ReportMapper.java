package com.lesama.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {

    public List<Map<String, Object>> countJobOption();

    List<Map<String, Object>> countEmpGenderData();
}
