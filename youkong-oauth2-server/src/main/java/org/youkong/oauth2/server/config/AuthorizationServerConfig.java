package org.youkong.oauth2.server.config;

import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.youkong.oauth2.server.service.UserServiceDetail;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	
	//RSA配置
    @Value("${config.oauth2.privateKey}")
    private String privateKey ;
    @Value("${config.oauth2.publicKey}")
    private String publicKey;
	
	
	 @Autowired
     @Qualifier("dataSource")
     DataSource dataSource;
	
	 @Autowired
	 AuthenticationManager authenticationManager;
	 
	 	
	 @Bean
	 public TokenStore tokenStore() {
	        return new JwtTokenStore(jwtAccessTokenConverter());
	 }
		    
	 @Bean
	 public JwtAccessTokenConverter jwtAccessTokenConverter() {
		 JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	     // 导入证书
	     //KeyStoreKeyFactory keyStoreKeyFactory =
	     // new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "zqdjjhave0c".toCharArray());
	     //converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
	     //converter.setSigningKey(key);
	    
		 converter.setSigningKey(privateKey);
	     converter.setVerifierKey(publicKey);
		 return converter;
	  }	 
	 	 
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 使用JdbcClientDetailsService客户端详情服务
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }
	
	@Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter())
        .authenticationManager(authenticationManager);		
    }

	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
        		.allowFormAuthenticationForClients();
    }
	
	
    
    
}
