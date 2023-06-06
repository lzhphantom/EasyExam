package com.lzhphantom.codegen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzhphantom.codegen.entity.GenFormConf;

/**
 * 表单管理
 *
 * @author lzhphantom
 */
public interface GenFormConfService extends IService<GenFormConf> {

    /**
     * 获取表单信息
     * @param dsName 数据源ID
     * @param tableName 表名称
     * @return
     */
    String getForm(String dsName, String tableName);

}
