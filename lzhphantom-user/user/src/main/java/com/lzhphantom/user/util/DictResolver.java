package com.lzhphantom.user.util;

import cn.hutool.core.util.StrUtil;
import com.lzhphantom.core.common.util.SpringContextHolder;
import com.lzhphantom.user.feign.RemoteDictService;
import com.lzhphantom.user.login.entity.DictItem;
import io.jsonwebtoken.lang.Assert;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author lzhphantom
 * 字典解析器
 */
@UtilityClass
public class DictResolver {
    /**
     * 根据字典类型获取所有字典项
     * @param type 字典类型
     * @return 字典数据项集合
     */
    public List<DictItem> getDictItemsByType(String type) {
        Assert.isTrue(StringUtils.isNotBlank(type), "参数不合法");

        RemoteDictService remoteDictService = SpringContextHolder.getBean(RemoteDictService.class);

        return remoteDictService.getDictByType(type).getData();
    }

    /**
     * 根据字典类型以及字典项字典值获取字典标签
     * @param type 字典类型
     * @param itemValue 字典项字典值
     * @return 字典项标签值
     */
    public String getDictItemLabel(String type, String itemValue) {
        Assert.isTrue(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(itemValue), "参数不合法");

        DictItem DictItem = getDictItemByItemValue(type, itemValue);

        return ObjectUtils.isNotEmpty(DictItem) ? DictItem.getLabel() : StrUtil.EMPTY;
    }

    /**
     * 根据字典类型以及字典标签获取字典值
     * @param type 字典类型
     * @param itemLabel 字典数据标签
     * @return 字典数据项值
     */
    public String getDictItemValue(String type, String itemLabel) {
        Assert.isTrue(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(itemLabel), "参数不合法");

        DictItem DictItem = getDictItemByItemLabel(type, itemLabel);

        return ObjectUtils.isNotEmpty(DictItem) ? DictItem.getValue() : StrUtil.EMPTY;
    }

    /**
     * 根据字典类型以及字典值获取字典项
     * @param type 字典类型
     * @param itemValue 字典数据值
     * @return 字典数据项
     */
    public DictItem getDictItemByItemValue(String type, String itemValue) {
        Assert.isTrue(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(itemValue), "参数不合法");

        List<DictItem> dictItemList = getDictItemsByType(type);

        if (CollectionUtils.isNotEmpty(dictItemList)) {
            return dictItemList.stream().filter(item -> itemValue.equals(item.getValue())).findFirst().orElse(null);
        }

        return null;
    }

    /**
     * 根据字典类型以及字典标签获取字典项
     * @param type 字典类型
     * @param itemLabel 字典数据项标签
     * @return 字典数据项
     */
    public DictItem getDictItemByItemLabel(String type, String itemLabel) {
        Assert.isTrue(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(itemLabel), "参数不合法");

        List<DictItem> dictItemList = getDictItemsByType(type);

        if (CollectionUtils.isNotEmpty(dictItemList)) {
            return dictItemList.stream().filter(item -> itemLabel.equals(item.getLabel())).findFirst().orElse(null);
        }

        return null;
    }
}
