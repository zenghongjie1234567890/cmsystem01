package com.zhj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class copy {
    private Integer teamId;

    private String teamName;

    private String teamWorks;
    private String teamTeaid;

    private String teamNums;

    private Integer teamGrade;

    private String teamPrice;

}
