package org.youkong.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.youkong.sso.entity.SecurityUser;
import org.youkong.sso.entity.User;
import org.youkong.sso.repository.UserRepository;


@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=userRepository.findByName(username);
		if(null==user){
				throw new UsernameNotFoundException("UserName "+username+" not found");
		}	
		
		return new SecurityUser(user);
	}

}
