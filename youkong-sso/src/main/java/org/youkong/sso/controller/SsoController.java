package org.youkong.sso.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SsoController {
		
	@Value("${server.port}")
	String port;
	
	@RequestMapping("/hello")
	@ResponseBody
	    public String index(@RequestParam String name) {  
			return "hello "+name+",the port is "+port;
	    }
	
	@RequestMapping("/login")
    public String login() {  
		return "login";
    }
	
}
