package com.lzhphantom.codegen.service.impl.temp;

import com.lzhphantom.codegen.entity.GenConfig;
import com.lzhphantom.codegen.service.GenCodeService;
import com.lzhphantom.core.constant.CommonConstants;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author lzhphantom
 */
@Service("element")
public class EleGenCodeServiceImpl implements GenCodeService {

    /**
     * 注入支持的模板列表
     * @param config 用户输入
     * @return ListString
     */
    @Override
    public List<String> getTemplates(GenConfig config) {
        List<String> templates = GenCodeService.super.getTemplates(config);
        templates.add("template/element/index.vue.vm");
        templates.add("template/element/form.vue.vm");
        templates.add("template/avue/api.js.vm");
        return templates;
    }

    /**
     * 获取文件名
     * @param template
     * @param className
     * @param packageName
     * @param moduleName
     */
    @Override
    public String getFileName(String template, String className, String packageName, String moduleName) {
        if (template.contains("element/index.vue.vm")) {
            return CommonConstants.FRONT_END_PROJECT + File.separator + "src" + File.separator + "views"
                    + File.separator + moduleName + File.separator + className.toLowerCase() + File.separator
                    + "index.vue";
        }

        if (template.contains("element/form.vue.vm")) {
            return CommonConstants.FRONT_END_PROJECT + File.separator + "src" + File.separator + "views"
                    + File.separator + moduleName + File.separator + className.toLowerCase() + File.separator
                    + className.toLowerCase() + "-form.vue";
        }

        if (template.contains("avue/api.js.vm")) {
            return CommonConstants.FRONT_END_PROJECT + File.separator + "src" + File.separator + "api" + File.separator
                    + className.toLowerCase() + ".js";
        }

        return GenCodeService.super.getFileName(template, className, packageName, moduleName);
    }

}
