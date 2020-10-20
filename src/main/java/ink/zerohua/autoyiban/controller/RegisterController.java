package ink.zerohua.autoyiban.controller;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 03:44
 **/
@Controller
public class RegisterController extends BaseController {

	@Resource
	private UserService userService;

	@GetMapping("")
	public String toRegisterPage() {
		return "index";
	}

	/**
	 * 注册
	 * @return
	 */
	@PostMapping("/register")
	public String register(User user, Map<String, Object> dataMap) {
		String result = userService.addUser(user);
		String msg = null;
		if (result.equals("1")) {
			msg = "Congratulation! You have successfully started a new life.";
		}else if (result.equals("2")) {
			msg = "Sorry, username or password error!";
		}else {
			msg = "You have existed in the system,don't do that again!";
		}
		dataMap.put("msg", msg);
		return "index";
	}


}
