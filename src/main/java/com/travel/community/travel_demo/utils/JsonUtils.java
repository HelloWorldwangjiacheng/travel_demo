package com.travel.community.travel_demo.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * 自己写一个Json转化的工具类
 */
public class JsonUtils {

    public static String getJson(Object object){
        return getDateJson(object,"yyyy-MM-dd HH:mm:ss");
    }

    //这个dataFormat可以为"yyyy-MM-dd HH:mm:ss"
    public static String getDateJson(Object object, String dataFormat){
        //创建一个jackson的mapper
        ObjectMapper objectMapper = new ObjectMapper();
        //如何让系统不返回时间戳！所以我们要关闭它的时间戳功能
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //时间格式化问题！自定日期格式对象
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataFormat);
        //让objectMapper指定时间日期格式为simpleDateFormat
        objectMapper.setDateFormat(simpleDateFormat);

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
