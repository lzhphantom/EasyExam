package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

/**
 * 公共参数配置
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class PublicParam extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公共参数名称
     */
    @Column(name = "publicName", nullable = false)
    private String publicName;

    /**
     * 公共参数地址值,英文大写+下划线
     * PIGX_PUBLIC_KEY
     */
    @Column(name = "publicKey", nullable = false)
    private String publicKey;

    /**
     * 值
     * 999
     */
    @Column(name = "publicValue")
    private String publicValue;

    /**
     * 状态（1有效；2无效；）
     */
    @Column(name = "keyStatus")
    private String ketStatus;

    /**
     * 公共参数编码
     * ^(PIG|PIGX)$
     */
    @Column(name = "validateCode")
    private String validateCode;

    /**
     * 是否是系统内置
     */
    @Column(name = "systemFlag")
    private String systemFlag;

    /**
     * 配置类型：0-默认；1-检索；2-原文；3-报表；4-安全；5-文档；6-消息；9-其他
     */
    @Column(name = "publicType")
    private String publicType;
}
