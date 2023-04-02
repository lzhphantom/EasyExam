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
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.CommonConstants;
import com.lzhphantom.core.constant.enums.MenuTypeEnum;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.dto.UserDTO;
import com.lzhphantom.user.dto.UserInfo;
import com.lzhphantom.user.login.entity.*;
import com.lzhphantom.user.util.ParamResolver;
import com.lzhphantom.user.vo.UserExcelVO;
import com.lzhphantom.user.vo.UserVO;
import com.lzhphantom.userimpl.mapper.*;
import com.lzhphantom.userimpl.service.AppService;
import com.lzhphantom.userimpl.service.MenuService;
import com.lzhphantom.userimpl.service.UserService;
import com.pig4cloud.plugin.excel.vo.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lzhphantom
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	private final AppService appService;

	private final RoleMapper roleMapper;

	private final DeptMapper deptMapper;

	private final MenuService menuService;

	private final PostMapper postMapper;

	private final UserRoleMapper userRoleMapper;

	private final UserPostMapper userPostMapper;

	/**
	 * 保存用户信息
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		User User = new User();
		BeanUtils.copyProperties(userDto, User);
		User.setDelFlag(CommonConstants.STATUS_NORMAL);
		User.setPassword(ENCODER.encode(userDto.getPassword()));
		baseMapper.insert(User);
		userDto.getRole().stream().map(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(User.getUserId());
			userRole.setRoleId(roleId);
			return userRole;
		}).forEach(userRoleMapper::insert);
		// 保存用户岗位信息
		Optional.ofNullable(userDto.getPost()).ifPresent(posts -> posts.stream().map(postId -> {
			UserPost userPost = new UserPost();
			userPost.setUserId(User.getUserId());
			userPost.setPostId(postId);
			return userPost;
		}).forEach(userPostMapper::insert));
		return Boolean.TRUE;
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param User 用户
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(User User) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUser(User);
		// 设置角色列表
		List<Role> roleList = roleMapper.listRolesByUserId(User.getUserId());
		userInfo.setRoleList(roleList);
		// 设置角色列表 （ID）
		List<Long> roleIds = roleList.stream().map(Role::getRoleId).collect(Collectors.toList());
		userInfo.setRoles(ArrayUtil.toArray(roleIds, Long.class));
		// 设置岗位列表
		List<Post> postList = postMapper.listPostsByUserId(User.getUserId());
		userInfo.setPostList(postList);
		// 设置权限列表（menu.permission）
		Set<String> permissions = roleIds.stream().map(menuService::findMenuByRoleId).flatMap(Collection::stream)
				.filter(m -> MenuTypeEnum.BUTTON.getType().equals(m.getType())).map(Menu::getPermission)
				.filter(StrUtil::isNotBlank).collect(Collectors.toSet());
		userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));

		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage<UserVO> getUserWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * 通过ID查询用户信息
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO getUserVoById(Long id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 * @param User 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#User.username")
	public Boolean removeUserById(User User) {
		userRoleMapper.deleteByUserId(User.getUserId());
		// 删除用户职位关系
		userPostMapper.delete(Wrappers.<UserPost>lambdaQuery().eq(UserPost::getUserId, User.getUserId()));
		this.removeById(User.getUserId());
		return Boolean.TRUE;
	}

	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public LzhphantomResult<Boolean> updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());

		// 判断手机号是否修改,更新手机号校验验证码
		if (!StrUtil.equals(userVO.getPhone(), userDto.getPhone())) {
			if (!appService.check(userDto.getPhone(), userDto.getCode())) {
				return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_ERROR));
			}
		}

		// 修改密码逻辑
		User User = new User();
		if (StrUtil.isNotBlank(userDto.getNewpassword1())) {
			Assert.isTrue(ENCODER.matches(userDto.getPassword(), userVO.getPassword()),
					MsgUtils.getMessage(ErrorCodes.SYS_USER_UPDATE_PASSWORDERROR));
			User.setPassword(ENCODER.encode(userDto.getNewpassword1()));
		}
		User.setPhone(userDto.getPhone());
		User.setUserId(userVO.getUserId());
		User.setAvatar(userDto.getAvatar());
		return LzhphantomResult.ok(this.updateById(User));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public LzhphantomResult<Boolean> updateUser(UserDTO userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			user.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		this.updateById(user);

		userRoleMapper
				.delete(Wrappers.<UserRole>update().lambda().eq(UserRole::getUserId, userDto.getUserId()));
		userDto.getRole().forEach(roleId -> {
			UserRole userRole = new UserRole();
			userRole.setUserId(user.getUserId());
			userRole.setRoleId(roleId);
			userRole.insert();
		});
		userPostMapper.delete(Wrappers.<UserPost>lambdaQuery().eq(UserPost::getUserId, userDto.getUserId()));
		userDto.getPost().forEach(postId -> {
			UserPost userPost = new UserPost();
			userPost.setUserId(user.getUserId());
			userPost.setPostId(postId);
			userPost.insert();
		});
		return LzhphantomResult.ok();
	}

	/**
	 * 查询上级部门的用户信息
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<User> listAncestorUsersByUsername(String username) {
		User user = this.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));

		Dept Dept = deptMapper.selectById(user.getDeptId());
		if (Dept == null) {
			return null;
		}

		Long parentId = Dept.getParentId();
		return this.list(Wrappers.<User>query().lambda().eq(User::getDeptId, parentId));
	}

	/**
	 * 查询全部的用户
	 *
	 * @param userDTO 查询条件
	 * @return list
	 */
	@Override
	public List<UserExcelVO> listUser(UserDTO userDTO) {
		List<UserVO> voList = baseMapper.selectVoList(userDTO);
		// 转换成excel 对象输出
		return voList.stream().map(userVO -> {
			UserExcelVO excelVO = new UserExcelVO();
			BeanUtils.copyProperties(userVO, excelVO);
			String roleNameList = userVO.getRoleList().stream().map(Role::getRoleName)
					.collect(Collectors.joining(StrUtil.COMMA));
			excelVO.setRoleNameList(roleNameList);
			String postNameList = userVO.getPostList().stream().map(Post::getPostName)
					.collect(Collectors.joining(StrUtil.COMMA));
			excelVO.setPostNameList(postNameList);
			return excelVO;
		}).collect(Collectors.toList());
	}

	/**
	 * excel 导入用户, 插入正确的 错误的提示行号
	 * @param excelVOList excel 列表数据
	 * @param bindingResult 错误数据
	 * @return ok fail
	 */
	@Override
	public LzhphantomResult importUser(List<UserExcelVO> excelVOList, BindingResult bindingResult) {
		// 通用校验获取失败的数据
		List<ErrorMessage> errorMessageList = (List<ErrorMessage>) bindingResult.getTarget();

		// 个性化校验逻辑
		List<User> userList = this.list();
		List<Dept> deptList = deptMapper.selectList(Wrappers.emptyWrapper());
		List<Role> roleList = roleMapper.selectList(Wrappers.emptyWrapper());
		List<Post> postList = postMapper.selectList(Wrappers.emptyWrapper());

		// 执行数据插入操作 组装 UserDto
		for (UserExcelVO excel : excelVOList) {
			Set<String> errorMsg = new HashSet<>();
			// 校验用户名是否存在
			boolean existUserName = userList.stream()
					.anyMatch(User -> excel.getUsername().equals(User.getUsername()));

			if (existUserName) {
				errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, excel.getUsername()));
			}

			// 判断输入的部门名称列表是否合法
			Optional<Dept> deptOptional = deptList.stream()
					.filter(dept -> excel.getDeptName().equals(dept.getName())).findFirst();
			if (!deptOptional.isPresent()) {
				errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_DEPT_DEPTNAME_INEXISTENCE, excel.getDeptName()));
			}

			// 判断输入的角色名称列表是否合法
			List<String> roleNameList = StrUtil.split(excel.getRoleNameList(), StrUtil.COMMA);
			List<Role> roleCollList = roleList.stream()
					.filter(role -> roleNameList.stream().anyMatch(name -> role.getRoleName().equals(name)))
					.collect(Collectors.toList());

			if (roleCollList.size() != roleNameList.size()) {
				errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_ROLE_ROLENAME_INEXISTENCE, excel.getRoleNameList()));
			}

			// 判断输入的岗位名称列表是否合法
			List<String> postNameList = StrUtil.split(excel.getPostNameList(), StrUtil.COMMA);
			List<Post> postCollList = postList.stream()
					.filter(post -> postNameList.stream().anyMatch(name -> post.getPostName().equals(name)))
					.collect(Collectors.toList());

			if (postCollList.size() != postNameList.size()) {
				errorMsg.add(MsgUtils.getMessage(ErrorCodes.SYS_POST_POSTNAME_INEXISTENCE, excel.getPostNameList()));
			}

			// 数据合法情况
			if (CollUtil.isEmpty(errorMsg)) {
				insertExcelUser(excel, deptOptional, roleCollList, postCollList);
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

	@Override
	public List<Long> listUserIdByDeptIds(Set<Long> deptIds) {
		return this.listObjs(
				Wrappers.lambdaQuery(User.class).select(User::getUserId).in(User::getDeptId, deptIds),
				Long.class::cast);
	}

	/**
	 * 插入excel User
	 */
	private void insertExcelUser(UserExcelVO excel, Optional<Dept> deptOptional, List<Role> roleCollList,
			List<Post> postCollList) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(excel.getUsername());
		userDTO.setPhone(excel.getPhone());
		// 批量导入初始密码为手机号
		userDTO.setPassword(userDTO.getPhone());
		// 根据部门名称查询部门ID
		userDTO.setDeptId(deptOptional.get().getDeptId());
		// 根据角色名称查询角色ID
		List<Long> roleIdList = roleCollList.stream().map(Role::getRoleId).collect(Collectors.toList());
		userDTO.setRole(roleIdList);
		List<Long> postIdList = postCollList.stream().map(Post::getPostId).collect(Collectors.toList());
		userDTO.setPost(postIdList);
		// 插入用户
		this.saveUser(userDTO);
	}

	/**
	 * 注册用户 赋予用户默认角色
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@Override
	public LzhphantomResult<Boolean> registerUser(UserDTO userDto) {
		// 校验验证码
		if (!appService.check(userDto.getPhone(), userDto.getCode())) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_APP_SMS_ERROR));
		}

		// 判断用户名是否存在
		User user = this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, userDto.getUsername()));
		if (user != null) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_USER_USERNAME_EXISTING, userDto.getUsername()));
		}

		// 获取默认角色编码
		String defaultRole = ParamResolver.getStr("USER_DEFAULT_ROLE");
		// 默认角色
		Role role = roleMapper
				.selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getRoleCode, defaultRole));

		if (role == null) {
			return LzhphantomResult.failed(MsgUtils.getMessage(ErrorCodes.SYS_PARAM_CONFIG_ERROR, "USER_DEFAULT_ROLE"));
		}

		userDto.setRole(Collections.singletonList(role.getRoleId()));
		return LzhphantomResult.ok(saveUser(userDto));
	}

}
