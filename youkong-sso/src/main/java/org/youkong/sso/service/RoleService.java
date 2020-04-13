package org.youkong.sso.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youkong.sso.entity.Role;
import org.youkong.sso.repository.RoleRepository;

@Service
@Transactional
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;
	
	public void save(Role role) {
		roleRepository.save(role);
	}
	
	public void delete(Long id) {
		roleRepository.deleteById(id);
	}
	
	public List<Role> findAll(){
		return roleRepository.findAll();
	}
	public Optional<Role> findOne(Long id) {
		Optional<Role> role=null;
		role =roleRepository.findById(id);
		return role;
	}
	
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
}
