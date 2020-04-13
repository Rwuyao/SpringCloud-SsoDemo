package org.youkong.sso.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.youkong.sso.entity.User;
import org.youkong.sso.repository.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void save(User user) {
		userRepository.save(user);
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	public List<User> findAll(){
		return userRepository.findAll();
	}
	public Optional<User> findOne(Long id) {
		Optional<User> user=null;
		user =userRepository.findById(id);
		return user;
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
}
