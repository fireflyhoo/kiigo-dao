package net.kiigo.core.dao.mybatis.dialect;


/***
 * 数据库方言接口
 * 
 * @author Coollf
 * 
 */
public interface Dialect {
	public static enum Type {
		MYSQL {
			@Override
			public String getValue() {
				return "mysql";
			}
		},
		SQLSERVER {
			@Override
			public String getValue() {
				return "sqlserver";
			}
		},
		ORACLE {
			@Override
			public String getValue() {
				return "oracle";
			}
		},
		DB2 {
			@Override
			public String getValue() {
				return "db2";
			}

		};

		public abstract String getValue();
	}

	public String getPageSql(String sql, long offset, int pagesize);
	
	public String getCountSql(String sql);

	public static class Builder {
		Type type;

		public Builder(Type type) {
			this.type = type;
		}

		public Builder(String type) {
			this.type = Type.valueOf(type);
		}

		public Dialect buildDialect() {
			return DialectFactory.creater(type); //构造数据库方言
		}
	}
}
