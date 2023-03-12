package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

/**
 * 字典项
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class DictItem extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属字典类id
     */
    @Column(name = "dictId")
    private Long dictId;

    /**
     * 所属字典类id
     */
    @Column(name = "dictKey")
    private String dictKey;

    /**
     * 数据值
     */
    @Column(name = "value")
    private String value;

    /**
     * 标签名
     */
    @Column(name = "label")
    private String label;

    /**
     * 类型
     */
    @Column(name = "type")
    private String type;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 排序（升序）
     */
    @Column(name = "sortOrder")
    private Integer sortOrder;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;
}
