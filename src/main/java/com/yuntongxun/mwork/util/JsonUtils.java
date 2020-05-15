package com.yuntongxun.mwork.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @program: mwork
 * @description: json
 * @author: liugang
 * @create: 2020-05-15 15:03
 **/
public class JsonUtils {

    private static final Gson gson;

    static {
        gson = buildGson();
    }

    /**
     * build Gson
     * @return
     */
    public static Gson buildGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    /**
     * object to json
     * @param obj
     * @return
     */
    public static String gsonObjectToJson(Object obj){
        if (obj == null) {
            return null;
        }
        return gson.toJson(obj);
    }
}
