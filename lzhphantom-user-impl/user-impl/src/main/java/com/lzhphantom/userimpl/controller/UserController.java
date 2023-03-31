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

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.security.util.SecurityUtils;
import com.lzhphantom.user.dto.UserDTO;
import com.lzhphantom.user.dto.UserInfo;
import com.lzhphantom.user.login.entity.User;
import com.lzhphantom.user.vo.UserExcelVO;
import com.lzhphantom.user.vo.UserInfoVO;
import com.lzhphantom.user.vo.UserVO;
import com.lzhphantom.userimpl.service.UserService;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author lzhphantom
 * @date 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "用户管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class UserController {

	private final UserService userService;

	/**
	 * 获取当前用户全部信息
	 * @return 用户信息
	 */
	@GetMapping(value = { "/info" })
	public LzhphantomResult<UserInfoVO> info() {
		String username = SecurityUtils.getUser().getUsername();
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
		if (user == null) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_QUERY_ERROR));
		}
		UserInfo userInfo = userService.getUserInfo(user);
		UserInfoVO vo = new UserInfoVO();
		vo.setUser(userInfo.getUser());
		vo.setRoles(userInfo.getRoles());
		vo.setPermissions(userInfo.getPermissions());
		return LzhphantomResult.ok(vo);
	}

	/**
	 * 获取指定用户全部信息
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{username}")
	public LzhphantomResult<UserInfo> info(@PathVariable String username) {
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
		if (user == null) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERINFO_EMPTY, username));
		}
		return LzhphantomResult.ok(userService.getUserInfo(user));
	}

	/**
	 * 根据部门id，查询对应的用户 id 集合
	 * @param deptIds 部门id 集合
	 * @return 用户 id 集合
	 */
	@Inner
	@GetMapping("/ids")
	public LzhphantomResult<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds) {
		return LzhphantomResult.ok(userService.listUserIdByDeptIds(deptIds));
	}

	/**
	 * 通过ID查询用户信息
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id:\\d+}")
	public LzhphantomResult<UserVO> user(@PathVariable Long id) {
		return LzhphantomResult.ok(userService.getUserVoById(id));
	}

	/**
	 * 判断用户是否存在
	 * @param userDTO 查询条件
	 * @return
	 */
	@Inner(false)
	@GetMapping("/check/exsit")
	public LzhphantomResult<Boolean> isExsit(UserDTO userDTO) {
		List<User> sysUserList = userService.list(new QueryWrapper<>(userDTO));
		if (CollUtil.isNotEmpty(sysUserList)) {
			return LzhphantomResult.ok(Boolean.TRUE, MsgUtils.getMessage(ErrorCodes.SYS_USER_EXISTING));
		}
		return LzhphantomResult.ok(Boolean.FALSE);
	}

	/**
	 * 删除用户信息
	 * @param id ID
	 * @return R
	 */
	@LzhphantomLog("删除用户信息")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	public LzhphantomResult<Boolean> userDel(@PathVariable Long id) {
		User sysUser = userService.getById(id);
		return LzhphantomResult.ok(userService.removeUserById(sysUser));
	}

	/**
	 * 添加用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@LzhphantomLog("添加用户")
	@PostMapping
	@XssCleanIgnore({ "password" })
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public LzhphantomResult<Boolean> user(@RequestBody UserDTO userDto) {
		return LzhphantomResult.ok(userService.saveUser(userDto));
	}

	/**
	 * 管理员更新用户信息
	 * @param userDto 用户信息
	 * @return R
	 */
	@LzhphantomLog("更新用户信息")
	@PutMapping
	@XssCleanIgnore({ "password" })
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public LzhphantomResult<Boolean> updateUser(@Valid @RequestBody UserDTO userDto) {
		return userService.updateUser(userDto);
	}

	/**
	 * 分页查询用户
	 * @param page 参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public LzhphantomResult<IPage<UserVO>> getUserPage(Page page, UserDTO userDTO) {
		return LzhphantomResult.ok(userService.getUserWithRolePage(page, userDTO));
	}

	/**
	 * 个人修改个人信息
	 * @param userDto userDto
	 * @return success/false
	 */
	@LzhphantomLog("修改个人信息")
	@PutMapping("/edit")
	@XssCleanIgnore({ "password", "newpassword1" })
	public LzhphantomResult<Boolean> updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		userDto.setUsername(SecurityUtils.getUser().getUsername());
		return userService.updateUserInfo(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public LzhphantomResult<List<User>> listAncestorUsers(@PathVariable String username) {
		return LzhphantomResult.ok(userService.listAncestorUsersByUsername(username));
	}

	/**
	 * 导出excel 表格
	 * @param userDTO 查询条件
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_user_import_export')")
	public List<UserExcelVO> export(UserDTO userDTO) {
		return userService.listUser(userDTO);
	}

	/**
	 * 导入用户
	 * @param excelVOList 用户列表
	 * @param bindingResult 错误信息列表
	 * @return R
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('sys_user_import_export')")
	public LzhphantomResult importUser(@RequestExcel List<UserExcelVO> excelVOList, BindingResult bindingResult) {
		return userService.importUser(excelVOList, bindingResult);
	}

}
