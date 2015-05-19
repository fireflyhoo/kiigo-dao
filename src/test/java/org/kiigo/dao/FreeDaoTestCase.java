package org.kiigo.dao;

import java.util.HashMap;

import net.kiigo.core.context.KGApplicationContextManger;
import net.kiigo.core.dao.DaoFactory;
import net.kiigo.core.dao.FreeDao;
import net.kiigo.core.dao.transaction.KGTransactionManager;
import net.kiigo.core.dao.transaction.KGTransactionWork;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.TransactionStatus;

public class FreeDaoTestCase {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public  void testInsertRetuenKey() throws Exception { 
		KGTransactionManager.execute(new KGTransactionWork() {

			@Override
			public Object doWork(TransactionStatus status) throws Exception {
				String sqlId = "org.kiigo.test.addUser";
				FreeDao freeDao = DaoFactory.createFreeDao();
				HashMap<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("name", "JTA");
				System.out.println(freeDao.insertReadKey("org.kiigo.test.addUser",parameter));
				long start = System.currentTimeMillis();
				freeDao.insertReadKey(sqlId, parameter);
				System.out.println("first  elapsed time:"+(System.currentTimeMillis() - start)+" milliseconds"); 
				
				start = System.currentTimeMillis();
				freeDao.insertReadKey(sqlId, parameter);
				System.out.println("second elapsed time:"+(System.currentTimeMillis() - start)+" milliseconds"); 
				
				start = System.currentTimeMillis();
				freeDao.insertReadKey(sqlId, parameter);
				System.out.println("third elapsed time:"+(System.currentTimeMillis() - start)+" milliseconds"); 
				
				start = System.currentTimeMillis();
				freeDao.insertReadKey(sqlId, parameter);
				System.out.println("four elapsed time:"+(System.currentTimeMillis() - start)+" milliseconds"); 
				
				start = System.currentTimeMillis();
				freeDao.insert(sqlId, parameter);
				System.out.println("just insert elapsed time:"+(System.currentTimeMillis() - start)+" milliseconds"); 
				
				
				String sql = "select count(*) from kiigo_dao where 1=1";
				JdbcTemplate jdbcTemplate =  KGApplicationContextManger.getApplicationContext().getBean(JdbcTemplate.class);
				jdbcTemplate.queryForObject(sql, Long.class);
				jdbcTemplate.queryForObject(sql, Long.class);
				jdbcTemplate.queryForObject(sql, Long.class);
				jdbcTemplate.queryForObject(sql, Long.class);
				jdbcTemplate.queryForObject(sql, Long.class);
				throw new RuntimeException("测试下下实物.");
			}
		});



		
	}	
}
