package com.framework.core.exception;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

/**
 * 
 * @Class ExceptionLog 对加注了@Service 标注的类进行拦截，记录执行情况
 * @Author guowenqin
 * @Create In 2014年9月28日
 */
public class ExceptionLog implements MethodInterceptor, Ordered {

    private int order;

    /** logger */
    private static final Logger logger = LoggerFactory
            .getLogger(ExceptionLog.class);

    /** 调用时间阀值 */
    protected long threshold = 2000L;

    /*
     * 执行调用方法
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {

        Method method = invocation.getMethod();
        Object target = invocation.getThis();

        String methodName = target.getClass().getSimpleName() + "."
                + method.getName();
//        System.out.println(methodName);
        try {
            Object returnObject = invocation.proceed();
            return returnObject;
        } catch (BusinessException e) {
            logger.trace(e.getCode() + ":" + methodName + ":",
                    e.getLocalizedMessage());
            throw e;
        } catch (Throwable t) {
            logger.error("服务拦截器调用" + methodName + "异常！", t);
            throw t;
        }
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
