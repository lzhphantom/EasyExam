package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author lzhphantom
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.AUTH_SERVICE)
public interface RemoteTokenService {
    /**
     * 分页查询token 信息
     * @param params 分页参数
     * @return page
     */
    @PostMapping(value = "/token/page", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult getTokenPage(@RequestBody Map<String, Object> params);

    /**
     * 删除token
     * @param token token
     * @return
     */
    @DeleteMapping(value = "/token/{token}", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<Boolean> removeToken(@PathVariable("token") String token);
}
