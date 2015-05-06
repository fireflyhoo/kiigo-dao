package net.kiigo.core.dao.transaction;

import net.kiigo.core.context.KGApplicationContextManger;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

/**
 * 事物管理器
 * @author Coollf
 *
 */
public class KGTransactionManager {
	public static <T> T execute(final KGTransactionWork<T> tran) {
		PlatformTransactionManager txManager = getTransactionManager();		
		return TransactionTemplateUtils.getDefaultTransactionTemplate(txManager).execute(new TransactionCallback<T>() {
			public T doInTransaction(TransactionStatus status) {				
				try {
					return tran.doWork(status);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});		
	}

	private static PlatformTransactionManager getTransactionManager() {		
		return KGApplicationContextManger.getApplicationContext().getBean("txManager",PlatformTransactionManager.class);
	}
}
