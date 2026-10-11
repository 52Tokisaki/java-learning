package com.itheima.mp.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(1, "正常"),
    FROZEN(2, "冻结")
    ;

    @EnumValue // MybatisPlus中处理枚举与数据库的映射
    @JsonValue // Jackson中处理枚举与返回JSON的映射
    private final int value;
    private final String desc;
    UserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
