package com.lzhphantom.codegen.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.codegen.entity.GenConfig;
import com.lzhphantom.codegen.service.GeneratorService;
import com.lzhphantom.core.common.util.LzhphantomResult;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器
 *
 * @author lzhphantom
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
@Tag(name = "代码生成模块")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class GeneratorController {

    private final GeneratorService generatorService;

    /**
     * 列表
     * @param tableName 参数集
     * @param dsName 数据源编号
     * @return 数据库表
     */
    @GetMapping("/page")
    public LzhphantomResult<IPage<List<Map<String, Object>>>> getPage(Page page, String tableName, String dsName) {
        return LzhphantomResult.ok(generatorService.getPage(page, tableName, dsName));
    }

    /**
     * 预览代码
     * @param genConfig 数据表配置
     * @return
     */
    @GetMapping("/preview")
    public LzhphantomResult<Map<String, String>> previewCode(GenConfig genConfig) {
        return LzhphantomResult.ok(generatorService.previewCode(genConfig));
    }

    /**
     * 生成代码
     */
    @SneakyThrows
    @PostMapping("/code")
    public void generatorCode(@RequestBody GenConfig genConfig, HttpServletResponse response) {
        byte[] data = generatorService.generatorCode(genConfig);
        response.reset();
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                String.format("attachment; filename=%s.zip", genConfig.getTableName()));
        response.addHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(data.length));
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), Boolean.TRUE, data);
    }

}
