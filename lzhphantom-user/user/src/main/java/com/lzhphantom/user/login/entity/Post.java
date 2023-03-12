package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

import java.util.Set;

/**
 * 岗位表
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 岗位编码
     */
    @Column(name = "postCode", nullable = false)
    private String postCode;

    /**
     * 岗位名称
     */
    @Column(name = "postName", nullable = false)
    private String postName;

    /**
     * 岗位排序
     */
    @Column(name = "postSort")
    private Integer postSort;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;

    @ManyToMany(mappedBy = "posts", fetch = FetchType.LAZY)
    private Set<User> users;
}
