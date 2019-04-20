package com.mao.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext=applicationContext;
    }

    public static <T> T popBean(Class<T> bean){
        if(applicationContext==null){
            return null;
        }
        return applicationContext.getBean(bean);
    }
    public static <T> T popBean(String className,Class<T> clazz){
        if(applicationContext==null){
            return null;
        }

        return applicationContext.getBean(className,clazz);

    }
}
