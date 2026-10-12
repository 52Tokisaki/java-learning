package com.itheima.mp.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户查询条件")
public class UserQuery extends PageQuery {

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户状态")
    private Integer status;

    @ApiModelProperty("账户最少余额")
    private Integer minBalance;

    @ApiModelProperty("账户最多余额")
    private Integer maxBalance;
}