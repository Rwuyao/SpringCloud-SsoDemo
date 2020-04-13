package org.youkong.sso.config.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.youkong.sso.service.CustomUserDetailsService;
import org.youkong.sso.utils.AccessAddressUtil;
import org.youkong.sso.utils.CollectionUtil;
import org.youkong.sso.utils.DateUtil;
import org.youkong.sso.utils.JwtTokenUtil;
import org.youkong.sso.utils.RedisUtil;
import org.youkong.sso.utils.StringUtil;
import com.alibaba.fastjson.JSON;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    CustomUserDetailsService UserDetailsService;

    @Autowired
    RedisUtil redisUtil;
 
    @Value("${token.expirationSeconds}")
    private int expirationSeconds;
 
    @Value("${token.validTime}")
    private int validTime;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");      
        //处理token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {       	 
        	if(!processjwt( authHeader, request,  response)) {
        		 return;
        	}
        }
        filterChain.doFilter(request, response);
    }

    private boolean processjwt(String authHeader,HttpServletRequest request, HttpServletResponse response ) throws IOException {
    	
    	 String authToken = authHeader.substring("Bearer ".length());
    	 //获取请求的ip地址
         String currentIp = AccessAddressUtil.getIpAddress(request);
    	 
         String username = JwtTokenUtil.parseToken(authToken, "_secret");
         String ip = CollectionUtil.getMapValue(JwtTokenUtil.getClaims(authToken), "ip");

         //进入黑名单验证
         if (redisUtil.isBlackList(authToken)) {
             //log.info("用户：{}的token：{}在黑名单之中，拒绝访问",username,authToken);
             response.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.TOKEN_IS_BLACKLIST, false)));
             return false;
         }

         //判断token是否过期
         /* 过期的话，从redis中读取有效时间（比如七天登录有效），再refreshToken（根据以后业务加入，现在直接refresh）
          * 	同时，已过期的token加入黑名单                                          	 
          */
         if (redisUtil.hasKey(authToken)) {//判断redis是否有保存
             String expirationTime = redisUtil.hget(authToken,"expirationTime").toString();
             if (JwtTokenUtil.isExpiration(expirationTime)) {
                 //获得redis中用户的token刷新时效
                 String tokenValidTime = (String) redisUtil.getTokenValidTimeByToken(authToken);
                 String currentTime = DateUtil.getTime();
                 //这个token已作废，加入黑名单
                 //log.info("{}已作废，加入黑名单",authToken);
                 redisUtil.hset("blacklist", authToken, DateUtil.getTime());

                 if (DateUtil.compareDate(currentTime, tokenValidTime)) {
                     //超过有效期，不予刷新
                     //log.info("{}已超过有效期，不予刷新",authToken);
                     response.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.LOGIN_IS_OVERDUE, false)));
                     return false;
                 } else {//仍在刷新时间内，则刷新token，放入请求头中
                      username = (String) redisUtil.getUsernameByToken(authToken);                        

                     ip = (String) redisUtil.getIPByToken(authToken);//更新ip

                     //获取请求的ip地址
                     Map<String, Object> map = new HashMap<>();
                     map.put("ip", ip);
                     String jwtToken = JwtTokenUtil.generateToken(username, expirationSeconds, map);

                     //新的token保存到redis中
                     redisUtil.setTokenRefresh(jwtToken,username,ip);
                     //删除旧的token保存的redis
                     redisUtil.deleteKey(authToken);


                     //log.info("redis已删除旧token：{},新token：{}已更新redis",authToken,jwtToken);
                     authToken = jwtToken;//更新token，为了后面
                     response.setHeader("Authorization", "Bearer " + jwtToken);
                 }
             }

         }

         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
             if (!StringUtil.equals(ip, currentIp)) {//地址不正确
                 //log.info("用户：{}的ip地址变动，进入黑名单校验",username);
                 //进入黑名单验证
                 if (redisUtil.isBlackList(authToken)) {
                     //log.info("用户：{}的token：{}在黑名单之中，拒绝访问",username,authToken);
                     response.getWriter().write(JSON.toJSONString(ResultVO.result(ResultEnum.TOKEN_IS_BLACKLIST, false)));
                     return false;
                 }
                 //黑名单没有则继续，如果黑名单存在就退出后面
             }


             UserDetails userDetails = UserDetailsService.loadUserByUsername(username);
             if (userDetails != null) {
                 UsernamePasswordAuthenticationToken authentication =
                         new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                 SecurityContextHolder.getContext().setAuthentication(authentication);
                 return true;
             }
         }
         return false;
    }
    
}

