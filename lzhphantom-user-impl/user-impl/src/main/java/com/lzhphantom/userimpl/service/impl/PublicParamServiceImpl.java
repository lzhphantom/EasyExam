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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.enums.DictTypeEnum;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.login.entity.PublicParam;
import com.lzhphantom.userimpl.mapper.PublicParamMapper;
import com.lzhphantom.userimpl.service.PublicParamService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
@Service
@AllArgsConstructor
public class PublicParamServiceImpl extends ServiceImpl<PublicParamMapper, PublicParam>
		implements PublicParamService {

	@Override
	@Cacheable(value = CacheConstants.PARAMS_DETAILS, key = "#publicKey", unless = "#result == null ")
	public String getSysPublicParamKeyToValue(String publicKey) {
		PublicParam publicParam = this.baseMapper
				.selectOne(Wrappers.<PublicParam>lambdaQuery().eq(PublicParam::getPublicKey, publicKey));

		if (publicParam != null) {
			return publicParam.getPublicValue();
		}
		return null;
	}

	/**
	 * 更新参数
	 * @param PublicParam
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, key = "#PublicParam.publicKey")
	public LzhphantomResult updateParam(PublicParam PublicParam) {
		PublicParam param = this.getById(PublicParam.getPublicId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystemFlag())) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_PARAM_DELETE_SYSTEM));
		}
		return LzhphantomResult.ok(this.updateById(PublicParam));
	}

	/**
	 * 删除参数
	 * @param publicId
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
	public LzhphantomResult removeParam(Long publicId) {
		PublicParam param = this.getById(publicId);
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystemFlag())) {
			return LzhphantomResult.failed("系统内置参数不能删除");
		}
		return LzhphantomResult.ok(this.removeById(publicId));
	}

	/**
	 * 同步缓存
	 * @return R
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
	public LzhphantomResult syncParamCache() {
		return LzhphantomResult.ok();
	}

}
