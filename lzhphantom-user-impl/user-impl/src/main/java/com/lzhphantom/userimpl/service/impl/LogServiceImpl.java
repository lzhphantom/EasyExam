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

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.user.dto.SystemLogDTO;
import com.lzhphantom.user.login.entity.SystemLog;
import com.lzhphantom.userimpl.mapper.LogMapper;
import com.lzhphantom.userimpl.service.LogService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 日志表 服务实现类
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, SystemLog> implements LogService {

	@Override
	public Page getLogByPage(Page page, SystemLogDTO Log) {
		return baseMapper.selectPage(page, buildQueryWrapper(Log));
	}

	/**
	 * 列表查询日志
	 * @param Log 查询条件
	 * @return List
	 */
	@Override
	public List getLogList(SystemLogDTO Log) {
		return baseMapper.selectList(buildQueryWrapper(Log));
	}

	/**
	 * 构建查询的 wrapper
	 * @param Log 查询条件
	 * @return LambdaQueryWrapper
	 */
	private LambdaQueryWrapper buildQueryWrapper(SystemLogDTO Log) {
		LambdaQueryWrapper<SystemLog> wrapper = Wrappers.<SystemLog>lambdaQuery()
				.eq(StrUtil.isNotBlank(Log.getType()), SystemLog::getType, Log.getType())
				.like(StrUtil.isNotBlank(Log.getRemoteAddr()), SystemLog::getRemoteAddr, Log.getRemoteAddr());

		if (ArrayUtil.isNotEmpty(Log.getCreateTime())) {
			wrapper.ge(SystemLog::getCreateDt, Log.getCreateTime()[0]).le(SystemLog::getCreateDt,
					Log.getCreateTime()[1]);
		}

		return wrapper;
	}

}
