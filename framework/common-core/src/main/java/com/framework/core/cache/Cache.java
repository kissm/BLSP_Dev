package com.framework.core.cache;


import java.lang.annotation.*;

import com.framework.core.cache.keygenerator.CacheKeyGenerator;
import com.framework.core.cache.keygenerator.ClassMethodArgsCacheKeyGenerator;

/**
 * 为方法加上缓存
 *
 * Created by gyh on 2014-05-06.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Cache {

  /**
   * 缓存key
   *
   * @return
   */
  String key() default "";

  /**
   * 缓存key产生器
   *
   * @return
   */
  Class<? extends CacheKeyGenerator> keyGenerator() default ClassMethodArgsCacheKeyGenerator.class;// former CacheKeyGenerator


  /**
   * 是否开启Redis缓存，默认开启
   *
   * @return
   */
  boolean redisEnable() default true;

  /**
   * Redis缓存的过期时间
   *
   * @return
   */
  int redisExpiredTime() default 60 * 60;//1小时

  /**
   * 使用Redis中hash结构存储缓存的hash名称
   *
   * @return
   */
  String redisMapKey() default "";
  
  String returnObj() default "";
  
  boolean isList() default false;


}