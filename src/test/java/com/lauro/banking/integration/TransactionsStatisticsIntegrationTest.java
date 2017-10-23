package com.lauro.banking.integration;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.lauro.banking.statistics.RecentStatistics;
import com.lauro.banking.transactions.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsStatisticsIntegrationTest {

	@LocalServerPort
	private int port;

	private URI transactionsURL;

	private URI statisticsURL;

	private final Transaction sampleTransaction1 = new Transaction(Instant.now().toEpochMilli(), 9L);
	private final Transaction sampleTransaction2 = new Transaction(Instant.now().toEpochMilli(), 3L);
	private final RecentStatistics expectedStatistics = new RecentStatistics(12D, 6D, 9D, 3D, 2L);

	@Autowired
	private TestRestTemplate restTemplate;

	@Before
	public void setUp() throws Exception {
		this.transactionsURL = new URI("http://localhost:" + port + "/transactions");
		this.statisticsURL = new URI("http://localhost:" + port + "/statistics");

	}

	@Test
	public void creatingTransactionsAndFetchingStatistics() throws Exception {
		restTemplate.postForLocation(transactionsURL, sampleTransaction1);
		restTemplate.postForLocation(transactionsURL, sampleTransaction2);

		TimeUnit.SECONDS.sleep(1);

		final RecentStatistics statistic = restTemplate.getForEntity(statisticsURL, RecentStatistics.class).getBody();

		assertEquals(expectedStatistics.getAvg(), statistic.getAvg());
		assertEquals(expectedStatistics.getMin(), statistic.getMin());
		assertEquals(expectedStatistics.getMax(), statistic.getMax());
		assertEquals(expectedStatistics.getSum(), statistic.getSum());
		assertEquals(expectedStatistics.getCount(), statistic.getCount());
	}
}
