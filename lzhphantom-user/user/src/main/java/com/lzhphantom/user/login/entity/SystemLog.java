package com.lzhphantom.user.login.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.lzhphantom.core.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.envers.Audited;

/**
 * 日志表
 *
 * @author lzhphantom
 */
@Entity
@Table(name = "LZHPHANTOM_USER")
@Data
@Accessors(chain = true)
@Audited
@EqualsAndHashCode(callSuper = true)
public class SystemLog extends BaseEntity {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志类型
     */
    @ExcelProperty("日志类型（0-正常 9-错误）")
    @Column(name = "type", nullable = false)
    private String type;

    /**
     * 日志标题
     */
    @ExcelProperty("日志标题")
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * 操作IP地址
     */
    @ExcelProperty("IP")
    @Column(name = "remoteAddress")
    private String remoteAddr;

    /**
     * 用户浏览器
     */
    @ExcelProperty("浏览器类型")
    @Column(name = "userAgent")
    private String userAgent;

    /**
     * 请求URI
     */
    @ExcelProperty("请求URI")
    @Column(name = "requestUri")
    private String requestUri;

    /**
     * 操作方式
     */
    @ExcelProperty("操作方式")
    @Column(name = "method")
    private String method;

    /**
     * 操作提交的数据
     */
    @ExcelProperty("请求参数")
    @Column(name = "params")
    private String params;

    /**
     * 执行时间
     */
    @ExcelProperty("方法执行时间")
    @Column(name = "time")
    private Long time;

    /**
     * 异常信息
     */
    @ExcelProperty("异常信息")
    @Column(name = "exception")
    private String exception;

    /**
     * 服务ID
     */
    @ExcelProperty("应用标识")
    @Column(name = "serviceId")
    private String serviceId;
}
