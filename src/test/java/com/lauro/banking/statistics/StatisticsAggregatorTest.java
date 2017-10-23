package com.lauro.banking.statistics;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lauro.banking.transactions.Transaction;
import com.lauro.banking.transactions.TransactionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsAggregatorTest {

	@Autowired
	private StatisticsAggregator aggregator;

	@Autowired
	private RecentStatistics recentStatistic;

	@Autowired
	private TransactionRepository transactionRepository;

	private final Transaction sampleTransaction1 = new Transaction(Instant.now().toEpochMilli(), 22L);
	private final Transaction sampleTransaction2 = new Transaction(Instant.now().minusSeconds(10L).toEpochMilli(), 99L);
	private final Transaction sampleTransaction3 = new Transaction(Instant.now().minusSeconds(55L).toEpochMilli(), 03L);

	@Test
	public void testAggregateLatestTransactions() {
		transactionRepository.add(sampleTransaction1);
		transactionRepository.add(sampleTransaction2);
		transactionRepository.add(sampleTransaction3);

		aggregator.aggregateTransactions();

		assert (recentStatistic.getMax() == 99L);
	}
}
