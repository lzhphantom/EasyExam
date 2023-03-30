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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.security.util.SecurityUtils;
import com.lzhphantom.user.login.entity.Dept;
import com.lzhphantom.userimpl.mapper.DeptMapper;
import com.lzhphantom.userimpl.service.DeptRelationService;
import com.lzhphantom.userimpl.service.DeptService;
import com.pig4cloud.pig.admin.api.entity.SysDept;
import com.pig4cloud.pig.admin.api.entity.SysDeptRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

	private final DeptRelationService sysDeptRelationService;

	/**
	 * 添加信息部门
	 * @param dept 部门
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveDept(Dept dept) {
		Dept sysDept = new Dept();
		BeanUtils.copyProperties(dept, sysDept);
		this.save(sysDept);
		sysDeptRelationService.saveDeptRelation(sysDept);
		return Boolean.TRUE;
	}

	/**
	 * 删除部门
	 * @param id 部门 ID
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeDeptById(Long id) {
		// 级联删除部门
		List<Long> idList = sysDeptRelationService
				.list(Wrappers.<DeptRelation>query().lambda().eq(DeptRelation::getAncestor, id)).stream()
				.map(SysDeptRelation::getDescendant).collect(Collectors.toList());

		if (CollUtil.isNotEmpty(idList)) {
			this.removeByIds(idList);
		}

		// 删除部门级联关系
		sysDeptRelationService.removeDeptRelationById(id);
		return Boolean.TRUE;
	}

	/**
	 * 更新部门
	 * @param sysDept 部门信息
	 * @return 成功、失败
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateDeptById(Dept sysDept) {
		// 更新部门状态
		this.updateById(sysDept);
		// 更新部门关系
		DeptRelation relation = new DeptRelation();
		relation.setAncestor(sysDept.getParentId());
		relation.setDescendant(sysDept.getDeptId());
		sysDeptRelationService.updateDeptRelation(relation);
		return Boolean.TRUE;
	}

	@Override
	public List<Long> listChildDeptId(Long deptId) {
		List<DeptRelation> deptRelations = sysDeptRelationService.list(Wrappers.<DeptRelation>lambdaQuery()
				.eq(DeptRelation::getAncestor, deptId).ne(DeptRelation::getDescendant, deptId));
		if (CollUtil.isNotEmpty(deptRelations)) {
			return deptRelations.stream().map(DeptRelation::getDescendant).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	/**
	 * 查询全部部门树
	 * @return 树
	 */
	@Override
	public List<Tree<Long>> listDeptTrees() {
		return getDeptTree(this.list(Wrappers.emptyWrapper()), 0L);
	}

	/**
	 * 查询用户部门树
	 * @return
	 */
	@Override
	public List<Tree<Long>> listCurrentUserDeptTrees() {
		Long deptId = SecurityUtils.getUser().getDeptId();
		List<Long> descendantIdList = sysDeptRelationService
				.list(Wrappers.<DeptRelation>query().lambda().eq(DeptRelation::getAncestor, deptId)).stream()
				.map(DeptRelation::getDescendant).collect(Collectors.toList());

		List<Dept> deptList = baseMapper.selectBatchIds(descendantIdList);
		Optional<Dept> dept = deptList.stream().filter(item -> item.getDeptId().intValue() == deptId).findFirst();
		return getDeptTree(deptList, dept.isPresent() ? dept.get().getParentId() : 0L);
	}

	/**
	 * 构建部门树
	 * @param depts 部门
	 * @param parentId 父级id
	 * @return
	 */
	private List<Tree<Long>> getDeptTree(List<Dept> depts, Long parentId) {
		List<TreeNode<Long>> collect = depts.stream().filter(dept -> dept.getDeptId().intValue() != dept.getParentId())
				.sorted(Comparator.comparingInt(SysDept::getSortOrder)).map(dept -> {
					TreeNode<Long> treeNode = new TreeNode();
					treeNode.setId(dept.getDeptId());
					treeNode.setParentId(dept.getParentId());
					treeNode.setName(dept.getName());
					treeNode.setWeight(dept.getSortOrder());
					// 扩展属性
					Map<String, Object> extra = new HashMap<>(4);
					extra.put("createTime", dept.getCreateTime());
					treeNode.setExtra(extra);
					return treeNode;
				}).collect(Collectors.toList());

		return TreeUtil.build(collect, parentId);
	}

}
