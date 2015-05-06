package net.kiigo.core.dao.mybatis.pagination;
/***
 * 分页工具
 * @author Coollf
 *
 */
public class PagingUtils {
	
	private static ThreadLocal<Long>  currentQuerySize = new ThreadLocal<Long>();
	
	public static long getQueryItemsSize(){
		long querySize = currentQuerySize.get();
		currentQuerySize.remove();
		return querySize;
	}
	
	public static void setQueryItemsSize(long totoleSeize){
		currentQuerySize.remove();
		currentQuerySize.set(totoleSeize);
	}	
}
