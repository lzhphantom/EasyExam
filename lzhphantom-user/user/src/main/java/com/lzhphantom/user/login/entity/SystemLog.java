package com.lzhphantom.user.login.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lzhphantom.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 日志表
 *
 * @author lzhphantom
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SystemLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ExcelProperty("日志编号")
    @Schema(description = "日志编号")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 日志类型
     */
    @NotBlank(message = "日志类型不能为空")
    @ExcelProperty("日志类型（0-正常 9-错误）")
    @Schema(description = "日志类型")
    private String type;

    /**
     * 日志标题
     */
    @NotBlank(message = "日志标题不能为空")
    @ExcelProperty("日志标题")
    @Schema(description = "日志标题")
    private String title;

    /**
     * 操作IP地址
     */
    @ExcelProperty("IP")
    @Schema(description = "操作ip地址")
    private String remoteAddr;

    /**
     * 用户浏览器
     */
    @ExcelProperty("浏览器类型")
    @Schema(description = "用户代理")
    private String userAgent;

    /**
     * 请求URI
     */
    @ExcelProperty("请求URI")
    @Schema(description = "请求uri")
    private String requestUri;

    /**
     * 操作方式
     */
    @ExcelProperty("操作方式")
    @Schema(description = "操作方式")
    private String method;

    /**
     * 操作提交的数据
     */
    @ExcelProperty("请求参数")
    @Schema(description = "数据")
    private String params;

    /**
     * 执行时间
     */
    @ExcelProperty("方法执行时间")
    @Schema(description = "方法执行时间")
    private Long time;

    /**
     * 异常信息
     */
    @ExcelProperty("异常信息")
    @Schema(description = "异常信息")
    private String exception;

    /**
     * 服务ID
     */
    @ExcelProperty("应用标识")
    @Schema(description = "应用标识")
    private String serviceId;

    /**
     * 删除标记
     */
    @TableLogic
    @ExcelIgnore
    private String delFlag;
}
