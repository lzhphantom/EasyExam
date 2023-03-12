package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.constant.ServiceNameConstants;
import com.lzhphantom.user.login.entity.OauthClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lzhphantom
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteClientDetailsService {
    /**
     * 通过clientId 查询客户端信息
     *
     * @param clientId 用户名
     * @return R
     */
    @GetMapping(value = "/client/getClientDetailsById/{clientId}", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<OauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId);

    /**
     * 查询全部客户端
     *
     * @return R
     */
    @GetMapping(value = "/client/list", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<List<OauthClientDetails>> listClientDetails();
}
