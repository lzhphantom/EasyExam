package com.lzhphantom.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author lzhphantom
 */
@Data
@Schema(description = "前端角色展示对象")
public class RoleVo {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单列表
     */
    private String menuIds;

}
