package com.mao.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class DeptParam {

    private Integer id;
    @NotBlank(message = "部门不能为null")
    @Length(min=2,max =15,message = "部门名称为2-15")
    private String name;

    @NotNull(message = "展示顺序不能为空")
    private Integer seq;
    private Integer parentId=0;
    @Length(max=150,message = "备注长度在150之内")
    private String remark;

}
