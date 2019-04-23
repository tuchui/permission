package com.mao.common;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class JsonData {
    private boolean ret;
    private String msg;
    private Object obj;
    public JsonData(boolean ret){
        this.ret=ret;
    }

    public static JsonData success(String msg){
        JsonData jsonData=new JsonData(true);
        jsonData.msg=msg;
        return jsonData;
    }
    public static JsonData success(String msg,Object obj){
        JsonData jsonData=new JsonData(true);
        jsonData.msg=msg;
        jsonData.obj=obj;
        return jsonData;
    }
    public static JsonData success(Object obj){
        JsonData jsonData=new JsonData(true);
        jsonData.obj=obj;
        return jsonData;
    }
    public static JsonData success(){
        JsonData jsonData=new JsonData(true);
        return jsonData;
    }
    public  static JsonData fail(String msg){
        JsonData jsonData=new JsonData(false);
        jsonData.msg=msg;
        return jsonData;
    }

    public  Map<String,Object> toMap(){
        HashMap<String,Object> map=new HashMap<>();
        map.put("msg",msg);
        map.put("ret",ret);
        map.put("obj",obj);
        return map;
    }

}
