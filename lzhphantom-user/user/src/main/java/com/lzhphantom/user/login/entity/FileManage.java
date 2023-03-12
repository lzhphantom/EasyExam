package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

/**
 * 文件管理
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class FileManage extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 文件名
     */
    @Column(name = "fileName")
    private String fileName;

    /**
     * 原文件名
     */
    @Column(name = "original")
    private String original;

    /**
     * 容器名称
     */
    @Column(name = "bucketName")
    private String bucketName;

    /**
     * 文件类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 文件大小
     */
    @Column(name = "fileSize")
    private Long fileSize;
}
