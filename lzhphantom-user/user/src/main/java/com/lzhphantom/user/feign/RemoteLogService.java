package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.constant.ServiceNameConstants;
import com.lzhphantom.user.login.entity.SystemLog;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author lzhphantom
 */
@FeignClient(contextId = "remoteLogService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteLogService {
    /**
     * 保存日志
     *
     * @param sysLog 日志实体
     * @return succes、false
     */
    @PostMapping(value = "/log", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<Boolean> saveLog(@RequestBody SystemLog sysLog);
}
