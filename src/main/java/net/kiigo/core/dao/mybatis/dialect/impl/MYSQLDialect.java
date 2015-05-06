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
//		sb.append("SELECT * from ( ").append(sql).append(")")
//				.append(" Pagination limit ").append(offset).append(",")
//				.append(pagesize);
		sb.append("").append(sql).append("")
				.append("  limit ").append(offset).append(",")
				.append(pagesize);
		
		return sb.toString();
	}

	public String getCountSql() {
		// TODO Auto-generated method stub
		return null;
	}

}
