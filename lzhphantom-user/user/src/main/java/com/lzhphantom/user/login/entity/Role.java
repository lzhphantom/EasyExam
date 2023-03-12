package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.Set;

/**
 * 角色表
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(name = "roleName", nullable = false)
    private String roleName;

    /**
     * 角色标识
     */
    @Column(name = "roleCode", nullable = false)
    private String roleCode;

    /**
     * 角色描述
     */
    @Column(name = "roleDesc", nullable = false)
    private String roleDesc;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private Set<User> users;

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "LZHPHANTOM_ROLE_MENU"
            ,joinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")}
            ,inverseJoinColumns = {@JoinColumn(name = "MENU_ID",referencedColumnName = "ID")})
    private Set<Menu> menus;
}
