package com.lesama.controller;

import com.lesama.annotation.LogOperation;
import com.lesama.pojo.Clazz;
import com.lesama.pojo.ClazzQuery;
import com.lesama.pojo.Result;
import com.lesama.service.ClazzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/clazzs")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping
    public Result list(ClazzQuery clazzQuery) {
        return Result.success(clazzService.list(clazzQuery));
    }

    @PostMapping
    @LogOperation
    public Result add(@RequestBody Clazz clazz) {
        log.info("新增班级：{}", clazz);
        clazzService.save(clazz);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    @LogOperation
    public Result delete(@PathVariable Integer id) {
        log.info("删除的班级Id为：{}", id);
        clazzService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询班级，班级id：{}", id);
        return Result.success(clazzService.get(id));
    }

    @PutMapping
    @LogOperation
    public Result update(@RequestBody Clazz clazz) {
        log.info("更新班级，班级信息：{}", clazz);
        clazzService.update(clazz);
        return Result.success();
    }

    /**
     * 查询所有班级接口
     * */
    @GetMapping("/list")
    public Result list() {
        return Result.success(clazzService.queryAllClazz());
    }
}
