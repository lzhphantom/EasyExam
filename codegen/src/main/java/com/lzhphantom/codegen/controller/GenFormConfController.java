package com.lzhphantom.codegen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.codegen.entity.GenFormConf;
import com.lzhphantom.codegen.service.GenFormConfService;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 表单管理
 *
 * @author lzhphantom
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
@Tag(name = "表单管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GenFormConfController {

    private final GenFormConfService genRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param formConf 生成记录
     * @return
     */
    @Operation(summary = "分页查询", description = "分页查询")
    @GetMapping("/page")
    public LzhphantomResult<IPage<GenFormConf>> getGenFormConfPage(Page page, GenFormConf formConf) {
        return LzhphantomResult.ok(genRecordService.page(page, Wrappers.query(formConf)));
    }

    /**
     * 通过id查询生成记录
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id查询", description = "通过id查询")
    @GetMapping("/{id}")
    public LzhphantomResult<GenFormConf> getById(@PathVariable("id") Integer id) {
        return LzhphantomResult.ok(genRecordService.getById(id));
    }

    /**
     * 通过id查询生成记录
     * @param dsName 数据源ID
     * @param tableName tableName
     * @return R
     */
    @Operation(summary = "通过tableName查询表单信息")
    @GetMapping("/info")
    public LzhphantomResult<String> form(String dsName, String tableName) {
        return LzhphantomResult.ok(genRecordService.getForm(dsName, tableName));
    }

    /**
     * 新增生成记录
     * @param formConf 生成记录
     * @return R
     */
    @Operation(summary = "新增生成记录", description = "新增生成记录")
    @LzhphantomLog("新增生成记录")
    @PostMapping
    @PreAuthorize("@pms.hasPermission('gen_form_add')")
    public LzhphantomResult<Boolean> save(@RequestBody GenFormConf formConf) {
        return LzhphantomResult.ok(genRecordService.save(formConf));
    }

    /**
     * 通过id删除生成记录
     * @param id id
     * @return R
     */
    @Operation(summary = "通过id删除生成记录", description = "通过id删除生成记录")
    @LzhphantomLog("通过id删除生成记录")
    @DeleteMapping("/{id}")
    @PreAuthorize("@pms.hasPermission('gen_form_del')")
    public LzhphantomResult<Boolean> removeById(@PathVariable Long id) {
        return LzhphantomResult.ok(genRecordService.removeById(id));
    }

}
