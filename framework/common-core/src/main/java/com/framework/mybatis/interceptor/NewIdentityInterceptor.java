package com.framework.mybatis.interceptor;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.framework.core.utils.Base58;
import com.framework.mybatis.ext.MapperProxyExt;

/**
 *
 * 主键自动赋值实现
 */
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class NewIdentityInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        if (mappedStatement.getSqlCommandType() != SqlCommandType.INSERT) {
            return invocation.proceed();
        }

        Object obj = invocation.getArgs()[1];
        if(obj instanceof Identity){
            Identity identity = (Identity) obj;
            if (identity!=null&& (identity.getId() == null || "".equals(identity.getId()))) {
                identity.setId(Base58.compressedUUID());
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);

    }

    @Override
    public void setProperties(Properties properties) {

    }

}