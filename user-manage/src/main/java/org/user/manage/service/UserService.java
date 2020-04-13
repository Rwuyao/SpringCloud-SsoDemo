package org.user.manage.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.user.manage.entity.BaseUser;
import org.user.manage.entity.JWT;
import org.user.manage.entity.UserLoginDTO;
import org.user.manage.exception.UserLoginException;
import org.user.manage.feign.AuthService;
import org.user.manage.repository.UserRepository;
import org.user.manage.utils.BPwdEncoderUtil;
@Service
public class UserService {
	 
	@Autowired
	private UserRepository userRepository;

	 @Autowired
	 private AuthService client;
	
	public BaseUser insertUser(String username,String  password){
		BaseUser user=new BaseUser( username,  BPwdEncoderUtil.BCryptPassword(password),  "",  null,  new Date(),  null);       
        return userRepository.save(user);
    } 
	
	public UserLoginDTO login(String username, String password){
    	BaseUser user=userRepository.findByName(username);
        if (null == user) {
            throw new UserLoginException("error username");
        }
        if(!BPwdEncoderUtil.matches(password,user.getPassword())){
            throw new UserLoginException("error password");
        }
        // 获取token
        JWT jwt=client.getToken("Basic dXNlci1zZXJ2aWNlOjEyMzQ1Ng==","password",username,password);
        // 获得用户菜单
        if(jwt==null){
            throw new UserLoginException("error internal");
        }
        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);
        return userLoginDTO;
    }

	    
}
