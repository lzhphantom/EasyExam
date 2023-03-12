package com.lzhphantom.user.feign;

import com.lzhphantom.core.common.util.LzhphantomResult;
import com.lzhphantom.core.constant.ServiceNameConstants;
import com.lzhphantom.user.login.entity.DictItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author lzhphantom
 * <p>
 * 查询字典相关
 */
@FeignClient(contextId = "remoteDictService", value = ServiceNameConstants.UMPS_SERVICE)
public interface RemoteDictService {
    /**
     * 通过字典类型查找字典
     *
     * @param type 字典类型
     * @return 同类型字典
     */
    @GetMapping("/dict/type/{type}")
    LzhphantomResult<List<DictItem>> getDictByType(@PathVariable("type") String type);
}
