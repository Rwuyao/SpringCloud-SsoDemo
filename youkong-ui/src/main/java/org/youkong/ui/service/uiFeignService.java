package org.youkong.ui.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-manage",fallbackFactory = uiFeignHystrix.class)
public interface uiFeignService {
	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name") String name);
}
