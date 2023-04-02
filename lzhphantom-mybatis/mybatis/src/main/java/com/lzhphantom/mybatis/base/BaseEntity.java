package com.lzhphantom.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lzhphantom.core.constant.enums.ACTION;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

@Getter
@Setter
public class BaseEntity implements Serializable {
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    // 创建时间
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;
    // 删除人员id
    @Schema(description = "删除人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String deleteBy;
    // 删除时间
    @Schema(description = "删除时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp deleteTime;
    // 更新人员id
    @Schema(description = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    // 更新时间
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @Schema(description = "状态")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String status;

    @Schema(description = "进行中")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Boolean isSagaInProgress;

    @Schema(description = "行为")
    @Enumerated(EnumType.STRING)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private ACTION action;
}
