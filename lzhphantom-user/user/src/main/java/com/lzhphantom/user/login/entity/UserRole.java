package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author lzhphantom
 * @since 2019/2/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Model<UserRole> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 角色ID
     */
    @Schema(description = "角色id")
    private Long roleId;
}
