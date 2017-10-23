package com.lauro.banking.transactions;

import static org.junit.Assert.assertEquals;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionConsumerTest {

	@Autowired
	private TransactionsLog transactionsLog;

	@Autowired
	private TransactionRepository repository;

	@Autowired
	private TransactionConsumer consumer;

	private final Transaction sampleTransaction = new Transaction(Instant.now().toEpochMilli(), 10L);

	@Test
	public void testConsumingFromTheTransactionLog() {
		transactionsLog.insert(sampleTransaction);

		consumer.consumeTransactionFromTheLog();

		final Transaction consumedTransaction = repository.getAll().stream().filter(t -> t.equals(sampleTransaction))
				.findFirst().get();

		assertEquals(sampleTransaction, consumedTransaction);
	}
}
