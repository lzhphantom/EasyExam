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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.security.annotation.Inner;
import com.lzhphantom.user.login.entity.Dept;
import com.lzhphantom.userimpl.service.DeptService;
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
 * 部门管理 前端控制器
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept")
@Tag(name = "部门管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {

	private final DeptService sysDeptService;

	/**
	 * 通过ID查询
	 * @param id ID
	 * @return SysDept
	 */
	@GetMapping("/{id:\\d+}")
	public LzhphantomResult<Dept> getById(@PathVariable Long id) {
		return LzhphantomResult.ok(sysDeptService.getById(id));
	}

	/**
	 * 返回树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	public LzhphantomResult<List<Tree<Long>>> listDeptTrees() {
		return LzhphantomResult.ok(sysDeptService.listDeptTrees());
	}

	/**
	 * 返回当前用户树形菜单集合
	 * @return 树形菜单
	 */
	@GetMapping(value = "/user-tree")
	public LzhphantomResult<List<Tree<Long>>> listCurrentUserDeptTrees() {
		return LzhphantomResult.ok(sysDeptService.listCurrentUserDeptTrees());
	}

	/**
	 * 添加
	 * @param sysDept 实体
	 * @return success/false
	 */
	@LzhphantomLog("添加部门")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_add')")
	public LzhphantomResult<Boolean> save(@Valid @RequestBody Dept sysDept) {
		return LzhphantomResult.ok(sysDeptService.saveDept(sysDept));
	}

	/**
	 * 删除
	 * @param id ID
	 * @return success/false
	 */
	@LzhphantomLog("删除部门")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_dept_del')")
	public LzhphantomResult<Boolean> removeById(@PathVariable Long id) {
		return LzhphantomResult.ok(sysDeptService.removeDeptById(id));
	}

	/**
	 * 编辑
	 * @param sysDept 实体
	 * @return success/false
	 */
	@LzhphantomLog("编辑部门")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_dept_edit')")
	public LzhphantomResult<Boolean> update(@Valid @RequestBody Dept sysDept) {
		return LzhphantomResult.ok(sysDeptService.updateDeptById(sysDept));
	}

	/**
	 * 根据部门名查询部门信息
	 * @param deptname 部门名
	 * @return
	 */
	@GetMapping("/details/{deptname}")
	public LzhphantomResult<Dept> user(@PathVariable String deptname) {
		Dept condition = new Dept();
		condition.setName(deptname);
		return LzhphantomResult.ok(sysDeptService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 查收子级id列表
	 * @return 返回子级id列表
	 */
	@Inner
	@GetMapping(value = "/child-id/{deptId:\\d+}")
	public LzhphantomResult<List<Long>> listChildDeptId(@PathVariable Long deptId) {
		return LzhphantomResult.ok(sysDeptService.listChildDeptId(deptId));
	}

}
