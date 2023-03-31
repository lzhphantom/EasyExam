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

import cn.hutool.core.lang.tree.Tree;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.util.SecurityUtils;
import com.lzhphantom.user.login.entity.Menu;
import com.lzhphantom.userimpl.service.MenuService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lzhphantom
 * @date 2017/10/31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/menu")
@Tag(name = "菜单管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class MenuController {

	private final MenuService sysMenuService;

	/**
	 * 返回当前用户的树形菜单集合
	 * @param parentId 父节点ID
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	public LzhphantomResult<List<Tree<Long>>> getUserMenu(Long parentId) {
		// 获取符合条件的菜单
		Set<Menu> menuSet = SecurityUtils.getRoles().stream().map(sysMenuService::findMenuByRoleId)
				.flatMap(Collection::stream).collect(Collectors.toSet());
		return LzhphantomResult.ok(sysMenuService.filterMenu(menuSet, parentId));
	}

	/**
	 * 返回树形菜单集合
	 * @param lazy 是否是懒加载
	 * @param parentId 父节点ID
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public LzhphantomResult<List<Tree<Long>>> getTree(boolean lazy, Long parentId) {
		return LzhphantomResult.ok(sysMenuService.treeMenu(lazy, parentId));
	}

	/**
	 * 返回角色的菜单集合
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	public LzhphantomResult<List<Long>> getRoleTree(@PathVariable Long roleId) {
		return LzhphantomResult.ok(
				sysMenuService.findMenuByRoleId(roleId).stream().map(Menu::getMenuId).collect(Collectors.toList()));
	}

	/**
	 * 通过ID查询菜单的详细信息
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id:\\d+}")
	public LzhphantomResult<Menu> getById(@PathVariable Long id) {
		return LzhphantomResult.ok(sysMenuService.getById(id));
	}

	/**
	 * 新增菜单
	 * @param sysMenu 菜单信息
	 * @return 含ID 菜单信息
	 */
	@LzhphantomLog("新增菜单")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_add')")
	public LzhphantomResult<Menu> save(@Valid @RequestBody Menu sysMenu) {
		sysMenuService.save(sysMenu);
		return LzhphantomResult.ok(sysMenu);
	}

	/**
	 * 删除菜单
	 * @param id 菜单ID
	 * @return success/false
	 */
	@LzhphantomLog("删除菜单")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	public LzhphantomResult<Boolean> removeById(@PathVariable Long id) {
		return LzhphantomResult.ok(sysMenuService.removeMenuById(id));
	}

	/**
	 * 更新菜单
	 * @param sysMenu
	 * @return
	 */
	@LzhphantomLog("更新菜单")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_menu_edit')")
	public LzhphantomResult<Boolean> update(@Valid @RequestBody Menu sysMenu) {
		return LzhphantomResult.ok(sysMenuService.updateMenuById(sysMenu));
	}

	/**
	 * 清除菜单缓存
	 */
	@LzhphantomLog("清除菜单缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	public LzhphantomResult clearMenuCache() {
		sysMenuService.clearMenuCache();
		return LzhphantomResult.ok();
	}

}
