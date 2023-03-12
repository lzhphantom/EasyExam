package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.Set;

/**
 * 部门管理
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_DEPT")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class Dept extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 部门名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 排序
     */
    @Column(name = "sortOrder", nullable = false)
    private Integer sortOrder;

    /**
     * 父级部门id
     */
    @Column(name = "parentId")
    private Long parentId;

}
