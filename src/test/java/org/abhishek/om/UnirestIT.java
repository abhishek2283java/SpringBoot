package org.abhishek.om;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.abhishek.om.owner.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

@SpringBootTest
public class UnirestIT {
	
	@Test
	public void Unirest_get() {
		HttpResponse<String> asString = Unirest.get("http://localhost:8080/owner-service/owners/{userId}")
	     .routeParam("userId", "2")
	     .asString();
		
		String body = asString.getBody();
		System.out.println(body);
		assertEquals(1, 1);
	}
	
	@Test
	public void Unirest_getAsObject() {
		HttpResponse<Owner> asObject = Unirest.get("http://localhost:8080/owner-service/owners/{userId}")
	     .routeParam("userId", "2")
	     .asObject(Owner.class);
		
		Owner body = asObject.getBody();
		
		assertEquals("Owner 3", body.getOwnername());
	}

	@Test
	public void Unirest_POST_AsJSON() {
		
		Owner owner = new Owner();
		owner.setOwnername("A KKKKK");
		
		HttpResponse<JsonNode> asJson = Unirest.post("http://localhost:8080/owner-service/owners")
				.header("content-type", "application/json")
				.body(owner)		//This will use Jackson to serialize the object into JSON. http://kong.github.io/unirest-java/#requests
				.asJson();
		
//		JSONObject jsonObject = Unirest.post("http://localhost:8080/owner-service/owners")
//		.header("content-type", "application/json")
//		.body(owner)		//This will use Jackson to serialize the object into JSON. http://kong.github.io/unirest-java/#requests
//		.asJson()
//		.getBody()
//		.getObject()
//		.getJSONObject("Owner");
		
		String prettyString = asJson.getBody().toPrettyString();
		System.out.println(prettyString);
		
		assertTrue(prettyString.contains("A KKKKK"));
	}

}
