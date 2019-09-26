package org.share.odies.core.utils;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TransferTool {

    public static Map<String,String> obj2Map(Object object){
        Map<String,String> map = new HashMap<>();
        if (object == null)
            return map;

        for (Field field : object.getClass().getDeclaredFields()){
            try {
                field.setAccessible(true);
                map.put(field.getName(),  field.get(object).toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T> T map2Obj(Map<byte[],byte[]>  map, Class<T> clazz){
        if (map == null)
            return null;


        String json = JSON.toJSONString(map);
        return JSON.parseObject(json,clazz);

    }


    public static void main(String[] args){

    }


}
