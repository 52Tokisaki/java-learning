package com.lesama.controller;

import com.lesama.pojo.Result;
import com.lesama.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/report")
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/empJobData")
    public Result countJobOption() {
        return Result.success(reportService.countJobOption());
    }

    @GetMapping("/empGenderData")
    public Result countEmpGenderData() {
        return Result.success(reportService.countEmpGenderData());
    }

    @GetMapping("/studentDegreeData")
    public Result countStudentDegreeData() {
        return Result.success(reportService.countStudentDegreeData());
    }

    @GetMapping("/studentCountData")
    public Result countStudentCountData() {
        return Result.success(reportService.countStudentCountData());
    }
}
