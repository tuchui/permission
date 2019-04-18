package com.mao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author:mao
 * @date:2019/4/18
 * @description:
 **/
@Controller
public class TestController {

    @RequestMapping("/hello")
    public String hello(){

        return "test";
    }

    @RequestMapping("/{hello}")
    @ResponseBody
    public Student test(@PathVariable("hello") String path){
        Student s=new Student();
        s.setAge(22);
        s.setName("jack");
        return s;
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
