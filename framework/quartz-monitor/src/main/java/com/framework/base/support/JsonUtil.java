package com.framework.base.support;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author
 * 
 */
public class JsonUtil {

    /**
     * 把数据对象转换成JSON字符串
     * 
     * @param obj
     * @return
     */
    public static String getJSONString(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * 从一个JSON对象字符格式中得到一个java对象
     * 
     * @param object
     * @param clazz
     * @return
     */
    public static <T> T getDTO(String jsonString, Class<T> clazz) {
        return new Gson().fromJson(new JsonParser().parse(jsonString), clazz);
    }
    
    public static  Object getDTO(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }
    
    /**
     * 按照指定的日期格式进行转换
     * 
     * @param jsonString
     * @param dateFormat
     * @param clazz
     * @return
     */
    public static <T> T getDTO(String jsonString, String dateFormat, Class<T> clazz) {
    	Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
    	
		return gson.fromJson(new JsonParser().parse(jsonString), clazz);
    }
    
    /**
     * json数组字符串转list
     * @param <T>
     * @param jsonString
     * @param clazz
     * @return
     */
    public static <T> List<T> getListDTO(String jsonString, Class<T> clazz){
    	List<T> retList=new ArrayList<T>();
    	Gson gson=new Gson();
    	Type  type=new TypeToken<List<T>>(){}.getType();
    	List<T> tmpList = gson.fromJson(jsonString,type); 
    	for(int i=0;i<tmpList.size();i++){
    		String objStr=gson.toJson(tmpList.get(i), LinkedHashMap.class);
    		T obj=gson.fromJson(objStr ,clazz);
    		retList.add(obj);
    	}
    	return retList;
    }
}
