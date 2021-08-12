package org.abhishek.om;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URISyntaxException;
import java.util.Arrays;

import org.abhishek.om.owner.Owner;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class tests_2_IT {
	
	TestRestTemplate testRestTemplate = new TestRestTemplate();
	ObjectMapper mapper;
	
	@Test
	public void getOwnerUsingExchange() {
		Owner response = null;
		final String baseUrl = "http://localhost:8080/owner-service/owners/1";
		HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 
	    HttpEntity<String> entity = new HttpEntity<String>(headers);
	     
	    ResponseEntity<String> responseEntity = testRestTemplate.exchange(baseUrl, HttpMethod.GET, entity, String.class);
	    mapper = new ObjectMapper();
		try {
			response = mapper.readValue(responseEntity.getBody(), Owner.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(responseEntity.getBody());
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertThat(responseEntity.getBody().contains("Abhikriti C"));
//		//responseEntity.getBody().contains("Abhikriti C")
//		assertEquals(1, response.getId());
		
		assertAll(
				() -> assertEquals(HttpStatus.OK, responseEntity.getStatusCode()),
				() -> assertThat(responseEntity.getBody().contains("Abhikriti C"))
				//() -> assertEquals(1, response.getId())
				);
	}
	
	@Test
	@Disabled
	public void getOwner() throws URISyntaxException {
		
		final String baseUrl = "http://localhost:8080/owner-service/owners/1";
		
		Owner response = null;
		
		TestRestTemplate testRestTemplate = new TestRestTemplate();
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(baseUrl, String.class);
		
		
		mapper = new ObjectMapper();
		try {
			response = mapper.readValue(responseEntity.getBody(), Owner.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		//assertEquals(1, response.getId());
		
	}
}
