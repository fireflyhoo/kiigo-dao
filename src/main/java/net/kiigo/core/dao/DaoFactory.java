package net.kiigo.core.dao;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.kiigo.core.dao.impl.FreeDaoMybatisImpl;

public class DaoFactory {	
	private static FreeDao freeDao  = new FreeDaoMybatisImpl();	
	
	private static ConcurrentMap<String, FreeDao> freeDaos =new ConcurrentHashMap<String, FreeDao>();
	
	public static FreeDao createFreeDao() {
		return freeDao;
	}
	
	/***
	 * 创建dao
	 * @param sessionFactoryName dao
	 * @return
	 */
	public static FreeDao createFreeDao(String sessionName){
		FreeDao freeDao = freeDaos.get(sessionName);
		if(freeDao != null){
			return freeDao;
		}
		synchronized(DaoFactory.class){
			freeDao = new FreeDaoMybatisImpl(sessionName);
			freeDaos.put(sessionName, freeDao);
		}
		return freeDao;
	}
}
