package com.intrum.task.api.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.intrum.task.api.dto.PayoutDTO;

@Service
public class SubmitDataService {
	
	private static final Logger logger = LoggerFactory.getLogger(SubmitDataService.class);

	@Value("${payout.endpoint}")
	private String payoutEndpoint;
	
	@Autowired
	private RestTemplate restTemplate;
	

	public void submitPayout (final PayoutDTO payoutDTO) {
		
		try {
			URI uri = new URI(payoutEndpoint);
	
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Type", "application/json");
	
			HttpEntity<PayoutDTO> httpEntity = new HttpEntity<>(payoutDTO, headers);
	
			ResponseEntity<String> result = restTemplate.postForEntity(uri, httpEntity, String.class);
			
			if (result.getStatusCode().equals(HttpStatus.OK)) {
				logger.info(result.getBody());
			} else {
				logger.error("Error in send Data");
			}
			
		} catch (URISyntaxException e) {
			logger.debug(e.getMessage());
		}
	}
}
