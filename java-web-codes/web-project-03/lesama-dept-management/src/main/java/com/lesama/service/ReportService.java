package com.lesama.service;

import com.lesama.pojo.JobOption;
import com.lesama.pojo.StudentOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption countJobOption();

    List<Map<String, Object>> countEmpGenderData();

    List<Map<String, Object>> countStudentDegreeData();

    StudentOption countStudentCountData();
}
