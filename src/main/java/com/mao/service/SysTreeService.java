package com.mao.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.mao.dto.DeptLevelDto;
import com.mao.mapper.SysDeptMapper;
import com.mao.model.SysDept;
import com.mao.utils.LevelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Description
 * @Author asus
 * @Date 2019/4/2321:16
 **/
@Service
public class SysTreeService {

    @Autowired
    public SysDeptMapper sysDeptMapper;

    /**
     * @Author mao
     * @Description //TODO 生成部门树
     * @Date 21:26 2019/4/23
     * @Param []
     * @return java.util.List<com.mao.dto.DeptLevelDto>
     **/
    public List<DeptLevelDto> deptTree(){
        //构建DeptTree
        //1根据mapper找到deptList
        //2将deptlist中sysDept 添加到DeptLevle的集合中
        //3返回 deptToListToTree 方法
        List<SysDept> sysDepts=sysDeptMapper.getAllDept();
        List<DeptLevelDto> deptLevelDtos= Lists.newArrayList();
        for (SysDept dept:sysDepts){
            DeptLevelDto dto=DeptLevelDto.adapt(dept);
            deptLevelDtos.add(dto);
        }
        return deptListToTree(deptLevelDtos);
    }

    /**
     * @Author mao
     * @Description //TODO 将集合生成树， 使用map存储 level 和deptLevleDto
     * @Date 21:33 2019/4/23
     * @Param [deptLevelDtos]
     * @return java.util.List<com.mao.dto.DeptLevelDto>
     **/
    private List<DeptLevelDto> deptListToTree(List<DeptLevelDto> deptLevelDtos) {
        //1检验集合是否为null
        //2使用map 存储 level 以及 List<DeptLevelDto>  --> Map<String,List<..>>
        //|
        // --2.1 level --> [dept1,dept2,dept4...]
        // |
        //  --2.2 使用Multimap 更加简洁
        //3 创建rootList
        //4 将deptLevelList集合存放到map中 ，其中rootlist 单独存放根 -->root=0
        //5 按照seq从小到大排序
        //6递归生成树

        if(CollectionUtils.isEmpty(deptLevelDtos)){
            return  Lists.newArrayList();
        }
        //学习使用此种方式
        Multimap<String,DeptLevelDto> multimap= ArrayListMultimap.create();
        List<DeptLevelDto> rootList=Lists.newArrayList();
        for (DeptLevelDto dto:deptLevelDtos){
            multimap.put(dto.getLevel(),dto);
            if(LevelUtils.ROOT.equals(dto.getLevel())){
                rootList.add(dto);
            }
        }

        Collections.sort(rootList, new Comparator<DeptLevelDto>() {
            @Override
            public int compare(DeptLevelDto o1, DeptLevelDto o2) {
                return o1.getSeq()-o2.getSeq();
            }
        });
        transformTree(rootList,LevelUtils.ROOT,multimap);
        return rootList;
    }

    /***
     * @Author mao
     * @Description //TODO 使用递归实现部门树
     * @Date 22:03 2019/4/23
     * @Param [rootList, root, multimap]
     * @return void
     **/
    private void transformTree(List<DeptLevelDto> rootList, String level, Multimap<String, DeptLevelDto> multimap) {
        //遍历该层每个元素
        //生成当前层level  如 0.1
        //处理下一层数据
        //判断下层集合是否为null
        // |___ 排序
        // |___ 递归继续进行下一层处理
        for (int i=0;i<rootList.size();i++){
            DeptLevelDto dto=rootList.get(i);
            String nextLevel=LevelUtils.calculateLevl(level,dto.getId());
            // CollectionList 可以强转为 List<DeptLevelDto>
            List<DeptLevelDto> deptLevelDtos=(List<DeptLevelDto>) multimap.get(nextLevel);

            if(CollectionUtils.isNotEmpty(deptLevelDtos)){
                    Collections.sort(deptLevelDtos,deptLevelDtoComparator);
                    //???
                    dto.setDeptList(deptLevelDtos);
                    transformTree(deptLevelDtos,nextLevel,multimap);
            }
        }
    }
    public Comparator<DeptLevelDto> deptLevelDtoComparator=new Comparator<DeptLevelDto>() {
        @Override
        public int compare(DeptLevelDto o1, DeptLevelDto o2) {
            return o1.getSeq()-o2.getSeq();
        }
    };
}
