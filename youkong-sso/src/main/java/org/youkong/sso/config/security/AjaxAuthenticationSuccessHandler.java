package org.youkong.sso.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.youkong.sso.entity.AjaxResponseBody;
import org.youkong.sso.entity.SecurityUser;
import org.youkong.sso.service.CustomUserDetailsService;
import org.youkong.sso.utils.AccessAddressUtil;
import org.youkong.sso.utils.JwtTokenUtil;
import org.youkong.sso.utils.RedisUtil;

import com.alibaba.fastjson.JSON;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Value("${token.expirationSeconds}")
    private int expirationSeconds;
 
    @Autowired
    private RedisUtil redisUtil;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	   //获取请求的ip地址
        String ip = AccessAddressUtil.getIpAddress(request);
        Map<String,Object> map = new HashMap<>();
        map.put("ip",ip);
 
        SecurityUser userDetails = (SecurityUser) authentication.getPrincipal();
        String jwtToken = JwtTokenUtil.generateToken(userDetails.getUsername(), expirationSeconds, map);
 

        redisUtil.setTokenRefresh(jwtToken,userDetails.getUsername(),ip);
        //log.info("用户{}登录成功，信息已保存至redis",userDetails.getUsername());
 
        response.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.USER_LOGIN_SUCCESS,"Bearer "+jwtToken,true)));
    
    }


}
