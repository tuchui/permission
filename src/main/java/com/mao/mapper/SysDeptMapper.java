package com.mao.mapper;

import com.mao.model.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/***
 * @Author mao
 * @Description //TODO
 * @Date 22:54 2019/4/22
 **/
public interface SysDeptMapper {
    int cuntByNameAndParentId(@Param("id") Integer id, @Param("name") String name, @Param("parentId") Integer parentId);

    SysDept selectByPrimaryKey(Integer parentId);

    int insertSelective(SysDept dept);

    List<SysDept> getAllDept();
}
