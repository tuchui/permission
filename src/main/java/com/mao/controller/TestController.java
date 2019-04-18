package com.mao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String test(@PathVariable("hello") String path){
        return path;
    }
}
