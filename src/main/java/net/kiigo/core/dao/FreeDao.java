package net.kiigo.core.dao;

import java.util.List;



/**
 * 持久层
 * @author Coollf
 *
 */
public interface FreeDao {
	/**
	 * 查询列表
	 * @param sqlId sqlID
	 * @param params 参数
	 * @param T 类型
	 * @return
	 */
	public <T> List<T> query(String sqlId,Object parameter, Class<T> ...T )throws Exception;
	
	/**
	 * 分页查询
	 * @param sqlId
	 * @param parameter
	 * @param offset
	 * @param pageSize
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T>PageCollection<T> query(String sqlId,Object parameter ,long offset,int pageSize,Class<T> ...T )throws Exception;
	
	
	/***
	 * 
	 *分页查询结果. <br/> 
	 * 
	 * @author Coollf
	 * @date: 2015年12月25日 下午4:40:54
	 * @version 1.0
	 *
	 * @param sqlId
	 * @param parameter
	 * @param pageIndex
	 * @param pageSize
	 * @param T
	 * @return
	 * @throws Exception
	 */
	public <T>PageCollection<T> queryPage(String sqlId,Object parameter ,int pageIndex,int pageSize,Class<T> T)throws Exception;
	
	
	/**
	 * 查询单个对象
	 * @param sqlId
	 * @param params
	 * @param T
	 * @return
	 */
	public <T> T queryObject(String sqlId,Object parameter, Class<T> ...T )throws Exception;
	
	
	
	/**
	 * 新增记录
	 * @param sqlId
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public boolean insert(String sqlId,Object parameter)throws Exception;
	
	
	
	
	/***
	 * 插入记录返回数据库生成的主键.
	 * @param sqlId
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public Object insertReadKey(String sqlId,Object parameter)throws Exception;
	
	
	/***
	 * 批量插入记录
	 * @param sqlId
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public boolean insert(String sqlId,Object... parameter)throws Exception;
	
	
	
	/**
	 * 修改
	 * @param sqlId
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public boolean modify(String sqlId,Object parameter)throws Exception;
	
	
	/**
	 * 删除数据
	 * @param sqlId
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public boolean remove(String sqlId,Object parameter)throws Exception;
	
}
