package com.lzhphantom.codegen.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzhphantom.codegen.entity.GenConfig;

import java.util.List;
import java.util.Map;

/**
 * @author lzhphantom
 */
public interface GeneratorService {

    /**
     * 生成代码
     * @param tableNames 表名称
     * @return
     */
    byte[] generatorCode(GenConfig tableNames);

    /**
     * 分页查询表
     * @param page 分页信息
     * @param tableName 表名
     * @param name 数据源ID
     * @return
     */
    IPage<List<Map<String, Object>>> getPage(Page page, String tableName, String name);

    /**
     * 预览代码
     * @param genConfig 查询条件
     * @return
     */
    Map<String, String> previewCode(GenConfig genConfig);

}
