package test;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.auth.model.User;
import com.auth.service.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={
		"classpath:applicationContext.xml",
		"classpath:spring-mvc.xml",
		"classpath:applicationContext-shiro.xml"
		})
public class Test {
	
	@Resource
	private UserServiceImpl userService;
	
	
	@org.junit.Test
	public void aaaaaa(){
		User user=userService.findByUserName("a");
		System.out.println(user);
		System.out.println(user.getRoleList());
		System.out.println(user.getRoleList().get(0).getAuthList());
		System.out.println(user.getRoleList().get(0).getAuthList().get(0).getMenuList());
	}
	
	
	
	public void test(){
		String name="zhang";
		name=name==null?"yuyu":"";
	}

}
