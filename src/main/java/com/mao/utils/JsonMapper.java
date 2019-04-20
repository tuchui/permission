package com.mao.utils;


import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;

@Slf4j
public class JsonMapper {
    private static ObjectMapper objectMapper=new ObjectMapper();
    static {
        // config
        objectMapper.disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setFilters(new SimpleFilterProvider().setFailOnUnknownId(false));
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
    }
    public static <T> String obj2String(T src){
        if (src==null){
            return null;
        }
        try {
            return src instanceof String ? (String) src: objectMapper.writeValueAsString(src);
        } catch (IOException e) {
            log.error("parse object to string exception {}",e);
            return null;
        }
    }
    public static <T> T string2Obj(String src, TypeReference<T> typeReference){
        if(src==null || typeReference==null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class)?(T) src:objectMapper.readValue(src,typeReference);
        } catch (IOException e) {
            log.error("parse String to Obj Exception ,src {} ,TypeReference {}",src,typeReference,e);
            return null;
        }
    }
}
