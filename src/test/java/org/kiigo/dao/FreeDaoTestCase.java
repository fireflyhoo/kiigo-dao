package org.kiigo.dao;

import java.util.HashMap;

import net.kiigo.core.dao.DaoFactory;
import net.kiigo.core.dao.FreeDao;

import org.junit.Test;

public class FreeDaoTestCase {
	
	@Test
	public  void testInsertRetuenKey() throws Exception { 
		String sqlId = "org.kiigo.test.addUser";
		FreeDao freeDao = DaoFactory.createFreeDao();
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("name", "尼玛");
		System.out.println(freeDao.insertReadKey("org.kiigo.test.addUser",parameter));
		long start = System.currentTimeMillis();
		freeDao.insertReadKey(sqlId, parameter);
		
		System.out.println(System.currentTimeMillis() - start); 
		
	}	
}
