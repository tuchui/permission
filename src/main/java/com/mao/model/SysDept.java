package com.mao.model;

import lombok.*;

import java.util.Date;

/***
 * @Author mao
 * @Description // 学习使用builder 构造器
 * @Date 23:01 2019/4/22
 **/
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SysDept {
    private Integer id;

    private String name;

    private Integer parentId;

    private String level;

    private Integer seq;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
}
