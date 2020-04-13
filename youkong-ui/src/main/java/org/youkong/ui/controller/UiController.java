package org.youkong.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.youkong.ui.service.uiFeignService;

//@RestController
//@RefreshScope
public class UiController {
	 
	/*@Autowired
	private uiFeignService UiFeignService;
	 
	@Value("${server}")
	String port;
	
	@GetMapping("/hello/{name}")
	public String hello(@PathVariable("name") String name) {	        
	   return "port:"+port+UiFeignService.hello(name);
	}*/
}
