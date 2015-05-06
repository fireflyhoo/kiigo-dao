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
//		throw new UnsupportedOperationException("方法还未实现");
	}

	public String getCountSql() {
		// TODO Auto-generated method stub
		return null;
	}

}
