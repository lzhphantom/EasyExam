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

package com.lzhphantom.userimpl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.user.login.entity.Dept;
import com.lzhphantom.user.login.entity.DeptRelation;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
public interface DeptRelationService extends IService<DeptRelation> {

	/**
	 * 新建部门关系
	 * @param sysDept 部门
	 */
	void saveDeptRelation(Dept sysDept);

	/**
	 * 通过ID删除部门关系
	 * @param id
	 */
	void removeDeptRelationById(Long id);

	/**
	 * 更新部门关系
	 * @param relation
	 */
	void updateDeptRelation(DeptRelation relation);

}
