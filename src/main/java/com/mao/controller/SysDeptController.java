package com.mao.controller;

import com.mao.common.JsonData;
import com.mao.dto.DeptLevelDto;
import com.mao.mapper.SysDeptMapper;
import com.mao.service.SysTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("sys/dept")
public class SysDeptController {
    @Autowired
    private SysTreeService sysTreeService;

    @RequestMapping("/dept.page")
    public ModelAndView page(){

        return new ModelAndView("dept");
    }

    @RequestMapping("/deptTree.json")
    @ResponseBody
    public JsonData deptTree(){
       List<DeptLevelDto> tree= sysTreeService.deptTree();
       return JsonData.success(tree);
    }
}
