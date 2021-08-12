package org.abhishek.om.owner;

import java.util.ArrayList;
import java.util.List;

import org.abhishek.om.owner.exception.OwnerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

	@Autowired
	private OwnerRepository ownerRepository;

	public Owner getOwner(int id) throws OwnerNotFoundException {
		return ownerRepository
				.findById(id)
				.orElseThrow(() -> new OwnerNotFoundException("Owner with Id " + id + " not found"));
	}

	public Owner addOwner(Owner owner) {
		return ownerRepository.save(owner);
	}

	public List<Owner> getAllOwners() throws OwnerNotFoundException {
		System.out.println(">>>>>>>>>>>>>>>>>>Inside getAllOwners");
		List<Owner> owners = new ArrayList<>();
		ownerRepository
			.findAll()
			.forEach(owners::add);
		if(owners.isEmpty()) {
			System.out.println(">>>>>>>>>>>>>>>>>>No Owners found");
			throw new OwnerNotFoundException("No Owners found");
		}
		return owners;
	}
	
	public Owner updateOwner(Owner owner) throws OwnerNotFoundException {
		System.out.println(">>>>>>>>>>>>>>>>>>Inside updateOwner");
		getOwner(owner.getId());
		return ownerRepository.save(owner);
	}
	
	public void deleteOwner(int ownerId) throws OwnerNotFoundException {
		getOwner(ownerId);
		ownerRepository.deleteById(ownerId);
	}
}
