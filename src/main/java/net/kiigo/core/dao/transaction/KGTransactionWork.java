package net.kiigo.core.dao.transaction;

import org.springframework.transaction.TransactionStatus;

public interface KGTransactionWork<T>   {
	@SuppressWarnings("hiding")
	public <T> T doWork(TransactionStatus status) throws Exception;
}
