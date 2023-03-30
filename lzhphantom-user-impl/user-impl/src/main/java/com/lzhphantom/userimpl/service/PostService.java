/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
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
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.lzhphantom.userimpl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.user.login.entity.Post;
import com.lzhphantom.user.vo.PostExcelVO;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * 岗位管理 服务类
 *
 * @author fxz
 * @date 2022-03-15 17:18:40
 */
public interface PostService extends IService<Post> {

	/**
	 * 导入岗位
	 * @param excelVOList 岗位列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	LzhphantomResult importPost(List<PostExcelVO> excelVOList, BindingResult bindingResult);

	/**
	 * 导出excel 表格
	 * @return
	 */
	List<PostExcelVO> listPost();

}
