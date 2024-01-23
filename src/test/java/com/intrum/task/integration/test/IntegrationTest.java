package com.intrum.task.integration.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.task.TaskApplication;
import com.intrum.task.data.entities.PayoutEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(
  webEnvironment = SpringBootTest.WebEnvironment.MOCK,
  classes = TaskApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
  locations = "classpath:application-test.yaml")
public class IntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void check_response_code_200() throws Exception {
		

		mockMvc.perform(MockMvcRequestBuilders.post("/payout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(getTestEntityOk())))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void check_validation_fields() throws Exception {
		

		mockMvc.perform(MockMvcRequestBuilders.post("/payout")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(getTestEntityKo())))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError())
				.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	private PayoutEntity getTestEntityOk () {
    	return new PayoutEntity("test-tax-number-3562662", "text-company-name", "TESTING", new Date(), 1000D);
    }
	
	private PayoutEntity getTestEntityKo () {
    	return new PayoutEntity("test-tax-number-3562662", "text-company-name", "TESTING", null, null);
    }
}
