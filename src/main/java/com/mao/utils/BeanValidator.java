package com.mao.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mao.exception.ParamsException;
import org.apache.commons.collections.MapUtils;

import javax.validation.*;
import java.util.*;

public class BeanValidator {
    private static ValidatorFactory validatorFactory= Validation.buildDefaultValidatorFactory();
    public static <T>Map<String,String> validate(T clazz,Class... groups){
        //1生成valdator实例
        //2 进行校验,返回校验结果
        //3 判断校验结果是否为null
        //3.1 为null 返回空Collections
        //3.2 不为null 把集合存到map中
        Validator validator=validatorFactory.getValidator();
        Set valRes=validator.validate(clazz,groups);
        if(valRes.isEmpty()){
            return Collections.emptyMap();
        }else{
            Iterator iterator=valRes.iterator();
            LinkedHashMap<String,String> error= Maps.newLinkedHashMap();
            while (iterator.hasNext()){
                ConstraintViolation constraintViolation=(ConstraintViolation)iterator.next();
                error.put(constraintViolation.getPropertyPath().toString(),constraintViolation.getMessage());
            }
            return error;
        }
    }
    public static Map<String,String> validateList(Collection<?> collections){
        Preconditions.checkNotNull(collections);
        Map error;
        Iterator iterator=collections.iterator();
        do{
            if(!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object obj=iterator.next();
            error=validate(obj,new Class[0]);
        }while (error.isEmpty());
        return error;
    }
    public static Map<String,String> validateObject(Object obj,Object... objects){
        if(obj!=null && objects.length>0){
            return validateList(Lists.asList(obj,objects));
        }else {
            return validate(obj,new Class[0]);
        }
    }

    public static void check(Object params) throws ParamsException{
     Map<String,String> results= BeanValidator.validate(params);
     if(MapUtils.isNotEmpty(results)){
         throw new ParamsException(results.toString());
     }
    }
}
