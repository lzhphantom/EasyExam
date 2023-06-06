package com.lzhphantom.codegen.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.codegen.entity.GenDatasourceConf;
import com.lzhphantom.codegen.service.GenDatasourceConfService;
import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.log.annotation.LzhphantomLog;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 *
 * @author lzhphantom
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dsconf")
@Tag(name = "数据源管理模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GenDsConfController {

    private final GenDatasourceConfService datasourceConfService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param datasourceConf 数据源表
     * @return
     */
    @GetMapping("/page")
    public LzhphantomResult<IPage<GenDatasourceConf>> getSysDatasourceConfPage(Page page, GenDatasourceConf datasourceConf) {
        return LzhphantomResult.ok(datasourceConfService.page(page, Wrappers.query(datasourceConf)));
    }

    /**
     * 查询全部数据源
     * @return
     */
    @GetMapping("/list")
    public LzhphantomResult<List<GenDatasourceConf>> list() {
        return LzhphantomResult.ok(datasourceConfService.list());
    }

    /**
     * 通过id查询数据源表
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    public LzhphantomResult<GenDatasourceConf> getById(@PathVariable("id") Long id) {
        return LzhphantomResult.ok(datasourceConfService.getById(id));
    }

    /**
     * 新增数据源表
     * @param datasourceConf 数据源表
     * @return R
     */
    @LzhphantomLog("新增数据源表")
    @PostMapping
    public LzhphantomResult<Boolean> save(@RequestBody GenDatasourceConf datasourceConf) {
        return LzhphantomResult.ok(datasourceConfService.saveDsByEnc(datasourceConf));
    }

    /**
     * 修改数据源表
     * @param conf 数据源表
     * @return R
     */
    @LzhphantomLog("修改数据源表")
    @PutMapping
    public LzhphantomResult<Boolean> updateById(@RequestBody GenDatasourceConf conf) {
        return LzhphantomResult.ok(datasourceConfService.updateDsByEnc(conf));
    }

    /**
     * 通过id删除数据源表
     * @param id id
     * @return R
     */
    @LzhphantomLog("删除数据源表")
    @DeleteMapping("/{id}")
    public LzhphantomResult<Boolean> removeById(@PathVariable Long id) {
        return LzhphantomResult.ok(datasourceConfService.removeByDsId(id));
    }

}