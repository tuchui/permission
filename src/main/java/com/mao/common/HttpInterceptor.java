package com.mao.common;

import com.mao.utils.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private static final String START_TIME="REQUEST_START_TIME";
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url=request.getRequestURI();
        Map parameterMap=request.getParameterMap();
        log.info("preHandle: request Url:{},request parameters {}",url, JsonMapper.obj2String(parameterMap));
        Long time=System.currentTimeMillis();
        request.setAttribute(START_TIME,time);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String url=request.getRequestURI();
        Long StartTime=(Long)request.getAttribute(START_TIME);
        Long EndTime=System.currentTimeMillis();
        Long CostTime=EndTime-StartTime;
        log.info("afterComplection url:{} cost time:{} ",url,CostTime);
    }
}
