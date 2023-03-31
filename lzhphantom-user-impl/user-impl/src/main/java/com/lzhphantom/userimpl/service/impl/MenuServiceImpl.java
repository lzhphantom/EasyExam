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

package com.lzhphantom.userimpl.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.CommonConstants;
import com.lzhphantom.core.constant.enums.MenuTypeEnum;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.login.entity.Menu;
import com.lzhphantom.user.login.entity.RoleMenu;
import com.lzhphantom.userimpl.mapper.MenuMapper;
import com.lzhphantom.userimpl.mapper.RoleMenuMapper;
import com.lzhphantom.userimpl.service.MenuService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lzhphantom
 * @since 2017-10-29
 */
@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

	private final RoleMenuMapper roleMenuMapper;

	@Override
	@Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")
	public Set<Menu> findMenuByRoleId(Long roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	/**
	 * 级联删除菜单
	 * @param id 菜单ID
	 * @return true成功, false失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean removeMenuById(Long id) {
		// 查询父节点为当前节点的节点
		List<Menu> menuList = this.list(Wrappers.<Menu>query().lambda().eq(Menu::getParentId, id));

		Assert.isTrue(CollUtil.isEmpty(menuList), MsgUtils.getMessage(ErrorCodes.SYS_MENU_DELETE_EXISTING));

		roleMenuMapper.delete(Wrappers.<RoleMenu>query().lambda().eq(RoleMenu::getMenuId, id));
		// 删除当前菜单及其子菜单
		return this.removeById(id);
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean updateMenuById(Menu Menu) {
		return this.updateById(Menu);
	}

	/**
	 * 构建树查询 1. 不是懒加载情况，查询全部 2. 是懒加载，根据parentId 查询 2.1 父节点为空，则查询ID -1
	 * @param lazy 是否是懒加载
	 * @param parentId 父节点ID
	 * @return
	 */
	@Override
	public List<Tree<Long>> treeMenu(boolean lazy, Long parentId) {
		if (!lazy) {
			List<TreeNode<Long>> collect = baseMapper
					.selectList(Wrappers.<Menu>lambdaQuery().orderByAsc(Menu::getSortOrder)).stream()
					.map(getNodeFunction()).collect(Collectors.toList());

			return TreeUtil.build(collect, CommonConstants.MENU_TREE_ROOT_ID);
		}

		Long parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;

		List<TreeNode<Long>> collect = baseMapper
				.selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parent)
						.orderByAsc(Menu::getSortOrder))
				.stream().map(getNodeFunction()).collect(Collectors.toList());

		return TreeUtil.build(collect, parent);
	}

	/**
	 * 查询菜单
	 * @param all 全部菜单
	 * @param parentId 父节点ID
	 * @return
	 */
	@Override
	public List<Tree<Long>> filterMenu(Set<Menu> all, Long parentId) {
		List<TreeNode<Long>> collect = all.stream()
				.filter(menu -> MenuTypeEnum.LEFT_MENU.getType().equals(menu.getType()))
				.filter(menu -> StrUtil.isNotBlank(menu.getPath())).map(getNodeFunction()).collect(Collectors.toList());
		Long parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
		return TreeUtil.build(collect, parent);
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public void clearMenuCache() {

	}

	@NotNull
	private Function<Menu, TreeNode<Long>> getNodeFunction() {
		return menu -> {
			TreeNode<Long> node = new TreeNode<>();
			node.setId(menu.getMenuId());
			node.setName(menu.getName());
			node.setParentId(menu.getParentId());
			node.setWeight(menu.getSortOrder());
			// 扩展属性
			Map<String, Object> extra = new HashMap<>();
			extra.put("icon", menu.getIcon());
			extra.put("path", menu.getPath());
			extra.put("type", menu.getType());
			extra.put("permission", menu.getPermission());
			extra.put("label", menu.getName());
			extra.put("sortOrder", menu.getSortOrder());
			extra.put("keepAlive", menu.getKeepAlive());
			node.setExtra(extra);
			return node;
		};
	}

}
