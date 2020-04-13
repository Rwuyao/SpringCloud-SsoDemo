package org.youkong.sso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.youkong.sso.entity.Resource;
import org.youkong.sso.entity.Role;
import org.youkong.sso.entity.User;
import org.youkong.sso.service.ResourceService;
import org.youkong.sso.service.RoleService;
import org.youkong.sso.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={App.class})
public class AppTest {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Test
    public void testOne(){
		
		 SingletonClass instance1 = Singleton.INSTANCE.getInstance();
	     SingletonClass instance2 = Singleton.INSTANCE.getInstance();
	     System.out.println("instance1 == instance2: " + (instance1 == instance2));
	     
	     SingletonClass instance3 = Singleton.INSTANCE2.getInstance2();
	     SingletonClass instance4 = Singleton.INSTANCE2.getInstance2();
	     System.out.println("instance3 == instance4: " + (instance3 == instance4));

		/*
	    BCryptPasswordEncoder Encoder=new BCryptPasswordEncoder();
		
		Resource Resource= new Resource("managment","",new Date());
		resourceService.save(Resource);
		Role role=new Role("admin",new Date(),null);
		roleService.save(role);
		User user =new User( "xiong",  Encoder.encode("123456"),  "",  null,  new Date(),  null);
		userService.save(user);*/
		BCryptPasswordEncoder Encoder=new BCryptPasswordEncoder();
		System.out.println(Encoder.encode("youkong"));
		//User user=userService.findByName("xiong");
		//user.setPassword(Encoder.encode(user.getPassword()));
		//Role role=roleService.findByName("admin");
		//List<Role> roles=new ArrayList<Role>(){{
		   // add(role);	
		//}};
		//user.setRoles(roles);
		//userService.save(user);	
	}
	
	private enum Singleton {
	    INSTANCE(1),
		INSTANCE2(2);
	    private SingletonClass instance;
	    private SingletonClass instance2;
	    
	    Singleton() {
	        this.instance = new SingletonClass();
	        System.out.println("枚举类构造函数");
	    }

	    Singleton(int i) {
	        this.instance = new SingletonClass();
	        System.out.println("枚举类构造函数"+i);
	    }
	    
	    public SingletonClass getInstance() {
	        return this.instance;
	    }

	    public SingletonClass getInstance2() {
	        return this.instance2;
	    }
	}
	 
	
	
}
