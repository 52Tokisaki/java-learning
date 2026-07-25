package com.lesama.service.impl;

import com.lesama.mapper.ReportMapper;
import com.lesama.pojo.JobOption;
import com.lesama.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;
    @Override
    public JobOption countJobOption() {
        List<Map<String, Object>> mapList = reportMapper.countJobOption();
        List<Object> jobList = mapList.stream().map(dataMap -> dataMap.get("pos")).toList();
        List<Object> dataList = mapList.stream().map(dataMap -> dataMap.get("num")).toList();
        return new JobOption(jobList, dataList);
    }

    @Override
    public List<Map<String, Object>> countEmpGenderData() {
        return reportMapper.countEmpGenderData();
    }
}
