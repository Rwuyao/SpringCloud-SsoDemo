package org.youkong.ui.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import feign.hystrix.FallbackFactory;

@Component
public class uiFeignHystrix implements FallbackFactory<uiFeignService> {

	 @Override
	    public uiFeignService create(Throwable throwable) {
	        String msg = throwable == null ? "" : throwable.getMessage();
	        if (!StringUtils.isEmpty(msg)) {
	            System.out.print(msg);
	        }
	        return new uiFeignService() {
	            @Override
	            public String hello(String name){
	            	return "hello"+name+",this message send failed";
	            }
	        };
	    }

}
