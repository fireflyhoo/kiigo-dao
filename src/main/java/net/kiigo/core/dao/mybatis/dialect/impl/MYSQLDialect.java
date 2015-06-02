package net.kiigo.core.dao.mybatis.dialect.impl;

import net.kiigo.core.dao.mybatis.dialect.Dialect;

/**
 * MSQL数据库方言实现
 * 
 * @author Coollf
 * 
 */
public class MYSQLDialect implements Dialect {

	public String getPageSql(String sql, long offset, int pagesize) {
		StringBuffer sb = new StringBuffer();
		sb.append("").append(sql).append("")
				.append("  limit ").append(offset).append(",")
				.append(pagesize);
		
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
