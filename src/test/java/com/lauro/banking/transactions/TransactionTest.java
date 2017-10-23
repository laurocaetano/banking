package com.lauro.banking.transactions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

public class TransactionTest {

	private Long secondsAgoToTimestamp(Long seconds) {
		return Instant.now().minusSeconds(seconds).toEpochMilli();
	}

	@Test
	public void testTransactionIsNewerThanSixtySeconds() {
		final Transaction transaction = new Transaction(secondsAgoToTimestamp(10L), 12L);
		assertTrue(transaction.isNewerThanSixtySeconds());
	}

	@Test
	public void testTransactionIsOlderThanSixtySeconds() {
		final Transaction transaction = new Transaction(secondsAgoToTimestamp(80L), 12L);
		assertFalse(transaction.isNewerThanSixtySeconds());
	}
}