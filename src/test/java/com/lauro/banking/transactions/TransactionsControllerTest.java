package com.lauro.banking.transactions;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

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
public class TransactionsControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testCreatingTransactionsNewerThanSixtySeconds() throws Exception {
		final Long timestamp = Instant.now().toEpochMilli();
		final String requestContent = "{\"amount\":12,\"timestamp\":" + timestamp + "}";

		mvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(requestContent)).andExpect(status().isCreated())
				.andReturn();
	}

	@Test
	public void testCreatingTransactionsOlderThanSixtySeconds() throws Exception {
		final Long timestamp = Instant.now().minusSeconds(70L).toEpochMilli();
		final String requestContent = "{\"amount\":12,\"timestamp\":" + timestamp + "}";

		mvc.perform(MockMvcRequestBuilders.post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(requestContent)).andExpect(status().isNoContent())
				.andReturn();
	}
}
