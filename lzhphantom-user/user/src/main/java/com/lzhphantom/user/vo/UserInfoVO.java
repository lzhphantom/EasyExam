package com.lzhphantom.user.vo;

import com.lzhphantom.user.login.entity.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lzhphantom
 * <p>
 * commit('SET_ROLES', data) commit('SET_NAME', data) commit('SET_AVATAR', data)
 * commit('SET_INTRODUCTION', data) commit('SET_PERMISSIONS', data)
 */
@Data
public class UserInfoVO implements Serializable {

    /**
     * 用户基本信息
     */
    private User user;

    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Long[] roles;

}
