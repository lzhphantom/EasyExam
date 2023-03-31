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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.user.login.entity.Dept;
import com.lzhphantom.user.login.entity.DeptRelation;
import com.lzhphantom.userimpl.mapper.DeptRelationMapper;
import com.lzhphantom.userimpl.service.DeptRelationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
@Service
@RequiredArgsConstructor
public class DeptRelationServiceImpl extends ServiceImpl<DeptRelationMapper, DeptRelation>
		implements DeptRelationService {

	private final DeptRelationMapper sysDeptRelationMapper;

	/**
	 * 维护部门关系
	 * @param dept 部门
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveDeptRelation(Dept dept) {
		// 增加部门关系表
		List<DeptRelation> relationList = sysDeptRelationMapper.selectList(
				Wrappers.<DeptRelation>query().lambda().eq(DeptRelation::getDescendant, dept.getParentId()))
				.stream().map(relation -> {
					relation.setDescendant(dept.getDeptId());
					return relation;
				}).collect(Collectors.toList());
		if (CollUtil.isNotEmpty(relationList)) {
			this.saveBatch(relationList);
		}

		// 自己也要维护到关系表中
		DeptRelation own = new DeptRelation();
		own.setDescendant(dept.getDeptId());
		own.setAncestor(dept.getDeptId());
		sysDeptRelationMapper.insert(own);
	}

	/**
	 * 通过ID删除部门关系
	 * @param id
	 */
	@Override
	public void removeDeptRelationById(Long id) {
		baseMapper.deleteDeptRelationsById(id);
	}

	/**
	 * 更新部门关系
	 * @param relation
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateDeptRelation(DeptRelation relation) {
		baseMapper.deleteDeptRelations(relation);
		baseMapper.insertDeptRelations(relation);
	}

}
