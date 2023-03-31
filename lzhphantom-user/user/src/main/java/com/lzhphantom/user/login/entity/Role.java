package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.lzhphantom.core.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色表
 *
 * @author lzhphantom
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "role_id", type = IdType.ASSIGN_ID)
    @Schema(description = "角色编号")
    private Long roleId;

    @NotBlank(message = "角色名称 不能为空")
    @Schema(description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色标识 不能为空")
    @Schema(description = "角色标识")
    private String roleCode;

    @NotBlank(message = "角色描述 不能为空")
    @Schema(description = "角色描述")
    private String roleDesc;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @TableLogic
    private String delFlag;
}
