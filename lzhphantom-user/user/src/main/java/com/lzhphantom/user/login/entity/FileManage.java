package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lzhphantom.mybatis.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件管理
 *
 * @author lzhphantom
 */
/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileManage extends BaseEntity {
    /**
     * 主键ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 原文件名
     */
    private String original;

    /**
     * 容器名称
     */
    private String bucketName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 删除标识：1-删除，0-正常
     */
    @TableLogic
    private Integer delFlag;
}
