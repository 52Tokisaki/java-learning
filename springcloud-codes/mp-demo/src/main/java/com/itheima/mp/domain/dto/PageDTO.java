package com.itheima.mp.domain.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("分页结果")
public class PageDTO<T> {
    private Long total;
    private Long pages;
    private List<T> list;

    public static <PO, VO> PageDTO<VO> of(Page<PO> page, Class<VO> clazz) {
        PageDTO<VO> dto = new PageDTO<>();
        dto.setTotal(page.getTotal());
        dto.setPages(page.getPages());
        List<PO> records = page.getRecords();
        dto.setList(BeanUtil.copyToList(records, clazz));
        return dto;
    }

    public static <PO, VO> PageDTO<VO> of(Page<PO> page, Function<PO, VO> convertor) {
        PageDTO<VO> dto = new PageDTO<>();
        dto.setTotal(page.getTotal());
        dto.setPages(page.getPages());
        List<PO> records = page.getRecords();
        dto.setList(records.stream().map(convertor).collect(Collectors.toList()));
        return dto;
    }
}