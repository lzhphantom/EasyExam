package com.lzhphantom.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lzhphantom.core.constant.enums.ACTION;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class BaseEntity {
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createDt;
    // 删除人员id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String deleteBy;
    // 删除时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp deleteDt;
    // 更新人员id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String submittedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp submittedDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String approvedBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp approvedDt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String status;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Boolean isSagaInProgress;

    @Enumerated(EnumType.STRING)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private ACTION action;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String delFlag;

    @PrePersist
    // 预处理 创建时间，更新时间，创建人员id，更新人员id
    public void prePersist() {
        if (Objects.isNull(this.isSagaInProgress)) {
            isSagaInProgress = Boolean.FALSE;
        }
        status = action.getStatus();
        if (Objects.isNull(this.createDt)) {
            this.createDt = Timestamp.valueOf(LocalDateTime.now());
        }
        if (this.updateDt == null) {
            this.updateDt = Timestamp.valueOf(LocalDateTime.now());
        }
        if (ACTION.INSERT.equals(this.getAction()) || ACTION.INSERT_TO_APPROVE.equals(this.getAction())) {
            this.createBy = "0";
            this.updateBy = "0";
        }
    }

    @PreUpdate
    public void preUpdate() {
        if (Objects.isNull(getIsSagaInProgress())) {
            isSagaInProgress = Boolean.FALSE;
        }
        status = action.getStatus();
        this.updateDt = Timestamp.valueOf(LocalDateTime.now());
        this.updateBy = "0";

        if (ACTION.DELETE.equals(this.getAction())) {
            this.deleteDt = Timestamp.valueOf(LocalDateTime.now());
            this.deleteBy = "0";
        }
    }
}
