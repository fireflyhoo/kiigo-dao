package net.kiigo.core.dao.mybatis.dialect.impl;

import net.kiigo.core.dao.mybatis.dialect.Dialect;

public class DB2Dialect  implements Dialect {

	public String getPageSql(String sql, long offset, int pagesize) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("方法还未实现");
	}

	public String getCountSql() {
		// TODO Auto-generated method stub
		return null;
	}

}
