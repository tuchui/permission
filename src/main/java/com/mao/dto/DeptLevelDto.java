package com.mao.dto;

import com.google.common.collect.Lists;
import com.mao.model.SysDept;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Description
 * @Author asus
 * @Date 2019/4/2321:12
 **/
@Getter
@Setter
@ToString
public class DeptLevelDto extends SysDept {
    List<DeptLevelDto> deptList= Lists.newArrayList();

    public static DeptLevelDto adapt(SysDept sysDept){
        DeptLevelDto deptLevelDto=new DeptLevelDto();
        BeanUtils.copyProperties(sysDept,deptLevelDto);
        return deptLevelDto;
    }
}
