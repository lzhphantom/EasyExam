/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lzhphantom.userimpl.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.user.login.entity.OauthClientDetails;
import com.lzhphantom.userimpl.service.OauthClientDetailsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lzhphantom
 * @since 2018-05-15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Tag(name = "客户端管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class OauthClientDetailsController {

	private final OauthClientDetailsService sysOauthClientDetailsService;

	/**
	 * 通过ID查询
	 * @param clientId 客户端id
	 * @return OauthClientDetails
	 */
	@GetMapping("/{clientId}")
	public LzhphantomResult<List<OauthClientDetails>> getByClientId(@PathVariable String clientId) {
		return LzhphantomResult.ok(sysOauthClientDetailsService
				.list(Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId)));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param sysOauthClientDetails 系统终端
	 * @return
	 */
	@GetMapping("/page")
	public LzhphantomResult<IPage<OauthClientDetails>> getOauthClientDetailsPage(Page page,
			OauthClientDetails sysOauthClientDetails) {
		return LzhphantomResult.ok(sysOauthClientDetailsService.page(page, Wrappers.query(sysOauthClientDetails)));
	}

	/**
	 * 添加
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@LzhphantomLog("添加终端")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_client_add')")
	public LzhphantomResult<Boolean> add(@Valid @RequestBody OauthClientDetails sysOauthClientDetails) {
		return LzhphantomResult.ok(sysOauthClientDetailsService.save(sysOauthClientDetails));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@LzhphantomLog("删除终端")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public LzhphantomResult<Boolean> removeById(@PathVariable String id) {
		return LzhphantomResult.ok(sysOauthClientDetailsService.removeClientDetailsById(id));
	}

	/**
	 * 编辑
	 * @param sysOauthClientDetails 实体
	 * @return success/false
	 */
	@LzhphantomLog("编辑终端")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_client_edit')")
	public LzhphantomResult<Boolean> update(@Valid @RequestBody OauthClientDetails sysOauthClientDetails) {
		return LzhphantomResult.ok(sysOauthClientDetailsService.updateClientDetailsById(sysOauthClientDetails));
	}

	@LzhphantomLog("清除终端缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_client_del')")
	public LzhphantomResult clearClientCache() {
		sysOauthClientDetailsService.clearClientCache();
		return LzhphantomResult.ok();
	}

	@Inner
	@GetMapping("/getClientDetailsById/{clientId}")
	public LzhphantomResult getClientDetailsById(@PathVariable String clientId) {
		return LzhphantomResult.ok(sysOauthClientDetailsService.getOne(
				Wrappers.<OauthClientDetails>lambdaQuery().eq(OauthClientDetails::getClientId, clientId), false));
	}

}
