package com.lauro.banking.statistics;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private RecentStatistics recentStatistic;

	@Test
	public void testFetchingTheRecentStatitics() throws Exception {
		final Double max = 10D;
		final Double min = 3D;
		final Double avg = 12D;
		final Double sum = 50D;
		final Long count = 12L;

		recentStatistic.setMax(max);
		recentStatistic.setMin(min);
		recentStatistic.setAvg(avg);
		recentStatistic.setSum(sum);
		recentStatistic.setCount(count);

		final String expectedJSONContent = "{\"max\":" + max + ",\"min\":" + min + ",\"avg\":" + avg + ",\"sum\":" + sum
				+ ",\"count\":" + count + "}";

		mvc.perform(MockMvcRequestBuilders.get("/statistics").accept(MediaType.APPLICATION_JSON))
				.andExpect(content().json(expectedJSONContent));
	}
}
