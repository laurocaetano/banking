package com.lauro.banking.transactions;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class TransactionRepository {
	private ConcurrentNavigableMap<Long, List<Transaction>> transactionsPerTimestamp = new ConcurrentSkipListMap<>();

	public void add(Transaction transaction) {
		final List<Transaction> transactions = transactionsPerTimestamp.getOrDefault(transaction.getTimestamp(),
				new ArrayList<Transaction>());

		transactions.add(transaction);
		transactionsPerTimestamp.put(transaction.getTimestamp(), transactions);
	}

	public List<Transaction> getAll() {
		return fetchTransactions(transactionsPerTimestamp);
	}

	public List<Transaction> getNewerThan(Long seconds) {
		return fetchTransactions(transactionsPerTimestamp.tailMap(secondsAgoToTimestamp(seconds)));

	}

	public void cleanTransactionsOlderThan(Long seconds) {
		final ConcurrentNavigableMap<Long, List<Transaction>> recentTransactions = transactionsPerTimestamp
				.tailMap(secondsAgoToTimestamp(seconds));
		transactionsPerTimestamp = recentTransactions;
	}

	private List<Transaction> fetchTransactions(ConcurrentNavigableMap<Long, List<Transaction>> transactions) {
		return transactions.values().stream().flatMap(t -> t.stream()).collect(Collectors.toList());
	}

	private Long secondsAgoToTimestamp(Long seconds) {
		return Instant.now().minusSeconds(seconds).toEpochMilli();
	}
}
