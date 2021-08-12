package org.abhishek.om;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class testsIT {
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@LocalServerPort
    int randomServerPort;
	
	@Test
	@Disabled
	public void getOwner() throws URISyntaxException {
		final String baseUrl = "http://localhost:"+randomServerPort+"/owner-service/owners/1";
		URI uri = new URI(baseUrl);
		
		//ResponseEntity<String> response = testRestTemplate.getForEntity("http://localhost:8080/owner-service/owners/1", String.class);
		ResponseEntity<String> response = testRestTemplate.getForEntity(uri, String.class);
		System.out.println(response.getStatusCode());
	}
}
