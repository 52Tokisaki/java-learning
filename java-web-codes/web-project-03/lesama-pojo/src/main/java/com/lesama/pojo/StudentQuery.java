package com.lesama.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentQuery {
    private Integer page = 1;
    private Integer pageSize = 10;
    private String name;
    private Integer degree;
    private Integer clazzId;
}
