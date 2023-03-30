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

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.login.entity.Role;
import com.lzhphantom.user.vo.RoleExcelVO;
import com.lzhphantom.userimpl.mapper.RoleMapper;
import com.lzhphantom.userimpl.mapper.RoleMenuMapper;
import com.lzhphantom.userimpl.service.RoleService;
import com.pig4cloud.pig.admin.api.entity.RoleMenu;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

	private final RoleMenuMapper roleMenuMapper;

	/**
	 * 通过角色ID，删除角色,并清空角色菜单缓存
	 * @param id
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean removeRoleById(Long id) {
		RoleMenuMapper.delete(Wrappers.<RoleMenu>update().lambda().eq(RoleMenu::getRoleId, id));
		return this.removeById(id);
	}

	/**
	 * 导入角色
	 * @param excelVOList 角色列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@Override
	public LzhphantomResult importRole(List<RoleExcelVO> excelVOList, BindingResult bindingResult) {
		// 通用校验获取失败的数据
		List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();

		// 个性化校验逻辑
		List<Role> roleList = this.list();

		// 执行数据插入操作 组装 RoleDto
		for (RoleExcelVO excel : excelVOList) {
			Set<String> errorMsg = new HashSet<>();
			// 检验角色名称或者角色编码是否存在
			boolean existRole = roleList.stream().anyMatch(Role -> excel.getRoleName().equals(Role.getRoleName())
					|| excel.getRoleCode().equals(Role.getRoleCode()));

			if (existRole) {
				errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_ROLE_NAMEORCODE_EXISTING, excel.getRoleName(),
						excel.getRoleCode()));
			}

			// 数据合法情况
			if (CollUtil.isEmpty(errorMsg)) {
				insertExcelRole(excel);
			}
			else {
				// 数据不合法情况
				errorMessageList.add(new ErrorMessage(excel.getLineNum(), errorMsg));
			}
		}
		if (CollUtil.isNotEmpty(errorMessageList)) {
			return LzhphantomResult.failed(errorMessageList);
		}
		return LzhphantomResult.ok();
	}

	/**
	 * 查询全部的角色
	 * @return list
	 */
	@Override
	public List<RoleExcelVO> listRole() {
		List<Role> roleList = this.list(Wrappers.emptyWrapper());
		// 转换成execl 对象输出
		return roleList.stream().map(role -> {
			RoleExcelVO roleExcelVO = new RoleExcelVO();
			BeanUtil.copyProperties(role, roleExcelVO);
			return roleExcelVO;
		}).collect(Collectors.toList());
	}

	/**
	 * 插入excel Role
	 */
	private void insertExcelRole(RoleExcelVO excel) {
		Role Role = new Role();
		Role.setRoleName(excel.getRoleName());
		Role.setRoleDesc(excel.getRoleDesc());
		Role.setRoleCode(excel.getRoleCode());
		this.save(Role);
	}

}
