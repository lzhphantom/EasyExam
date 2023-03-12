package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.Set;

/**
 * 菜单权限表
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 菜单名称
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 菜单权限标识
     */
    @Column(name = "permission")
    private String permission;

    /**
     * 父菜单ID
     */
    @Column(name = "parentId", nullable = false)
    private Long parentId;

    /**
     * 图标
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 前端URL
     */
    @Column(name = "path")
    private String path;

    /**
     * 排序值
     */
    @Column(name = "sortOrder")
    private Integer sortOrder;

    /**
     * 菜单类型 （0菜单 1按钮）
     */
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 路由缓冲
     */
    @Column(name = "keepAlive")
    private String keepAlive;

    @ManyToMany(mappedBy = "menus", fetch = FetchType.LAZY)
    private Set<Role> roles;
}
