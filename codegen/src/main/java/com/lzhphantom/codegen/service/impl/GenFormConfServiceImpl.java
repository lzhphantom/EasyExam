package com.lzhphantom.codegen.service.impl;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzhphantom.codegen.entity.ColumnEntity;
import com.lzhphantom.codegen.entity.GenFormConf;
import com.lzhphantom.codegen.mapper.GenFormConfMapper;
import com.lzhphantom.codegen.mapper.GeneratorMapper;
import com.lzhphantom.codegen.service.GenCodeService;
import com.lzhphantom.codegen.service.GenFormConfService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 表单管理
 *
 * @author lzhphantom
 */
@Service
@RequiredArgsConstructor
public class GenFormConfServiceImpl extends ServiceImpl<GenFormConfMapper, GenFormConf> implements GenFormConfService {

    private final GeneratorMapper generatorMapper;

    private final GenCodeService avue;

    /**
     * 1. 根据数据源、表名称，查询已配置表单信息 2. 不存在调用模板生成
     * @param dsName 数据源ID
     * @param tableName 表名称
     * @return
     */
    @Override
    @SneakyThrows
    public String getForm(String dsName, String tableName) {
        GenFormConf form = getOne(Wrappers.<GenFormConf>lambdaQuery()
                .eq(GenFormConf::getTableName, tableName)
                .orderByDesc(GenFormConf::getCreateTime), false);

        if (form != null) {
            return form.getFormInfo();
        }

        List<Map<String, String>> columns = generatorMapper.queryColumns(tableName, dsName);
        // 设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", ClasspathResourceLoader.class.getName());
        Velocity.init(prop);
        Template template = Velocity.getTemplate("template/avue/crud.js.vm", CharsetUtil.UTF_8);
        VelocityContext context = new VelocityContext();

        List<ColumnEntity> columnList = Lists.newArrayList();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setLowerAttrName(StringUtils.uncapitalize(avue.columnToJava(column.get("columnName"))));
            columnList.add(columnEntity);
        }
        context.put("columns", columnList);
        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return StrUtil.trim(StrUtil.removePrefix(writer.toString(), "export const tableOption ="));
    }

}
