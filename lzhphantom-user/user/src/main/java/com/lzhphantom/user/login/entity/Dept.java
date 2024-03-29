package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lzhphantom.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门管理
 *
 * @author lzhphantom
 */
@Schema(description = "部门")
@Data
@EqualsAndHashCode(callSuper = true)
public class Dept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.ASSIGN_ID)
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Schema(description = "部门名称", required = true)
    private String name;

    /**
     * 排序
     */
    @NotNull(message = "部门排序值不能为空")
    @Schema(description = "排序值", required = true)
    private Integer sortOrder;

    /**
     * 父级部门id
     */
    @Schema(description = "父级部门id")
    private Long parentId;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    @TableLogic
    private String delFlag;

}
