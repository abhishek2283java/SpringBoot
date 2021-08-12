package org.abhishek.om.owner;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/owner-service")
@Validated
public class OwnerController {
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OwnerService ownerService;
	
//	@RequestMapping("/owners")
//	public List<Owner> getAllOwners() {
//		return ownerService.getAllOwners();
//	}
	
	@RequestMapping("/owners")
	public ResponseEntity<List<Owner>> getAllOwnersV2() {
		return ResponseEntity.status(HttpStatus.OK).body(ownerService.getAllOwners());
	}
	
	@RequestMapping("/owners/{ownerId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Owner> fetchOwner(@PathVariable("ownerId") int id) {
		return ResponseEntity.status(HttpStatus.OK).body(ownerService.getOwner(id));
	}
	
	//@RequestMapping(method = RequestMethod.POST, value="/owners")
	//public ResponseEntity<Owner> addOwner(@Valid @RequestBody Owner owner) {
//	public ResponseEntity<Owner> addOwner(@RequestBody Owner owner) {
//		Owner ownerResp = ownerService.addOwner(owner);
//		return ResponseEntity.status(HttpStatus.CREATED).body(ownerResp);
//	}
	
	@RequestMapping(method = RequestMethod.POST, value="/owners")
	public ResponseEntity<Owner> addOwner(@Valid @RequestBody Owner owner) {
	//public ResponseEntity<Owner> addOwnerV2(@RequestBody Owner owner) {
		Owner ownerResp = ownerService.addOwner(owner);
		
		URI location = 
			    ServletUriComponentsBuilder.fromCurrentServletMapping().path("/owners/{id}").build()
			      .expand(ownerResp.getId()).toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(location);
		
		return ResponseEntity.status(HttpStatus.CREATED)
			      .headers(headers)
			      .body(ownerResp);
		
		//return ResponseEntity.status(HttpStatus.CREATED).body(ownerResp);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value="/owners/{ownerId}")
	public ResponseEntity<Owner> updateOwner(@RequestBody Owner owner, @PathVariable("ownerId") int id) {
		owner.setId(id);
		Owner updateOwner = ownerService.updateOwner(owner);
		return ResponseEntity.status(HttpStatus.OK).body(updateOwner);
		//ResponseEntity.created(null)
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value="/owners/{ownerId}")
	public ResponseEntity<Void> deleteOwner(@PathVariable("ownerId") int id) {
		ownerService.deleteOwner(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
