package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends Model<RoleMenu> {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @Schema(description = "角色id")
    private Long roleId;

    /**
     * 菜单ID
     */
    @Schema(description = "菜单id")
    private Long menuId;
}
