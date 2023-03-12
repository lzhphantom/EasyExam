package com.lzhphantom.user.login.entity;

import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

/**
 * 客户端信息
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class OauthClientDetails extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "clientId")
    private String clientId;
    /**
     * 客户端密钥
     */
    @Column(name = "clientSecret", nullable = false)
    private String clientSecret;

    /**
     * 资源ID
     */
    @Column(name = "resourceIds")
    private String resourceIds;

    /**
     * 作用域
     */
    @Column(name = "scope", nullable = false)
    private String scope;

    /**
     * 授权方式（A,B,C）
     */
    @Column(name = "authorizedGrantTypes")
    private String authorizedGrantTypes;

    /**
     * 回调地址
     */
    @Column(name = "webServerRedirectUri")
    private String webServerRedirectUri;

    /**
     * 权限
     */
    @Column(name = "authorities")
    private String authorities;

    /**
     * 请求令牌有效时间
     */
    @Column(name = "accessTokenValidity")
    private Integer accessTokenValidity;

    /**
     * 刷新令牌有效时间
     */
    @Column(name = "refreshTokenValidity")
    private Integer refreshTokenValidity;

    /**
     * 扩展信息
     */
    @Column(name = "additionalInformation")
    private String additionalInformation;

    /**
     * 是否自动放行
     */
    @Column(name = "autoapprove")
    private String autoapprove;
}
