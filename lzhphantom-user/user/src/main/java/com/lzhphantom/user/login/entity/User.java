package com.lzhphantom.user.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.Set;

@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username", nullable = false)
    private String username;

    /**
     * 密码
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * 随机盐
     */
    @JsonIgnore
    @Column(name = "randomSalt", nullable = false)
    private String salt;

    /**
     * 邮箱
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * 手机号
     */
    @Column(name = "phone", nullable = false)
    private String phone;

    /**
     * 锁定标记
     */
    @Column(name = "lockFlag", nullable = false)
    private String lockFlag;
    /**
     * 头像
     */
    @Column(name = "avatarAddress", nullable = false)
    private String avatar;
    /**
     * 部门
     */
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Dept dept;

    /**
     * 岗位
     */
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "LZHPHANTOM_USER_POST"
    ,joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")}
    ,inverseJoinColumns = {@JoinColumn(name = "POST_ID",referencedColumnName = "ID")})
    private Set<Post> posts;

    /**
     * 角色
     */
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name = "LZHPHANTOM_USER_ROLE"
            ,joinColumns = {@JoinColumn(name = "USER_ID",referencedColumnName = "ID")}
            ,inverseJoinColumns = {@JoinColumn(name = "ROLE_ID",referencedColumnName = "ID")})
    private Set<Role> roles;

}
