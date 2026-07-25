package com.lesama.mapper;

import com.lesama.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpExprMapper {
    void insertBatch(List<EmpExpr> exprList);

    void delteByEmpIds(List<Integer> empIds);
}
