package com.framework.core.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import com.framework.core.utils.JsonUtil;
import com.framework.core.utils.RedisUtil;

/**
 * 对加注了@RedisCacheGet 标注的方法进行拦截，先查询缓存
 * 
 * 
 */
public class RedisCacheGetInterceptor implements MethodInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    private static boolean cacheFlag = true;

    public Object invoke(MethodInvocation invocation) throws Throwable {
        String redisName = "";
        String redisKey = "";
        String returnObj = "";
        boolean isList=false;
        String value = "";
        if (cacheFlag) {
        	//得到切点处方法的传入参数，并将参数当做查询缓存的key值
            Object[] object = invocation.getArguments();
            if (object.length > 0 && object[0] != null && object[0].getClass().equals(String.class)) {
                redisKey = object[0].toString();
                //得到切点处的方法
                Method method = invocation.getMethod();
                //从方法中得到该方法的注解
                Annotation[] annotations = method.getAnnotations();
                //对得到的注解数组进行遍历，找出@RedisCacheGet这个注解
                for (Annotation annotation : annotations) {
                    if (annotation.annotationType().isAssignableFrom(RedisCacheGet.class)) {
                    	//得到@RedisCacheGe，并获取器参数
                        RedisCacheGet redisCache = (RedisCacheGet) annotation;
                        redisName = redisCache.redisName();
                        returnObj = redisCache.returnObj();
                        isList = redisCache.isList();
                        break;
                    }
                }
                if (!redisName.isEmpty() && !redisKey.isEmpty()) {
                    try {
						value = redisUtil.getHSet(redisName, redisKey);
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
                    if (value != null && !value.isEmpty() && !"null".equals(value)) {
                        if (returnObj != null && !returnObj.isEmpty()) {
                        	//此处，将查询的值转换城 returnObj指定的返回类型的对象
                            return isList?JsonUtil.getListDTO(value, Class.forName(returnObj)):JsonUtil.getDTO(value, Class.forName(returnObj));
                        } else {
                            return value;
                        }
                    }
                }
            }
        }
        //如果没有从redis中查到相应的数据，那么就会执行注解所标记的的那个方法，并得到相应的返回结果
        //然后将返回结果保存到缓存中
        Object returnObject = invocation.proceed();
        if (cacheFlag) {
            // 建查询结果加入缓存
            if (!redisName.isEmpty() && !redisKey.isEmpty() && returnObject!=null) {
                redisUtil.setHSet(redisName, redisKey, JsonUtil.getJSONString(returnObject));
            }
        }
        return returnObject;
    }

    /**
     * @Return the boolean cachFlag
     */
    public static boolean isCacheFlag() {
        return cacheFlag;
    }

    /**
     * @Param boolean cachFlag to set
     */
    public static void setCacheFlag(boolean cacheFlag) {
        RedisCacheGetInterceptor.cacheFlag = cacheFlag;
    }
}
