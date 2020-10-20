package ink.zerohua.autoyiban.controller;

import ink.zerohua.autoyiban.entity.User;
import ink.zerohua.autoyiban.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * @program: auto-yiban
 * @author: zerohua
 * @create: 2020-10-17 10:30
 **/
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Resource
	private UserService userService;

	@GetMapping("")
	public String toLoginPage() {
		return "";
	}

	@PostMapping("/check")
	public String login(User user) {
		if (userService.verify(user).equals("1")) {
			return "";
		}
		return "";
	}
}
