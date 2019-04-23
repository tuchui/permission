package com.mao.service;

import com.mao.exception.ParamsException;
import com.mao.mapper.SysDeptMapper;
import com.mao.model.SysDept;
import com.mao.params.DeptParam;
import com.mao.utils.BeanValidator;
import com.mao.utils.LevelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDeptService {
    @Autowired
    private SysDeptMapper sysDeptMapper;

   /**
    * @Author mao
    * @Description //TODO
    *           //检验deptParm是否合法
    *         //检查同一层级下是否存在 相同名称的部门
    *         //使用Builder 构造deptParam 对象
    *         // 构造dept 层级
    *            保存到数据库中
    * @Date 22:25 2019/4/22
    * @Param [deptParam]
    * @return void
    **/
    public void save(DeptParam deptParam){
        //检验deptParm是否合法
        //检查同一层级下是否存在 相同名称的部门
        //使用Builder 构造deptParam 对象
        // 构造dept 层级
        //保存到数据库中
        BeanValidator.check(deptParam);
        if(checkExist(deptParam.getId(),deptParam.getName(),deptParam.getParentId())){
            throw new ParamsException("同一层级下是存在相同名称的部门");
        }
        SysDept dept=SysDept.builder()
                            .name(deptParam.getName())
                            .parentId(deptParam.getParentId())
                            .seq(deptParam.getSeq())
                            .remark(deptParam.getRemark())
                            .build();
        String parnetLevel=getLevel(deptParam.getParentId());
        dept.setLevel(LevelUtils.calculateLevl(parnetLevel,deptParam.getParentId()));
        sysDeptMapper.insertSelective(dept);

    }

    private String getLevel(Integer parentId) {
        SysDept dept=sysDeptMapper.selectByPrimaryKey(parentId);
        if(dept==null){
            return null;
        }
        return dept.getLevel();
    }

    /**
     * @Author mao
     * @Description 检查部门是否合法 //TODO
     * @Date 22:51 2019/4/22
     * @Param [id, name, parentId]
     * @return boolean
     **/
    private boolean checkExist(Integer id, String name, Integer parentId) {
        int count=sysDeptMapper.cuntByNameAndParentId(id,name,parentId);
        if(count>0){
            return true;
        }
        return  false;
    }
}
