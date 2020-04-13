package org.youkong.sso.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youkong.sso.entity.Resource;
import org.youkong.sso.repository.ResourceRepository;

@Service
@Transactional
public class ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	
	public void save(Resource Resource) {
		resourceRepository.save(Resource);
	}
	
	public void delete(Long id) {
		resourceRepository.deleteById(id);
	}
	
	public List<Resource> findAll(){
		return resourceRepository.findAll();
	}
	public Optional<Resource> findOne(Long id) {
		Optional<Resource> Resource=null;
		Resource =resourceRepository.findById(id);
		return Resource;
	}
	
	public Resource findByName(String name) {
		return resourceRepository.findByName(name);
	}
}
