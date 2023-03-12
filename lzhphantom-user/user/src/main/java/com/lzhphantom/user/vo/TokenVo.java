package com.lzhphantom.user.vo;

import lombok.Data;

/**
 * 前端展示令牌管理
 *
 * @author lzhphantom
 */
@Data
public class TokenVo {

    private String id;

    private Long userId;

    private String clientId;

    private String username;

    private String accessToken;

    private String issuedAt;

    private String expiresAt;

}
