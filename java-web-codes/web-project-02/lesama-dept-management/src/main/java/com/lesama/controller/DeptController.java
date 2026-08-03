package com.lesama.controller;

import com.lesama.annotation.LogOperation;
import com.lesama.pojo.Dept;
import com.lesama.pojo.Result;
import com.lesama.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

//    @RequestMapping(value = "/depts", method = RequestMethod.GET)
    @GetMapping
    public Result findAll() {
        List<Dept> depts = deptService.findAll();
        return Result.success(depts);
    }

    /**
     *  接收请求参数的方式一：通过HttpServletRequest对象中的getParameter()方法获取
     *  该方法的返回值为String类型，需要转换为对应的数据类型
     * */
/*
    @DeleteMapping("/depts")
    public Result delete(HttpServletRequest request) {
        Integer id = Integer.parseInt(request.getParameter("id"));

        System.out.println("删除的部门Id为" + id);
        return Result.success();
    }
*/

    /**
     * 接收请求参数的方式二：使用@RequestParam注解
     * 该注解默认required属性为true，表示该参数必须传递
     * */
/*    @DeleteMapping("/depts")
    public Result delete(@RequestParam(value = "id", required = false) Integer id) {

        System.out.println("删除的部门Id为" + id);
        return Result.success();
    }*/


    /**
     * 接收请求参数的方式三：使用形参名称（只有当形参名称与请求参数名称一致时）
     * */
    @LogOperation
    @DeleteMapping
    public Result delete(Integer id) {
        deptService.deleteById(id);

        log.info("删除的部门Id为：{}", id);
        return Result.success();
    }


    /**
     * 新增部门
     * */
    @LogOperation
    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增的部门为：{}", dept);
        deptService.save(dept);
        return Result.success();
    }
    
    /**
     * 根据id查询部门
     * */
    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return Result.success(deptService.findById(id));
    }

    /**
     * 修改部门
     * */
    @LogOperation
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改的部门为：{}", dept);
        deptService.updateById(dept);
        return Result.success();
    }
}
