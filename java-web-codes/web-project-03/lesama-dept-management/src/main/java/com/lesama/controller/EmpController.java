package com.lesama.controller;

import com.lesama.annotation.LogOperation;
import com.lesama.pojo.Emp;
import com.lesama.pojo.EmpQuery;
import com.lesama.pojo.PageResult;
import com.lesama.pojo.Result;
import com.lesama.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/emps")
@Slf4j
@RestController
public class EmpController {
    @Autowired
    private EmpService empService;

    /*@GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Integer gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        log.info("分页查询，参数：page={}, pageSize={}, name={}, gender={}, begin={}, end={}", page, pageSize, name, gender, begin, end);
        return Result.success(empService.page(page, pageSize, name, gender, begin, end));
    }*/

    @GetMapping
    public Result page(EmpQuery empQuery) {
        log.info("分页查询，参数：{}", empQuery);
        return Result.success(empService.page(empQuery));
    }

    @PostMapping
    @LogOperation
    public Result save(@RequestBody Emp emp) {
        log.info("新增员工，员工数据：{}", emp);
        empService.save(emp);
        return Result.success();
    }

    @DeleteMapping
    @LogOperation
    public Result delete(@RequestParam List<Integer> ids) {
        log.info("删除员工，员工id：{}", ids);
        empService.delete(ids);
        return Result.success();
    }


    /**
     * 根据id查询员工
     * */
    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询员工，员工id：{}", id);
        return Result.success(empService.getById(id));
    }

    /**
     * 根据id修改员工
     * */
    @PutMapping
    @LogOperation
    public Result update(@RequestBody Emp emp) {
        log.info("修改员工，员工数据：{}", emp);
        empService.updateById(emp);
        return Result.success();
    }

    @GetMapping("/list")
    public Result list() {
        return Result.success(empService.list());
    }
}
