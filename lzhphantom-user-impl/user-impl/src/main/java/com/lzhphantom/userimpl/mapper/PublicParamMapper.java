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
import com.lzhphantom.user.login.entity.PublicParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
@Mapper
public interface PublicParamMapper extends BaseMapper<PublicParam> {

}
