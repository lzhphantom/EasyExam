package com.lzhphantom.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lzhphantom
 * <p>
 * 日志查询传输对象
 */
@Data
@Schema(description = "日志查询对象")
public class SystemLogDTO {
    /**
     * 查询日志类型
     */
    @Schema(description = "日志类型")
    private String type;

    /**
     * 创建时间区间 [开始时间，结束时间]
     */
    @Schema(description = "创建时间区间")
    private LocalDateTime[] createTime;

    /**
     * 请求IP
     */
    private String remoteAddr;
}
