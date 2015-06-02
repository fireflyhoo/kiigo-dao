package net.kiigo.core.dao.mybatis.pagination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import net.kiigo.core.dao.JdbcConstants;
import net.kiigo.core.dao.mybatis.dialect.Dialect;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

/***
 * 实现数据库分页
 * @author Coollf
 *
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class })})
public class PaginationInterceptor implements Interceptor {

	public Object intercept(Invocation invocation) throws Throwable {
		   StatementHandler statementHandler = (StatementHandler)invocation.getTarget(); 
		   Connection connection = (Connection) invocation.getArgs()[0];
           MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory());
           RowBounds rowBounds = (RowBounds)metaStatementHandler.getValue("delegate.rowBounds");
           if(rowBounds == null || rowBounds == RowBounds.DEFAULT){
                   return invocation.proceed();
           }
           Dialect.Type databaseType  = null;
           String typeName  =  getDbType(connection.getMetaData().getURL());
           try{
                 databaseType = Dialect.Type.valueOf(typeName.toUpperCase());
           } catch(Exception e){
           }
           
           if(databaseType == null){
                   throw new RuntimeException("The  DateBase type  ["+ typeName + "] can not support");
           }
           Dialect dialect = new Dialect.Builder(databaseType).buildDialect();
           String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
           
           metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getPageSql(originalSql, rowBounds.getOffset(), rowBounds.getLimit()) );
           metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
           metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
           MappedStatement mappedStatement = (MappedStatement)metaStatementHandler.getValue("delegate.mappedStatement");  
           BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
           
           String count_originalSql = dialect.getCountSql(originalSql);
          
           PreparedStatement preparedStatement = connection.prepareStatement(count_originalSql);
           
           Object parameterObject =   boundSql.getParameterObject();
           setParameters(preparedStatement, mappedStatement, boundSql, parameterObject);
           ResultSet res  = preparedStatement.executeQuery();
           res.next();
           int size = res.getInt(1);
           res.close();
           preparedStatement.close();
           connection.clearWarnings();
           PagingUtils.setQueryItemsSize(size);
           Object object = invocation.proceed();
           
           return object;
	}

	/***
	 * 获取数据库类型
	 * @param connection
	 * @return
	 */
	private String getDbType(String  rawUrl) {
		if (rawUrl == null) {
            return null;
        }

        if (rawUrl.startsWith("jdbc:derby:")) {
            return JdbcConstants.DERBY;
        } else if (rawUrl.startsWith("jdbc:mysql:") || rawUrl.startsWith("jdbc:cobar:")) {
            return JdbcConstants.MYSQL;
        } else if (rawUrl.startsWith("jdbc:log4jdbc:")) {
            return JdbcConstants.LOG4JDBC;
        } else if (rawUrl.startsWith("jdbc:mariadb:")) {
            return JdbcConstants.MARIADB;
        } else if (rawUrl.startsWith("jdbc:oracle:")) {
            return JdbcConstants.ORACLE;
        } else if (rawUrl.startsWith("jdbc:alibaba:oracle:")) {
            return JdbcConstants.ALI_ORACLE;
        } else if (rawUrl.startsWith("jdbc:microsoft:")) {
            return JdbcConstants.SQL_SERVER;
        } else if (rawUrl.startsWith("jdbc:sqlserver:")) {
            return JdbcConstants.SQL_SERVER;
        } else if (rawUrl.startsWith("jdbc:sybase:Tds:")) {
            return JdbcConstants.SYBASE;
        } else if (rawUrl.startsWith("jdbc:jtds:")) {
            return JdbcConstants.JTDS;
        } else if (rawUrl.startsWith("jdbc:fake:") || rawUrl.startsWith("jdbc:mock:")) {
            return JdbcConstants.MOCK;
        } else if (rawUrl.startsWith("jdbc:postgresql:")) {
            return JdbcConstants.POSTGRESQL;
        } else if (rawUrl.startsWith("jdbc:hsqldb:")) {
            return JdbcConstants.HSQL;
        } else if (rawUrl.startsWith("jdbc:db2:")) {
            return JdbcConstants.DB2;
        } else if (rawUrl.startsWith("jdbc:sqlite:")) {
            return "sqlite";
        } else if (rawUrl.startsWith("jdbc:ingres:")) {
            return "ingres";
        } else if (rawUrl.startsWith("jdbc:h2:")) {
            return JdbcConstants.H2;
        } else if (rawUrl.startsWith("jdbc:mckoi:")) {
            return "mckoi";
        } else if (rawUrl.startsWith("jdbc:cloudscape:")) {
            return "cloudscape";
        } else if (rawUrl.startsWith("jdbc:informix-sqli:")) {
            return "informix";
        } else if (rawUrl.startsWith("jdbc:timesten:")) {
            return "timesten";
        } else if (rawUrl.startsWith("jdbc:as400:")) {
            return "as400";
        } else if (rawUrl.startsWith("jdbc:sapdb:")) {
            return "sapdb";
        } else if (rawUrl.startsWith("jdbc:JSQLConnect:")) {
            return "JSQLConnect";
        } else if (rawUrl.startsWith("jdbc:JTurbo:")) {
            return "JTurbo";
        } else if (rawUrl.startsWith("jdbc:firebirdsql:")) {
            return "firebirdsql";
        } else if (rawUrl.startsWith("jdbc:interbase:")) {
            return "interbase";
        } else if (rawUrl.startsWith("jdbc:pointbase:")) {
            return "pointbase";
        } else if (rawUrl.startsWith("jdbc:edbc:")) {
            return "edbc";
        } else if (rawUrl.startsWith("jdbc:mimer:multi1:")) {
            return "mimer";
        } else {
            return null;
        }
	}


	/***
	 * 设置查询语句
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,  
	        Object parameterObject) throws SQLException {  
	    ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);  
	    parameterHandler.setParameters(ps);  
	}  
	
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);  
	}
	public void setProperties(Properties properties) {}
}
