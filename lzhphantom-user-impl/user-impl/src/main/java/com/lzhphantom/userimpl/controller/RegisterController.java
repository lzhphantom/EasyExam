package com.lzhphantom.userimpl.controller;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.user.dto.UserDTO;
import com.lzhphantom.userimpl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzhphantom
 * @date 2022/3/30
 *
 * 客户端注册功能 register.user = false
 */
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "register.user", matchIfMissing = true)
public class RegisterController {

	private final UserService userService;

	/**
	 * 注册用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@Inner(value = false)
	@LzhphantomLog("注册用户")
	@PostMapping("/user")
	public LzhphantomResult<Boolean> registerUser(@RequestBody UserDTO userDto) {
		return userService.registerUser(userDto);
	}

}
