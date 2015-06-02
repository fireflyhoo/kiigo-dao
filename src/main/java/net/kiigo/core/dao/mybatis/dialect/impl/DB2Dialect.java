package net.kiigo.core.dao.mybatis.dialect.impl;

import net.kiigo.core.dao.mybatis.dialect.Dialect;

public class DB2Dialect  implements Dialect {

	public String getPageSql(String sql, long offset, int pagesize) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("方法还未实现");
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
