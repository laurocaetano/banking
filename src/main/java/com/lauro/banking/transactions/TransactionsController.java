package com.lauro.banking.transactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionsController {

	@Autowired
	private TransactionsLog transactionsLog;

	@PostMapping(path = "/transactions", consumes = "application/json")
	public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
		if (transaction.isNewerThanSixtySeconds()) {
			transactionsLog.insert(transaction);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} else {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
