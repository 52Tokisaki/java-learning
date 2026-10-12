package com.itheima.mp.domain.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页结果")
public class PageDTO<T> {
    private Long total;
    private Long pages;
    private List<T> list;
}