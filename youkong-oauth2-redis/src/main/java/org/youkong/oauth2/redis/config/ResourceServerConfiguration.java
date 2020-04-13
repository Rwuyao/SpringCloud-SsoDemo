package org.youkong.oauth2.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends  ResourceServerConfigurerAdapter {
	
    
	    @Override
	    public void configure(HttpSecurity http) throws Exception {
	        http	        		
	                .csrf().disable()	                	               	                
	                .requestMatchers()
	                .antMatchers("/users/**")
	                .and()
	                .authorizeRequests()
	                .antMatchers("/users/**")
	                .permitAll();
	               

	    }
	    
	  
}
