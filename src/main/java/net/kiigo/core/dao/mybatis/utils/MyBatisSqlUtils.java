package net.kiigo.core.dao.mybatis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.SqlSession;

/******
 * mybatisSql sql工具类
 * @author Coollf
 *
 */
public class MyBatisSqlUtils {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

	/***
	 * 运行时获取获取sql
	 * 
	 * @param session
	 * @param id
	 * @param parameterMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static MyBatisSql getMyBatisSql(SqlSession session, String id,Map<String, Object> parameterMap) {
		MyBatisSql ibatisSql = new MyBatisSql();
		MappedStatement ms = session.getConfiguration().getMappedStatement(id);
		BoundSql boundSql = ms.getBoundSql(parameterMap);
		ibatisSql.setSql(boundSql.getSql());
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		if (parameterMappings != null) {
			Object[] parameterArray = new Object[parameterMappings.size()];
			ParameterMapping parameterMapping = null;
			Object value = null;
			Object parameterObject = null;
			MetaObject metaObject = null;
			PropertyTokenizer prop = null;
			String propertyName = null;
			String[] names = null;
			for (int i = 0; i < parameterMappings.size(); i++) {
				parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					propertyName = parameterMapping.getProperty();
					names = propertyName.split("\\.");
					if (propertyName.indexOf(".") != -1 && names.length == 2) {
						parameterObject = parameterMap.get(names[0]);
						propertyName = names[1];
					} else if (propertyName.indexOf(".") != -1
							&& names.length == 3) {
						parameterObject = parameterMap.get(names[0]); // map
						if (parameterObject instanceof Map) {
							parameterObject = ((Map) parameterObject)
									.get(names[1]);
						}
						propertyName = names[2];
					} else {
						parameterObject = parameterMap.get(propertyName);
					}
					metaObject = parameterMap == null ? null : MetaObject
							.forObject(parameterObject, DEFAULT_OBJECT_FACTORY,
									DEFAULT_OBJECT_WRAPPER_FACTORY);
					prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (ms.getConfiguration().getTypeHandlerRegistry()
							.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName
							.startsWith(ForEachSqlNode.ITEM_PREFIX)
							&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = MetaObject.forObject(value,
									DEFAULT_OBJECT_FACTORY,
									DEFAULT_OBJECT_WRAPPER_FACTORY).getValue(
									propertyName.substring(prop.getName()
											.length()));
						}
					} else {
						value = metaObject == null ? null : metaObject
								.getValue(propertyName);
					}
					parameterArray[i] = value;
				}
			}
			ibatisSql.setParameters(parameterArray);
		}
		return ibatisSql;
	}

	public static class MyBatisSql {
		/** * 运行期 sql */
		private String sql;
		/** * 参数 数组 */
		private Object[] parameters;

		public void setSql(String sql) {
			this.sql = sql;
		}

		/**
		 * @return 无参数值sql【方便测试】
		 */
		public String getSql() {
			return sql;
		}

		/**
		 * @return 有参数值sql【方便测试】
		 */
		public String getBindParamsSql() {
			return coventToSql(1);
		}

		/**
		 * @return 有参数格式优化sql【方便保存】
		 */
		public String getBeautifyBindParamsSql() {
			return coventToSql(2);
		}

		private String coventToSql(int type) {
			if (parameters == null || sql == null) {
				return "";
			}
			List<Object> parametersArray = Arrays.asList(parameters);
			List<Object> list = new ArrayList<Object>(parametersArray);
			while (sql.indexOf("?") != -1 && list.size() > 0
					&& parameters.length > 0) {
				sql = sql.replaceFirst("\\?", "'" + list.get(0).toString()
						+ "'");
				list.remove(0);
			}
			if (type == 2) {
				sql = sql.replaceAll("\\s|\t|\r|\n+", "\t");
				return sql.replaceAll("\t+", " ");
			}
			return sql.replaceAll("(\r?\n(\\s*\r?\n)+)", "\r\n");
		}

		public void setParameters(Object[] parameters) {
			this.parameters = parameters;
		}

		public Object[] getParameters() {
			return parameters;
		}

	}
}
