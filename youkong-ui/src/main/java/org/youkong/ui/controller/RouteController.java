package org.youkong.ui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
	@RequestMapping("/test")
	public String index() {	
	return "index";
	
}
	
}
