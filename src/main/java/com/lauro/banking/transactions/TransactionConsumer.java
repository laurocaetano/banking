package com.lauro.banking.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TransactionConsumer {

	@Autowired
	TransactionsLog transactions;

	@Autowired
	TransactionRepository transactionRepository;

	@Scheduled(fixedRate = 500)
	public void consumeTransactionFromTheLog() {
		for (int i = 0; i < 1000; i++) {
			final Transaction transaction = transactions.consume();

			if (transaction != null) {
				transactionRepository.add(transaction);
			}
		}
	}
}
