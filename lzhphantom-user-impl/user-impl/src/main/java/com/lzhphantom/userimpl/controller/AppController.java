package com.lzhphantom.userimpl.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.user.dto.AppSmsDTO;
import com.lzhphantom.user.dto.UserInfo;
import com.lzhphantom.user.login.entity.User;
import com.lzhphantom.userimpl.service.AppService;
import com.lzhphantom.userimpl.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * @author lengleng
 * @date 2021/9/16 移动端登录
 */
@RestController
@AllArgsConstructor
@RequestMapping("/app")
@Tag(name = "移动端登录模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class AppController {

	private final AppService appService;

	private final UserService userService;

	/**
	 * 发送手机验证码
	 * @param sms 请求手机对象
	 * @return code
	 */
	@Inner(value = false)
	@PostMapping("/sms")
	public LzhphantomResult<Boolean> sendSmsCode(@Valid @RequestBody AppSmsDTO sms) {
		return appService.sendSmsCode(sms);
	}

	/**
	 * 获取指定用户全部信息
	 * @param phone 手机号
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{phone}")
	public LzhphantomResult<UserInfo> infoByMobile(@PathVariable String phone) {
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getPhone, phone));
		if (user == null) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, phone));
		}
		return LzhphantomResult.ok(userService.getUserInfo(user));
	}

}
