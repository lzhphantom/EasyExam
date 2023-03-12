package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.SecurityConstants;
import com.lzhphantom.core.constant.ServiceNameConstants;
import com.lzhphantom.user.dto.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author lzhphantom
 */
@FeignClient(contextId = "remoteUserService",value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteUserService {
    /**
     * 通过用户名查询用户、角色信息
     * @param username 用户名
     * @return R
     */
    @GetMapping(value = "/user/info/{username}", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<UserInfo> info(@PathVariable("username") String username);

    /**
     * 通过手机号码查询用户、角色信息
     * @param phone 手机号码
     * @param from 调用标志
     * @return R
     */
    @GetMapping(value = "/app/info/{phone}", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<UserInfo> infoByMobile(@PathVariable("phone") String phone);

    /**
     * 根据部门id，查询对应的用户 id 集合
     * @param deptIds 部门id 集合
     * @param from 调用标志
     * @return 用户 id 集合
     */
    @GetMapping(value = "/user/ids", headers = SecurityConstants.HEADER_FROM_IN)
    LzhphantomResult<List<Long>> listUserIdByDeptIds(@RequestParam("deptIds") Set<Long> deptIds);
}
