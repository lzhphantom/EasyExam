/*
 *    Copyright (c) 2018-2025, lzhphantom All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lzhphantom (wangiegie@gmail.com)
 */

package com.lzhphantom.userimpl.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.dto.AppSmsDTO;
import com.lzhphantom.user.login.entity.User;
import com.lzhphantom.userimpl.mapper.UserMapper;
import com.lzhphantom.userimpl.service.AppService;
import io.springboot.sms.core.SmsClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author lzhphantom
 * <p>
 * 手机登录相关业务实现
 */
@Slf4j
@Service
@AllArgsConstructor
public class AppServiceImpl implements AppService {

	private final RedisTemplate redisTemplate;

	private final UserMapper userMapper;

	private final SmsClient smsClient;

	/**
	 * 发送手机验证码 TODO: 调用短信网关发送验证码,测试返回前端
	 * @param sms 手机号
	 * @return code
	 */
	@Override
	public LzhphantomResult<Boolean> sendSmsCode(AppSmsDTO sms) {
		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + sms.getPhone());

		if (codeObj != null) {
			log.info("手机号验证码未过期:{}，{}", sms.getPhone(), codeObj);
			return LzhphantomResult.ok(Boolean.FALSE, MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_OFTEN));
		}

		// 校验手机号是否存在 sys_user 表
		if (sms.getExist()
				&& !userMapper.exists(Wrappers.<User>lambdaQuery().eq(User::getPhone, sms.getPhone()))) {
			return LzhphantomResult.ok(Boolean.FALSE, MsgUtils.getMessage(ErrorCodes.SYS_APP_PHONE_UNREGISTERED, sms.getPhone()));
		}

		String code = RandomUtil.randomNumbers(Integer.parseInt(SecurityConstants.CODE_SIZE));
		log.info("手机号生成验证码成功:{},{}", sms.getPhone(), code);
		redisTemplate.opsForValue().set(CacheConstants.DEFAULT_CODE_KEY + sms.getPhone(), code,
				SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

		// 调用短信通道发送
		this.smsClient.sendCode(code, sms.getPhone());
		return LzhphantomResult.ok(Boolean.TRUE, code);
	}

	/**
	 * 校验验证码
	 * @param phone 手机号
	 * @param code 验证码
	 * @return
	 */
	@Override
	public boolean check(String phone, String code) {
		Object codeObj = redisTemplate.opsForValue().get(CacheConstants.DEFAULT_CODE_KEY + phone);

		if (Objects.isNull(codeObj)) {
			return false;
		}
		return codeObj.equals(code);
	}

}
