package com.lesama.controller;

import com.lesama.pojo.Result;
import com.lesama.pojo.Student;
import com.lesama.pojo.StudentQuery;
import com.lesama.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public Result page(StudentQuery studentQuery) {
        log.info("分页查询，参数：{}", studentQuery);
        return Result.success(studentService.page(studentQuery));
    }

    @PostMapping
    public Result add(@RequestBody Student student) {
        log.info("新增学生：{}", student);
        studentService.save(student);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("删除学生：{}", id);
        studentService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result get(@PathVariable Integer id) {
        log.info("查询学生，学生id：{}", id);
        return Result.success(studentService.getById(id));
    }

    @PutMapping
    public Result update(@RequestBody Student student) {
        log.info("更新学生：{}", student);
        studentService.update(student);
        return Result.success();
    }

    @PutMapping("/violation/{id}/{violationScore}")
    public Result addViolationScore(@PathVariable Integer id, @PathVariable Short violationScore) {
        log.info("增加学生违纪分数，学生id：{}，违纪分数：{}", id, violationScore);
        Student student = studentService.getById(id);
        student.setViolationScore((short)(student.getViolationScore() + violationScore));
        student.setViolationCount((short)(student.getViolationCount() + 1));
        studentService.update(student);
        return Result.success();
    }
}
