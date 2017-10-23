package com.lauro.banking.transactions;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.util.List;

import org.junit.Test;

public class TransactionRepositoryTest {

	private final TransactionRepository repository = new TransactionRepository();
	private final Long sixtySecondsAgo = 60L;
	private final Transaction sampleTransaction = new Transaction(Instant.now().toEpochMilli(), 10L);

	@Test
	public void testInsertingNewTransaction() {
		repository.add(sampleTransaction);

		final List<Transaction> allTransactions = repository.getAll();

		assertEquals(allTransactions.get(0), sampleTransaction);
	}

	@Test
	public void testGettingTransactionsNewerThanSixtySeconds() {
		repository.add(sampleTransaction);

		final List<Transaction> transactionsNewerThanSixtySeconds = repository.getNewerThan(sixtySecondsAgo);

		assertEquals(transactionsNewerThanSixtySeconds.get(0), sampleTransaction);
		assertEquals(transactionsNewerThanSixtySeconds.size(), 1);
	}

	@Test
	public void testGettingTransactionsOlderThanSixtySeconds() {
		sampleTransaction.setTimestamp(Instant.now().minusSeconds(80L).toEpochMilli());
		repository.add(sampleTransaction);

		final List<Transaction> transactionsNewerThanSixtySeconds = repository.getNewerThan(sixtySecondsAgo);

		assertEquals(transactionsNewerThanSixtySeconds.size(), 0);
	}

	@Test
	public void testCleanTransactionsOlderThanSixtySeconds() {
		sampleTransaction.setTimestamp(Instant.now().minusSeconds(80L).toEpochMilli());

		repository.add(sampleTransaction);
		repository.cleanTransactionsOlderThan(sixtySecondsAgo);

		final List<Transaction> allTransactions = repository.getAll();

		assertEquals(allTransactions.size(), 0);
	}

	@Test
	public void testDoesNotCleanTransactionsNewerThanSixtySeconds() {
		repository.add(sampleTransaction);
		repository.cleanTransactionsOlderThan(sixtySecondsAgo);

		final List<Transaction> allTransactions = repository.getAll();

		assertEquals(allTransactions.size(), 1);
	}
}
