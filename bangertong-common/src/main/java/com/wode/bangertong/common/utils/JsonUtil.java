package com.wode.bangertong.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int SUCCESS_CODE = 200;
    private static final int DATA_NOT_FIND_CODE = 10;
    private static final int PARAM_ERROR_CODE = 601;
    private static final int SERVER_ERROR_CODE = 500;

    public JsonUtil() {
    }

    public static String STATUS_OK(String message) {
        Map<String, Object> map = new HashMap();
        map.put("code", 200);
        map.put("message", message);
        return JSON.toJSONString(map);
    }

    public static String DATA_NOT_FIND() {
        Map<String, Object> map = new HashMap();
        map.put("code", 10);
        map.put("message", "no video");
        return JSON.toJSONString(map);
    }

    public static String PARAMETER_ERROR(String message) {
        Map<String, Object> map = new HashMap();
        map.put("code", 601);
        map.put("message", message);
        return JSON.toJSONString(map);
    }

    public static String EXCEPTION_ERROR() {
        Map<String, Object> map = new HashMap();
        map.put("code", 500);
        map.put("message", "server error");
        return JSON.toJSONString(map);
    }

    public static String TO_JSON(Object data) {
        return JSON.toJSONString(data);
    }

    public static Object TO_OBJ(String source, Class<?> clazz) {
        return JSON.parseObject(source, clazz);
    }

    public static Map<String, Object> parseMap(String source) {
        Map map = null;

        try {
            map = (Map)TO_OBJ(source, Map.class);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return map;
    }

    public static JSONArray TO_JSONArray(String jsonString) {
        return JSON.parseArray(jsonString);
    }

    public static JSONObject TO_JSONObject(String jsonString) {
        return (JSONObject)JSON.parse(jsonString);
    }
//
//    public static void main(String[] args) {
//    }
}