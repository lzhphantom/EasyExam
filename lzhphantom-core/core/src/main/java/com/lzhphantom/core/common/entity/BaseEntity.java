package com.lzhphantom.core.common.entity;

import com.lzhphantom.core.common.enums.ACTION;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@MappedSuperclass
@Audited
public class BaseEntity {
    @Column(name = "CREATE_BY", updatable = false)
    private String createBy;
    // 创建时间
    @Column(name = "CREATE_TIME", updatable = false)
    @CreationTimestamp
    private Timestamp createDt;
    // 删除人员id
    @Column(name = "DELETE_BY")
    private String deleteBy;
    // 删除时间
    @Column(name = "DELETE_TIME")
    private Timestamp deleteDt;
    // 更新人员id
    @Column(name = "UPDATE_BY")
    private String updateBy;
    // 更新时间
    @Column(name = "UPDATE_TIME")
    @UpdateTimestamp
    private Timestamp updateDt;

    @Column(name = "SUBMITTED_BY")
    private String submittedBy;

    @Column(name = "SUBMITTED_TIME")
    private Timestamp submittedDt;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "APPROVED_TIME")
    private Timestamp approvedDt;

    @Column(name = "STATUS", length = 30)
    private String status;

    @ColumnDefault("0")
    @Column(name = "IS_SAGA_IN_PROGRESS", nullable = false)
    private Boolean isSagaInProgress;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION", length = 30, nullable = false)
    private ACTION action;

    @PrePersist
    // 预处理 创建时间，更新时间，创建人员id，更新人员id
    public void prePersist() {
        if (Objects.isNull(getIsSagaInProgress())) {
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
