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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.common.util.MsgUtils;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.core.constant.enums.DictTypeEnum;
import com.lzhphantom.core.exception.ErrorCodes;
import com.lzhphantom.user.login.entity.Dict;
import com.lzhphantom.user.login.entity.DictItem;
import com.lzhphantom.userimpl.mapper.DictItemMapper;
import com.lzhphantom.userimpl.mapper.DictMapper;
import com.lzhphantom.userimpl.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * 字典表
 *
 * @author lzhphantom
 * @date 2019/03/19
 */
@Service
@RequiredArgsConstructor
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

	private final DictItemMapper dictItemMapper;

	/**
	 * 根据ID 删除字典
	 * @param id 字典ID
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public void removeDict(Long id) {
		Dict dict = this.getById(id);
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(dict.getSystemFlag()),
				MsgUtils.getMessage(ErrorCodes.SYS_DICT_DELETE_SYSTEM));
		baseMapper.deleteById(id);
		dictItemMapper.delete(Wrappers.<DictItem>lambdaQuery().eq(DictItem::getDictId, id));
	}

	/**
	 * 更新字典
	 * @param dict 字典
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, key = "#dict.type")
	public void updateDict(Dict dict) {
		Dict Dict = this.getById(dict.getId());
		// 系统内置
		Assert.state(!DictTypeEnum.SYSTEM.getType().equals(Dict.getSystemFlag()),
				MsgUtils.getMessage(ErrorCodes.SYS_DICT_UPDATE_SYSTEM));
		this.updateById(dict);
	}

	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public void clearDictCache() {

	}

}
