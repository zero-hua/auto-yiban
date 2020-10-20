package ink.zerohua.autoyiban;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class AutoYibanApplicationTests {

	@Resource
	UserService userService;

	@Test
	void contextLoads() {
		User user = new User();
		user.setYibanAccount("201803120103");
		System.out.println(userService.getOne(user));
	}

}
