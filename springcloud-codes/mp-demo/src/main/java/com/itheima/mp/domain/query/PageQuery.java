package com.itheima.mp.domain.query;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分页查询实体")
public class PageQuery {
    @ApiModelProperty("页码")
    private Long pageNo = 1L;
    @ApiModelProperty("分页大小")
    private Long pageSize = 2L;
    @ApiModelProperty("排序字段")
    private String sortBy;
    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;

    public <T> Page<T> toMpPage(OrderItem ... items) {
        Page<T> page = Page.of(pageNo, pageSize);
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        } else if (items != null) {
            page.addOrder(items);
        }
        return page;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, Boolean defaultIsAsc) {
        Page<T> page = Page.of(pageNo, pageSize);
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        } else {
            page.addOrder(new OrderItem(defaultSortBy, defaultIsAsc));
        }
        return page;
    }

    public <T> Page<T> toMpPageDefaultCreateTime() {
        Page<T> page = Page.of(pageNo, pageSize);
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        } else {
            page.addOrder(new OrderItem("create_time", false));
        }
        return page;
    }

    public <T> Page<T> toMpPageDefaultUpdateTime() {
        Page<T> page = Page.of(pageNo, pageSize);
        if (StrUtil.isNotBlank(sortBy)) {
            page.addOrder(new OrderItem(sortBy, isAsc));
        } else {
            page.addOrder(new OrderItem("update_time", false));
        }
        return page;
    }
}