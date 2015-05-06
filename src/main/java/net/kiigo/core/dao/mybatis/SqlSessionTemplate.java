package net.kiigo.core.dao.mybatis;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.dao.support.PersistenceExceptionTranslator;

public class SqlSessionTemplate extends org.mybatis.spring.SqlSessionTemplate {

	public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory,
			ExecutorType executorType,
			PersistenceExceptionTranslator exceptionTranslator) {
		super(sqlSessionFactory, executorType, exceptionTranslator);
	}

	public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory,
			ExecutorType executorType) {
		super(sqlSessionFactory, executorType);
	}

	public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}
	
	 /**
	   * {@inheritDoc}
	   */
	  public void commit() {
//	    throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
	  }

	  /**
	   * {@inheritDoc}
	   */
	  public void commit(boolean force) {
//	    throw new UnsupportedOperationException("Manual commit is not allowed over a Spring managed SqlSession");
	  }

	  /**
	   * {@inheritDoc}
	   */
	  public void rollback() {
//	    throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
	  }

	  /**
	   * {@inheritDoc}
	   */
	  public void rollback(boolean force) {
//	    throw new UnsupportedOperationException("Manual rollback is not allowed over a Spring managed SqlSession");
	  }

	  /**
	   * {@inheritDoc}
	   */
	  public void close() {
//	    throw new UnsupportedOperationException("Manual close is not allowed over a Spring managed SqlSession");
	  }

}
