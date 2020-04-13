package org.youkong.oauth2.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.youkong.oauth2.redis.entity.BaseUser;
import org.youkong.oauth2.redis.entity.SecurityUser;
import org.youkong.oauth2.redis.repository.UserRepository;

@Component
public class UserServiceDetail implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BaseUser baseuser=userRepository.findByName(username);
		if(null==baseuser){
				throw new UsernameNotFoundException("UserName "+username+" not found");
		}	
		
		return new SecurityUser(baseuser);
	}

}
