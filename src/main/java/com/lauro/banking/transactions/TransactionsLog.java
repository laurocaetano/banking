package com.lauro.banking.transactions;

import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.stereotype.Component;

@Component
public class TransactionsLog {

	private final AbstractQueue<Transaction> transactionQueue = new ConcurrentLinkedQueue<>();

	public boolean insert(Transaction transaction) {
		return transactionQueue.add(transaction);
	}

	public Transaction consume() {
		return transactionQueue.poll();
	}
}
