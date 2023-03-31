/*
 *    Copyright (c) 2018-2025, lzhphantom All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lzhphantom (wangiegie@gmail.com)
 */

package com.lzhphantom.userimpl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzhphantom.user.login.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 岗位管理表 mapper接口
 *
 * @author fxz
 * @date 2022-03-15 17:18:40
 */
@Mapper
public interface PostMapper extends BaseMapper<Post> {

	/**
	 * 通过用户ID，查询岗位信息
	 * @param userId 用户id
	 * @return 岗位信息
	 */
	List<Post> listPostsByUserId(Long userId);

}
