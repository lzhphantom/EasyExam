package com.lzhphantom.user.dto;

import com.lzhphantom.user.login.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {
    /**
     * 角色ID
     */
    private List<Long> role;

    private Long deptId;

    /**
     * 岗位ID
     */
    private List<Long> post;

    /**
     * 新密码
     */
    private String newpassword1;

    /**
     * 验证码
     */
    private String code;
}
