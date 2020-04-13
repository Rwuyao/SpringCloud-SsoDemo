package org.user.manage.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.user.manage.entity.BaseUser;
import org.user.manage.entity.UserLoginDTO;
import org.user.manage.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    UserService userService;

    @PostMapping("/register")
    public BaseUser postUser(@RequestParam("username") String username , @RequestParam("password") String password){
        //参数判断，省略
       return userService.insertUser(username,password);
    }
    
    @RequestMapping("/test")
	public String test(HttpServletRequest request) {
		System.out.println("----------------header----------------");
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			System.out.println(key + ": " + request.getHeader(key));
		}
		System.out.println("----------------header----------------");
		return "hellooooooooooooooo!";
	}

    
    /*@PostMapping("/login")
    public UserLoginDTO login(@RequestParam("username") String username , @RequestParam("password") String password){
        //参数判断，省略
        return userService.login(username,password);
    }*/
}
