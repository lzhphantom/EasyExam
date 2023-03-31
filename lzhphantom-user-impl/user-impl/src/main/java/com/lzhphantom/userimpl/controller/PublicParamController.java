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

package com.lzhphantom.userimpl.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.user.login.entity.PublicParam;
import com.lzhphantom.userimpl.service.PublicParamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 公共参数
 *
 * @author Lucky
 * @date 2019-04-29
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/param")
@Tag(name = "公共参数配置")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PublicParamController {

	private final PublicParamService sysPublicParamService;

	/**
	 * 通过key查询公共参数值
	 * @param publicKey
	 * @return
	 */
	@Inner(value = false)
	@Operation(summary = "查询公共参数值", description = "根据key查询公共参数值")
	@GetMapping("/publicValue/{publicKey}")
	public LzhphantomResult publicKey(@PathVariable("publicKey") String publicKey) {
		return LzhphantomResult.ok(sysPublicParamService.getSysPublicParamKeyToValue(publicKey));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysPublicParam 公共参数
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	public LzhphantomResult getSysPublicParamPage(Page page, PublicParam sysPublicParam) {
		return LzhphantomResult.ok(sysPublicParamService.page(page,
				Wrappers.<PublicParam>lambdaQuery()
						.like(StrUtil.isNotBlank(sysPublicParam.getPublicName()), PublicParam::getPublicName,
								sysPublicParam.getPublicName())
						.like(StrUtil.isNotBlank(sysPublicParam.getPublicKey()), PublicParam::getPublicKey,
								sysPublicParam.getPublicKey())));
	}

	/**
	 * 通过id查询公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "通过id查询公共参数", description = "通过id查询公共参数")
	@GetMapping("/{publicId}")
	public LzhphantomResult getById(@PathVariable("publicId") Long publicId) {
		return LzhphantomResult.ok(sysPublicParamService.getById(publicId));
	}

	/**
	 * 新增公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "新增公共参数", description = "新增公共参数")
	@LzhphantomLog("新增公共参数")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_add')")
	public LzhphantomResult save(@RequestBody PublicParam sysPublicParam) {
		return LzhphantomResult.ok(sysPublicParamService.save(sysPublicParam));
	}

	/**
	 * 修改公共参数
	 * @param sysPublicParam 公共参数
	 * @return R
	 */
	@Operation(summary = "修改公共参数", description = "修改公共参数")
	@LzhphantomLog("修改公共参数")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_publicparam_edit')")
	public LzhphantomResult updateById(@RequestBody PublicParam sysPublicParam) {
		return sysPublicParamService.updateParam(sysPublicParam);
	}

	/**
	 * 通过id删除公共参数
	 * @param publicId id
	 * @return R
	 */
	@Operation(summary = "删除公共参数", description = "删除公共参数")
	@LzhphantomLog("删除公共参数")
	@DeleteMapping("/{publicId}")
	@PreAuthorize("@pms.hasPermission('sys_publicparam_del')")
	public LzhphantomResult removeById(@PathVariable Long publicId) {
		return sysPublicParamService.removeParam(publicId);
	}

	/**
	 * 同步参数
	 * @return R
	 */
	@LzhphantomLog("同步参数")
	@PutMapping("/sync")
	public LzhphantomResult sync() {
		return sysPublicParamService.syncParamCache();
	}

}
