package com.lesama.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lesama.mapper.EmpExprMapper;
import com.lesama.mapper.EmpMapper;
import com.lesama.pojo.*;
import com.lesama.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Autowired
    private EmpLogServiceImpl empLogService;

/*    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        Long total = empMapper.count();
        List<Emp> rows = empMapper.listByPage((page - 1) * pageSize, pageSize);
        return new PageResult<>(total, rows);
    }*/

    // 使用PageHelper进行分页查询
/*    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize,
                                String name, Integer gender,
                                LocalDate begin, LocalDate end) {
        PageHelper.startPage(page, pageSize);
        List<Emp> rows = empMapper.listByPage((page - 1) * pageSize, pageSize, name, gender, begin, end);
        Page<Emp> p = (Page<Emp>) rows;
        return new PageResult<>(p.getTotal(), p.getResult());
    }*/

    @Override
    public PageResult<Emp> page(EmpQuery empQuery) {
        PageHelper.startPage(empQuery.getPage(), empQuery.getPageSize());
        List<Emp> rows = empMapper.listByPage(empQuery);
        Page<Emp> p = (Page<Emp>) rows;
        return new PageResult<>(p.getTotal(), p.getResult());
    }

    @Transactional(rollbackFor = Exception.class) // 添加事务, 确保除运行时异常以外的异常也能回滚
    @Override
    public void save(Emp emp) {
        try {
            emp.setCreateTime(LocalDateTime.now());
            emp.setUpdateTime(LocalDateTime.now());
            empMapper.insert(emp);
            List<EmpExpr> exprList = emp.getExprList();
            if (exprList != null && exprList.size() > 0) {
                exprList.forEach(expr -> expr.setEmpId(emp.getId()));
                empExprMapper.insertBatch(exprList);
            }
        } finally {
            EmpLog empLog = new EmpLog(null, LocalDateTime.now(), "保存员工信息" + emp);
            empLogService.insertLog(empLog);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> ids) {
        empExprMapper.delteByEmpIds(ids);
        empExprMapper.delteByEmpIds(ids);
    }

    @Override
    public Emp getById(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateById(Emp emp) {
        // 1. 修改员工基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        // 2. 先删除员工的工作经历信息
        empExprMapper.delteByEmpIds(Arrays.asList(emp.getId()));

        // 3. 再保存员工新的工作经历信息
        List<EmpExpr> exprList = emp.getExprList();
        if (exprList != null && exprList.size() > 0) {
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public List<Emp> list() {
        return empMapper.queryAll();
    }

}
