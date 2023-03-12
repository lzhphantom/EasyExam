package com.lzhphantom.user.dto;

import com.lzhphantom.user.login.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lzhphantom
 * 角色Dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends Role {
    /**
     * 角色部门Id
     */
    private Long roleDeptId;

    /**
     * 部门名称
     */
    private String deptName;
}
