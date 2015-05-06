package net.kiigo.core.dao.mybatis.dialect;

import net.kiigo.core.dao.mybatis.dialect.Dialect.Type;
import net.kiigo.core.dao.mybatis.dialect.impl.DB2Dialect;
import net.kiigo.core.dao.mybatis.dialect.impl.MYSQLDialect;
import net.kiigo.core.dao.mybatis.dialect.impl.ORACLEDialect;
import net.kiigo.core.dao.mybatis.dialect.impl.SQLSERVERDialect;

public class DialectFactory {
	static Dialect creater(Type type){
		Dialect dialect = null;
		switch (type) {
			case MYSQL:
				dialect = new MYSQLDialect();
				break;
			case ORACLE:
				dialect = new ORACLEDialect();
				break;
			case SQLSERVER:
				dialect = new SQLSERVERDialect();
				break;
			case DB2:
				dialect = new DB2Dialect();
			default :
				throw new RuntimeException("数据库方言类型不正确！");
		}
		return dialect;
	}
}
