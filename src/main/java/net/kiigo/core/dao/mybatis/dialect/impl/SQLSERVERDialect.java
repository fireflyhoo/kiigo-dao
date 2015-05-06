package net.kiigo.core.dao.mybatis.dialect.impl;

import net.kiigo.core.dao.mybatis.dialect.Dialect;

public class SQLSERVERDialect implements Dialect {

	public String getPageSql(String sql, long offset, int pagesize) {
		String[] querySelects = getQuerySelects(sql);
		StringBuffer result = new StringBuffer(sql.length() + 100);
		result.append("select * from(");
		result.append(querySelects[0]);
		result.append(",row_number()over(");
		result.append(querySelects[2]);
		result.append(") as row");
		result.append(querySelects[1]);
		result.append(") tmp where tmp.row between ");
		result.append(offset + 1);
		result.append(" and ");
		result.append(offset);
		return result.toString();
	}

	// 通过sql语句，得到相应的select、from and where、order by三条语句
	private String[] getQuerySelects(String querySelect) {
		String[] result = new String[3];
		if (querySelect.indexOf(" from ") == -1
				|| querySelect.indexOf(" order by ") == -1) {
			throw new RuntimeException("不支持不带from和order by的分页查询语句");
		}
		String select = querySelect.substring(0, querySelect.indexOf(" from "));
		// while (select.endsWith(" ")) {
		// select.substring(0, select.length() - 1);
		// }
		result[0] = select;
		result[1] = querySelect.substring(querySelect.indexOf(" from "),
				querySelect.indexOf(" order by "));
		result[2] = querySelect.substring(querySelect.indexOf(" order by "));
		return result;
	}

	public String getCountSql() {
		// TODO Auto-generated method stub
		return null;
	}

}
