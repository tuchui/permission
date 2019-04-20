package com.mao.common;

import com.mao.exception.ParamsException;
import com.mao.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class SpringExceptionReslover implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e) {
            //1获取url
            //2定义视图
            // 3定义 默认异常信息
            //4项目中所有请求json数据，都使用.json结尾的异常处理
            // 4.1 判断是否 属于自定义异常 使用jsonview 和 toMap
            //5所有请求page页面，都使用.page结尾的异常 处理
            //6未知异常处理
        String url=req.getRequestURI();
        ModelAndView mv=null;
        String defaultMsg="sys error";
        String json=".json";
        String page=".page";
        String jsonView="jsonView";
        if(url.endsWith(json)){
            if(e instanceof ParamsException || e instanceof PermissionException){
                log.error("ParamException or PermissionException:"+url,e);
                JsonData result=JsonData.fail(e.getMessage());
                mv=new ModelAndView(jsonView,result.toMap());
            }else{
                log.error("unknow Json Exception:"+url,e);
                JsonData result=JsonData.fail(defaultMsg);
                mv=new ModelAndView(jsonView,result.toMap());
            }
            return mv;
        }else if(url.endsWith(page)){
                log.error("unknow page exception: "+url,e);
                JsonData res=JsonData.fail(e.getMessage());
                mv=new ModelAndView("exception",res.toMap());
                return mv;
        }else {
            log.error("unknow exception:"+url,e);
            JsonData res = JsonData.fail(defaultMsg);
            mv = new ModelAndView(jsonView, res.toMap());
            return mv;
        }
    }

}
