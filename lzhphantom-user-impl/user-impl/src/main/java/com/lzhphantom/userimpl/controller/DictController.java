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

package com.lzhphantom.userimpl.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.CacheConstants;
import com.lzhphantom.log.annotation.LzhphantomLog;
import com.lzhphantom.user.login.entity.Dict;
import com.lzhphantom.user.login.entity.DictItem;
import com.lzhphantom.userimpl.service.DictItemService;
import com.lzhphantom.userimpl.service.DictService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author lzhphantom
 * @since 2019-03-19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dict")
@Tag(name = "字典管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DictController {

	private final DictItemService sysDictItemService;

	private final DictService sysDictService;

	/**
	 * 通过ID查询字典信息
	 * @param id ID
	 * @return 字典信息
	 */
	@GetMapping("/{id:\\d+}")
	public LzhphantomResult<Dict> getById(@PathVariable Long id) {
		return LzhphantomResult.ok(sysDictService.getById(id));
	}

	/**
	 * 分页查询字典信息
	 * @param page 分页对象
	 * @return 分页对象
	 */
	@GetMapping("/page")
	public LzhphantomResult<IPage<Dict>> getDictPage(Page page, Dict sysDict) {
		return LzhphantomResult.ok(sysDictService.page(page, Wrappers.<Dict>lambdaQuery()
				.like(StrUtil.isNotBlank(sysDict.getDictKey()), Dict::getDictKey, sysDict.getDictKey())));
	}

	/**
	 * 通过字典类型查找字典
	 * @param type 类型
	 * @return 同类型字典
	 */
	@GetMapping("/key/{key}")
	@Cacheable(value = CacheConstants.DICT_DETAILS, key = "#key")
	public LzhphantomResult<List<DictItem>> getDictByKey(@PathVariable String key) {
		return LzhphantomResult.ok(sysDictItemService.list(Wrappers.<DictItem>query().lambda().eq(DictItem::getDictKey, key)));
	}

	/**
	 * 添加字典
	 * @param sysDict 字典信息
	 * @return success、false
	 */
	@LzhphantomLog("添加字典")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_dict_add')")
	public LzhphantomResult<Boolean> save(@Valid @RequestBody Dict sysDict) {
		return LzhphantomResult.ok(sysDictService.save(sysDict));
	}

	/**
	 * 删除字典，并且清除字典缓存
	 * @param id ID
	 * @return R
	 */
	@LzhphantomLog("删除字典")
	@DeleteMapping("/{id:\\d+}")
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	public LzhphantomResult removeById(@PathVariable Long id) {
		sysDictService.removeDict(id);
		return LzhphantomResult.ok();
	}

	/**
	 * 修改字典
	 * @param sysDict 字典信息
	 * @return success/false
	 */
	@PutMapping
	@LzhphantomLog("修改字典")
	@PreAuthorize("@pms.hasPermission('sys_dict_edit')")
	public LzhphantomResult updateById(@Valid @RequestBody Dict sysDict) {
		sysDictService.updateDict(sysDict);
		return LzhphantomResult.ok();
	}

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param sysDictItem 字典项
	 * @return
	 */
	@GetMapping("/item/page")
	public LzhphantomResult<IPage<DictItem>> getDictItemPage(Page page, DictItem sysDictItem) {
		return LzhphantomResult.ok(sysDictItemService.page(page, Wrappers.query(sysDictItem)));
	}

	/**
	 * 通过id查询字典项
	 * @param id id
	 * @return R
	 */
	@GetMapping("/item/{id:\\d+}")
	public LzhphantomResult<DictItem> getDictItemById(@PathVariable("id") Long id) {
		return LzhphantomResult.ok(sysDictItemService.getById(id));
	}

	/**
	 * 新增字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@LzhphantomLog("新增字典项")
	@PostMapping("/item")
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public LzhphantomResult<Boolean> save(@RequestBody DictItem sysDictItem) {
		return LzhphantomResult.ok(sysDictItemService.save(sysDictItem));
	}

	/**
	 * 修改字典项
	 * @param sysDictItem 字典项
	 * @return R
	 */
	@LzhphantomLog("修改字典项")
	@PutMapping("/item")
	public LzhphantomResult updateById(@RequestBody DictItem sysDictItem) {
		sysDictItemService.updateDictItem(sysDictItem);
		return LzhphantomResult.ok();
	}

	/**
	 * 通过id删除字典项
	 * @param id id
	 * @return R
	 */
	@LzhphantomLog("删除字典项")
	@DeleteMapping("/item/{id:\\d+}")
	public LzhphantomResult removeDictItemById(@PathVariable Long id) {
		sysDictItemService.removeDictItem(id);
		return LzhphantomResult.ok();
	}

	@LzhphantomLog("清除字典缓存")
	@DeleteMapping("/cache")
	@PreAuthorize("@pms.hasPermission('sys_dict_del')")
	public LzhphantomResult clearDictCache() {
		sysDictService.clearDictCache();
		return LzhphantomResult.ok();
	}

}
