package org.youkong.sso.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.youkong.sso.config.security.AjaxAccessDeniedHandler;
import org.youkong.sso.config.security.AjaxAuthenticationEntryPoint;
import org.youkong.sso.config.security.AjaxAuthenticationFailureHandler;
import org.youkong.sso.config.security.AjaxAuthenticationSuccessHandler;
import org.youkong.sso.config.security.AjaxLogoutSuccessHandler;
import org.youkong.sso.config.security.JwtAuthenticationTokenFilter;
import org.youkong.sso.service.CustomUserDetailsService;



@Configuration
@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter{

	 @Autowired
	 AjaxAuthenticationEntryPoint authenticationEntryPoint;  //  未登陆时返回 JSON 格式的数据给前端（否则为 html）

	 @Autowired
	 AjaxAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

	 @Autowired
	 AjaxAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html）

	 @Autowired
	 AjaxLogoutSuccessHandler  logoutSuccessHandler;  // 注销成功返回的 JSON 格式数据给前端（否则为 登录时的 html）

	 @Autowired
	 AjaxAccessDeniedHandler accessDeniedHandler;    // 无权访问返回的 JSON 格式数据给前端（否则为 403 html 页面）

	 @Autowired
	 JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter; // JWT 拦截器
	 
	@Autowired 
	private CustomUserDetailsService customUserDetailsService;

	 @Autowired
     @Qualifier("dataSource")
     DataSource dataSource;	
	
	 @Bean
	 public PersistentTokenRepository persistentTokenRepository(){
	     JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
	     tokenRepository.setDataSource(dataSource);
	     // 如果token表不存在，使用下面语句可以初始化该表；若存在，会报错。
	     // tokenRepository.setCreateTableOnStartup(true);
	     return tokenRepository;
	 }

	 	
	@Bean
	public PasswordEncoder passwordEncoder() {
	   return new BCryptPasswordEncoder();
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }
   
 
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 使用 JWT，关闭token
        .and()
        .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
         //.access("@rbacauthorityservice.hasPermission(request,authentication)") // RBAC 动态 url 认证
        .and()
        .formLogin()  //开启登录
        .successHandler(authenticationSuccessHandler) // 登录成功
        .failureHandler(authenticationFailureHandler) // 登录失败
        .permitAll()
        .and()
        .logout()
        .logoutSuccessHandler(logoutSuccessHandler)
        .permitAll();		
		// 记住我
        http.rememberMe().rememberMeParameter("remember-me")
        .tokenRepository(persistentTokenRepository()).tokenValiditySeconds(300);
		
		 http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
	     http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // JWT Filter

    }
	
}
