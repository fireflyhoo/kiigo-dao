package net.kiigo.core.dao.mybatis.dialect.impl;

import net.kiigo.core.dao.mybatis.dialect.Dialect;

public class ORACLEDialect  implements Dialect{

	public String getPageSql(String sql, long offset, int pagesize) {
		StringBuffer sb =new StringBuffer();
		sb.append("SELECT * FROM ").append("\n");
		sb.append("(").append("\n");
		sb.append("	SELECT A.*, ROWNUM RN ").append("\n");
		sb.append("	FROM (").append(sql).append(") A ").append("\n");
		sb.append("	WHERE ROWNUM < ").append(offset + pagesize).append("\n");
		sb.append(")").append("\n");
		sb.append("WHERE RN >= ").append(offset).append("\n");
		return sb.toString();
	}

	@Override
	public String getCountSql(String sql) {
		int index = sql.toUpperCase().indexOf(" FROM ");
		if(index == -1){
			throw new RuntimeException(String.format("SQL[%s],不存在from 关键字. ",sql));
		}
		return "SELECT COUNT(1) " + sql.substring(index);
	}

}
