package com.mao.controller;

import com.mao.common.JsonData;
import com.mao.exception.ParamsException;
import com.mao.exception.PermissionException;
import com.mao.params.TestVo;
import com.mao.utils.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author:mao
 * @date:2019/4/18
 * @description:
 **/
@Controller
@Slf4j
public class TestController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        throw new PermissionException(" hello permission exception");

    }

    @RequestMapping("/{hello}")
    @ResponseBody
    public Student test(@PathVariable("hello") String path){
        Student s=new Student();
        s.setAge(22);
        s.setName("jack");
        return s;
    }
    @RequestMapping("/test")
    @ResponseBody
    public JsonData testBeanValidate(TestVo testVo){

        try{
            Map<String,String> res=BeanValidator.validateObject(testVo);
            if(res!=null && res.entrySet().size()>0){
                for(Map.Entry<String,String>entry: res.entrySet() ){
                    log.info("{}-->{}",entry.getKey(),entry.getValue());
                }
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return JsonData.success("test validate");
    }

    @RequestMapping("/test2.json")
    @ResponseBody
    public JsonData test2(TestVo testVo)throws ParamsException {
        BeanValidator.check(testVo);
        return JsonData.success("test2");
    }
    @RequestMapping("/studeng")
    public String student(Model model){
       model.addAttribute("studeng","test");
       return "jsonView";
    }
}
class Student{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
