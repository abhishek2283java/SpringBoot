package org.abhishek.om;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.abhishek.om.owner.Owner;
import org.abhishek.om.owner.OwnerController;
import org.abhishek.om.owner.OwnerRepository;
import org.abhishek.om.owner.OwnerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class OwnerRestApiMySqlApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	OwnerService ownerService;
	
	@MockBean
	OwnerRepository ownerRepository;
	
//	@Autowired
//	private OwnerController controller;

//	@Test
//	void contextLoads() {
//		assertThat(controller).isNotNull();
//	}
	
	@Test
	public void should_ReturnCountOfOwners_When_getAllOwnersIsCalled() throws Exception {
		// when
		
		List<Owner> owners = new ArrayList<Owner>();
		owners.add(buildOwner());
		owners.add(buildOwner());
		
		when(ownerService.getAllOwners()).thenReturn(owners);
		
		this.mockMvc.perform(get("/owner-service/owners"))
			.andDo(print())
			.andExpect(status().isOk())	//checking the status in the response
			.andExpect(jsonPath("$.length()", is(2)))	//hamcrest is needed. length of the json array response. In this case 2 owner instances are being returned
			.andExpect(jsonPath("$.[0].id").value(1))
			.andExpect(jsonPath("$.[0].ownername").value("Test Owner 1"));	//this must be the actual string in the json. Not ownerName
			//.andExpect(content().string("Greetings from The Happy Hotel. We've got enough beds for 10 guests!"));
	}
	
	@Test
	public void should_addANewOwner_When_InputOK() throws Exception {
		Owner owner = buildOwner();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(owner);
		
		when(ownerService.addOwner(any())).thenReturn(owner);	//jsonPath and response did not show up with owner passed to addOwner
		
		this.mockMvc.perform(post("/owner-service/owners").contentType(MediaType.APPLICATION_JSON)
				.content(jsonString))	//sending the input payload
				.andDo(print())			//andDo is for printing the result
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(owner.getId()));
				//.andExpect(jsonPath("$.id").value(1));
		
		verify(ownerService, times(1)).addOwner(any());
		verify(ownerRepository, never()).save(any());
	}
	
	@Test
	public void should_UpdateOwner_When_InputOK() throws Exception{
		
		Owner existingOwner = buildOwner();
		Owner updatedOwner = updatedOwner();
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(updatedOwner);
		
		when(ownerService.updateOwner(any())).thenReturn(updatedOwner);
		
		this.mockMvc
			.perform(put("/owner-service/owners/{ownerId}", existingOwner.getId())
					.contentType(MediaType.APPLICATION_JSON)
					.content(jsonString))	//the request payload
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.ownername").value(updatedOwner.getOwnername()));
			//.andExpect(jsonPath("$.message").value("Hello World John Doe!!!"));	
	}
	
	private Owner buildOwner() {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setOwnername("Test Owner 1");
		return owner;
	}
	
	//for UpdateOwner controller method. this is updated owner
	private Owner updatedOwner() {
		Owner owner = new Owner();
		owner.setOwnername("Test Owner 1 Updated");
		return owner;
	}

}
