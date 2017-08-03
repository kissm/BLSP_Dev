package com.framework.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.jdbc.ConnectionLogger;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import com.framework.core.base.page.Page;
import com.framework.mybatis.ext.BoundSqlProxy;
import com.framework.mybatis.ext.MapperProxyExt;
import com.framework.mybatis.ext.SqlSourceProxyBoundSql;

/**
 * 分页查询时把List放入参数page中并返回
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class NewPagingInterceptor implements Interceptor {

	private String dbDialect;

	public String getDbDialect() {
		return dbDialect;
	}

	public void setDbDialect(String dbDialect) {
		this.dbDialect = dbDialect;
	}

	// 存储所有语句名称
	HashMap<String, String> map_statement = new HashMap<String, String>();

	// 用户提供分页计算条数后缀
	static final String COUNT_ID = "_count";


	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object parameter = invocation.getArgs()[1];
		Page<?> page = null;
		if(parameter instanceof MapperMethod.ParamMap){
			MapperMethod.ParamMap<?> param = 
					(MapperMethod.ParamMap<?>)parameter;
			if(param.containsKey("page"))
				page = (Page<?>)param.get("page");
		}

		if (page == null) {
			return invocation.proceed();
		} else {
			return handlePaging(invocation, parameter, page);
		}

	}

	/**
	 * 处理分页的情况
	 * <p>
	 *
	 * @param invocation
	 * @param parameter
	 * @param page
	 * @throws SQLException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Object handlePaging(Invocation invocation, Object parameter, Page page) throws Exception {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Configuration configuration = mappedStatement.getConfiguration();
		if (map_statement.isEmpty()) {
			initStatementMap(configuration);
		}
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		SqlSource sqlsource = new SqlSourceProxyBoundSql(new BoundSqlProxy(configuration, page, boundSql));
		// 查询结果集
		MappedStatement.Builder builder = new MappedStatement.Builder(configuration, mappedStatement.getId(), sqlsource,
				SqlCommandType.SELECT);
		builder.resultMaps(mappedStatement.getResultMaps()).resultSetType(mappedStatement.getResultSetType())
				.statementType(mappedStatement.getStatementType());

		List data = (List) exeQuery(invocation, builder.build());
		// 设置到page对象
		page.setList(data);
		page.setCount(getTotalSize(invocation, configuration, mappedStatement, boundSql, parameter));
		List<Page> pageList = new ArrayList<Page>(1);
		pageList.add(page);
		return pageList;
		
		
	}

	/**
	 * 根据提供的语句执行查询操作
	 * <p>
	 *
	 * @param invocation
	 * @param query_statement
	 * @return
	 * @throws Exception
	 */
	protected Object exeQuery(Invocation invocation, MappedStatement query_statement) throws Exception {
		Object[] args = invocation.getArgs();
		return invocation.getMethod().invoke(invocation.getTarget(),
				new Object[] { query_statement, args[1], args[2], args[3] });
	}

	/**
	 * 获取总记录数量
	 * <p>
	 *
	 * @param configuration
	 * @param mappedStatement
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("rawtypes")
	protected int getTotalSize(Invocation invocation, Configuration configuration, MappedStatement mappedStatement,
			BoundSql boundSql, Object parameter) throws Exception {

		String count_id = mappedStatement.getId() + COUNT_ID;
		int totalSize = 0;
		if (map_statement.containsKey(count_id)) {
			// 优先查找能统计条数的sql
			List data = (List) exeQuery(invocation, mappedStatement.getConfiguration().getMappedStatement(count_id));
			if (data.size() > 0) {
				totalSize = Integer.parseInt(data.get(0).toString());
			}
		} else {
			Executor exe = (Executor) invocation.getTarget();
			Connection connection = getConnection(exe.getTransaction(), mappedStatement.getStatementLog());
			String countSql = getCountSql(boundSql.getSql());
			totalSize = getTotalSize(configuration, mappedStatement, boundSql, countSql, connection, parameter);
		}

		return totalSize;
	}

	/**
	 * 拼接获取条数的sql语句
	 * <p>
	 *
	 * @param sqlPrimary
	 */
	protected String getCountSql(String sqlPrimary) {
		String sqlUse = sqlPrimary.replaceAll("[\\s]+", " ");
		String upperString = sqlUse.toUpperCase();
		int order_by = upperString.lastIndexOf(" ORDER BY ");
		if (order_by > -1) {
			sqlUse = sqlUse.substring(0, order_by);
		}
		String[] paramsAndMethod = sqlUse.split("\\s");
		int count = 0;
		int index = 0;
		for (int i = 0; i < paramsAndMethod.length; i++) {
			String upper = paramsAndMethod[i].toUpperCase();
			if (upper.length() == 0) {
				continue;
			}
			if (upper.equals("SELECT")) {
				count++;
			} else if (upper.equals("FROM")) {
				count--;
			}
			if (count == 0) {
				index = i;
				break;
			}
		}
		StringBuilder return_sql = new StringBuilder("SELECT COUNT(1) AS cnt ");
		StringBuilder common_count = new StringBuilder();
		for (int j = index; j < paramsAndMethod.length; j++) {
			common_count.append(" ");
			common_count.append(paramsAndMethod[j]);
		}
		if (upperString.contains(" GROUP BY ")) {
			throw new RuntimeException("不支持group by 分页,请自行提供sql语句以 查询语句+_count结尾.");
		}
		return return_sql.append(common_count).toString();
	}

	/**
	 * 计算总条数
	 * <p>
	 *
	 * @param parameterObj
	 * @param countSql
	 * @param connection
	 * @return
	 */
	protected int getTotalSize(Configuration configuration, MappedStatement mappedStatement, BoundSql boundSql,
			String countSql, Connection connection, Object parameter) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalSize = 0;
		try {
			ParameterHandler handler = configuration.newParameterHandler(mappedStatement, parameter, boundSql);
			stmt = connection.prepareStatement(countSql);
			handler.setParameters(stmt);
			rs = stmt.executeQuery();
			if (rs.next()) {
				totalSize = rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new RuntimeException("获取总条数错误，请自行提供计算总页数的语句，以{id}_count结尾");
		} finally {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		}
		return totalSize;
	}
	
	/**
	 * 寻找page对象
	 * <p>
	 *
	 * @param parameter
	 */
	protected Page<?> seekPage(Invocation inv) {
		Page<?> page = null;
		
		Object[] args = inv.getArgs();
		if (args == null) {
			return null;
		}
		for (Object arg : args) {
			if (arg instanceof Page) {
				page = (Page<?>) arg;
				return page;
			}
		}
		return page;
	}
	


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
	}

	/**
	 * 获取数据库连接
	 * <p>
	 *
	 * @param transaction
	 * @param statementLog
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection(Transaction transaction, Log statementLog) throws SQLException {
		Connection connection = transaction.getConnection();
		if (statementLog.isDebugEnabled()) {
			return ConnectionLogger.newInstance(connection, statementLog, 0);
		} else {
			return connection;
		}
	}

	/**
	 * 获取所有statement语句的名称
	 * <p>
	 *
	 * @param configuration
	 */
	protected synchronized void initStatementMap(Configuration configuration) {
		if (!map_statement.isEmpty()) {
			return;
		}
		Collection<String> statements = configuration.getMappedStatementNames();
		for (String element : statements) {
			map_statement.put(element, element);
		}
	}

}