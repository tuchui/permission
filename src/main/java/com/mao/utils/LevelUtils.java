package com.mao.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author mao
 * @Description //TODO
 * @Date 23:08 2019/4/22
 **/
public class LevelUtils {

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4

    /**
     *root="0"
     */
    public final static String ROOT="0";
    /**
     * SEPERATOR="."
     */
    public final static String SEPERATOR=".";
    /**
     * @Author mao
     * @Description //TODO 如何构建level？  parentLevel . parentId
     * @Date 23:21 2019/4/22
     * @Param [parentLevel, parentId]
     * @return java.lang.String
     **/
    public static String calculateLevl(String parentLevel,Integer partentId){
        if(StringUtils.isEmpty(parentLevel)){
            return ROOT;
        }
       return StringUtils.join(parentLevel,SEPERATOR,partentId);
    }
}
