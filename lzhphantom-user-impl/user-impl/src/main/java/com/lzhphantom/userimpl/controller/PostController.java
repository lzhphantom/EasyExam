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

package com.lzhphantom.userimpl.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.user.login.entity.Post;
import com.lzhphantom.user.vo.PostExcelVO;
import com.lzhphantom.userimpl.service.PostService;
import com.pig4cloud.plugin.excel.annotation.RequestExcel;
import com.pig4cloud.plugin.excel.annotation.ResponseExcel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fxz
 * @date 2022-03-15 17:18:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
@Tag(name = "岗位管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class PostController {

	private final PostService sysPostService;

	/**
	 * 获取岗位列表
	 * @return 岗位列表
	 */
	@GetMapping("/list")
	public LzhphantomResult<List<Post>> listPosts() {
		return LzhphantomResult.ok(sysPostService.list(Wrappers.emptyWrapper()));
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @return
	 */
	@Operation(summary = "分页查询", description = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('sys_post_get')")
	public LzhphantomResult<Page> getPostPage(Page page) {
		return LzhphantomResult.ok(sysPostService.page(page, Wrappers.<Post>lambdaQuery().orderByAsc(Post::getPostSort)));
	}

	/**
	 * 通过id查询岗位信息表
	 * @param postId id
	 * @return R
	 */
	@Operation(summary = "通过id查询", description = "通过id查询")
	@GetMapping("/{postId}")
	@PreAuthorize("@pms.hasPermission('sys_post_get')")
	public LzhphantomResult getById(@PathVariable("postId") Long postId) {
		return LzhphantomResult.ok(sysPostService.getById(postId));
	}

	/**
	 * 新增岗位信息表
	 * @param sysPost 岗位信息表
	 * @return R
	 */
	@Operation(summary = "新增岗位信息表", description = "新增岗位信息表")
	@LzhphantomLog("新增岗位信息表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_post_add')")
	public LzhphantomResult save(@RequestBody Post sysPost) {
		return LzhphantomResult.ok(sysPostService.save(sysPost));
	}

	/**
	 * 修改岗位信息表
	 * @param sysPost 岗位信息表
	 * @return R
	 */
	@Operation(summary = "修改岗位信息表", description = "修改岗位信息表")
	@LzhphantomLog("修改岗位信息表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_post_edit')")
	public LzhphantomResult updateById(@RequestBody Post sysPost) {
		return LzhphantomResult.ok(sysPostService.updateById(sysPost));
	}

	/**
	 * 通过id删除岗位信息表
	 * @param postId id
	 * @return R
	 */
	@Operation(summary = "通过id删除岗位信息表", description = "通过id删除岗位信息表")
	@LzhphantomLog("通过id删除岗位信息表")
	@DeleteMapping("/{postId}")
	@PreAuthorize("@pms.hasPermission('sys_post_del')")
	public LzhphantomResult removeById(@PathVariable Long postId) {
		return LzhphantomResult.ok(sysPostService.removeById(postId));
	}

	/**
	 * 导出excel 表格
	 * @return
	 */
	@ResponseExcel
	@GetMapping("/export")
	@PreAuthorize("@pms.hasPermission('sys_post_import_export')")
	public List<PostExcelVO> export() {
		return sysPostService.listPost();
	}

	/**
	 * 导入岗位
	 * @param excelVOList 岗位列表
	 * @param bindingResult 错误信息列表
	 * @return ok fail
	 */
	@PostMapping("/import")
	@PreAuthorize("@pms.hasPermission('sys_post_import_export')")
	public LzhphantomResult importRole(@RequestExcel List<PostExcelVO> excelVOList, BindingResult bindingResult) {
		return sysPostService.importPost(excelVOList, bindingResult);
	}

}
