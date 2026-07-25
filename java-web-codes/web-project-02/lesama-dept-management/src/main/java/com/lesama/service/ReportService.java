package com.lesama.service;

import com.lesama.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JobOption countJobOption();

    List<Map<String, Object>> countEmpGenderData();
}
