package org.youkong.sso.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.youkong.sso.entity.AjaxResponseBody;
import org.youkong.sso.utils.DateUtil;
import org.youkong.sso.utils.RedisUtil;

import com.alibaba.fastjson.JSON;

@Component
public class AjaxLogoutSuccessHandler implements LogoutSuccessHandler {

	 	@Autowired
	    private RedisUtil redisUtil;
	 
	    @Override
	    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
	        String authHeader = httpServletRequest.getHeader("Authorization");
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            final String authToken = authHeader.substring("Bearer ".length());
	            //将token放入黑名单中
	            redisUtil.hset("blacklist", authToken, DateUtil.getTime());
	           // log.info("token：{}已加入redis黑名单",authToken);
	        }
	        httpServletResponse.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.USER_LOGOUT_SUCCESS,true)));
	    }
}
