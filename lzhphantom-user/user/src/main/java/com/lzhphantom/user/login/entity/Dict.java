package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.List;

/**
 * 字典表
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class Dict extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 类型
     */
    @Column(name = "dictKey")
    private String dictKey;

    /**
     * 描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 是否是系统内置
     */
    @Column(name = "systemFlag")
    private String systemFlag;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "dictId",referencedColumnName = "id")
    private List<DictItem> dictItems;
}
