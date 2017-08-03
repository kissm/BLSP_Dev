package com.framework.core.base.service.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD})
@Inherited
public @interface InterfaceLog {
	String code() default "";
	String directionType() default "1";
	String inMemberTokenProperty() default "";
	String inSysIdProperty() default "";
	String inChannelProperty() default "";
	
//	String OutOrderNoProperty() default "";
//	String OutOrderSubNoProperty() default "";
//	String OutOrderItemNoProperty() default "";
//	String OutOrderIdProperty() default "";
//	String OutOrderSubIdProperty() default "";
//	String OutOrderItemIdProperty() default "";
}
