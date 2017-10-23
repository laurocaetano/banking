package com.lauro.banking.transactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OldTransactionsCleaner {

	private static final Logger log = LoggerFactory.getLogger(OldTransactionsCleaner.class);

	@Autowired
	TransactionRepository transactionRepository;

	private final Long olderTransactionsInSecond = 61L;

	@Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
		log.info("Cleaning transactiosn older than " + olderTransactionsInSecond + " seconds.");
		transactionRepository.cleanTransactionsOlderThan(olderTransactionsInSecond);
	}
}
