package com.lauro.banking.statistics;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lauro.banking.transactions.Transaction;
import com.lauro.banking.transactions.TransactionRepository;

@Component
public class StatisticsAggregator {

	private final Long transactionTimeframeInSeconds = 60L;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RecentStatistics recentStatistic;

	@Scheduled(fixedRate = 500)
	public void aggregateTransactions() {
		final List<Transaction> recentTransactions = transactionRepository.getNewerThan(transactionTimeframeInSeconds);

		final DoubleSummaryStatistics stats = recentTransactions.parallelStream()
				.collect(Collectors.summarizingDouble(Transaction::getAmount));

		recentStatistic.setAvg(stats.getAverage());
		recentStatistic.setSum(stats.getSum());
		recentStatistic.setMax(stats.getMax());
		recentStatistic.setMin(stats.getMin());
		recentStatistic.setCount(stats.getCount());
	}
}
