package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author lzhphantom
 * <p>
 * 查询参数相关
 */
@FeignClient(contextId = "remoteParamService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteParamService {
    /**
     * 通过key 查询参数配置
     *
     * @param key key
     * @return
     */
    @GetMapping(value = "/param/publicValue/{key}", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<String> getByKey(@PathVariable("key") String key);
}
