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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.user.login.entity.OauthClientDetails;
import com.lzhphantom.userimpl.mapper.OauthClientDetailsMapper;
import com.lzhphantom.userimpl.service.OauthClientDetailsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails>
		implements OauthClientDetailsService {

	/**
	 * 通过ID删除客户端
	 * @param id
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#id")
	public Boolean removeClientDetailsById(String id) {
		return this.removeById(id);
	}

	/**
	 * 根据客户端信息
	 * @param clientDetails
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetails.clientId")
	public Boolean updateClientDetailsById(OauthClientDetails clientDetails) {
		return this.updateById(clientDetails);
	}

	/**
	 * 清除客户端缓存
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, allEntries = true)
	public void clearClientCache() {

	}

}
