package com.lauro.banking.transactions;

import java.time.Instant;

public class Transaction {

	private Long amount;
	private Long timestamp;

	public Transaction() {
	}

	public Transaction(Long timestamp, Long amount) {
		this.timestamp = timestamp;
		this.amount = amount;
	}

	public Long getAmount() {
		return amount;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isNewerThanSixtySeconds() {
		return timestamp > sixtySecondsAgo();
	}

	private Long sixtySecondsAgo() {
		return Instant.now().minusSeconds(60L).toEpochMilli();
	}
}