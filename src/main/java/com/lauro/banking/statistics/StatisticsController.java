package com.lauro.banking.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

	@Autowired
	private RecentStatistics recentStatistics;

	@GetMapping(path = "/statistics", produces = "application/json")
	public ResponseEntity<RecentStatistics> recentTransactions() {
		return ResponseEntity.ok(recentStatistics);
	}
}
