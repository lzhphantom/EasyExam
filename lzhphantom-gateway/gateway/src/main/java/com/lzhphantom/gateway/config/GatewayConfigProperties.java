package com.lzhphantom.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.List;

/**
 * 网关配置文件
 * @author lzhphantom
 */
@Data
@RefreshScope
@ConfigurationProperties("gateway")
public class GatewayConfigProperties {
    /**
     * 网关解密登录前端密码 秘钥 {@link PasswordDecoderFilter}
     */
    private String encodeKey;
    private List<String> ignoreClients;
}
