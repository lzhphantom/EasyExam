package com.lzhphantom.user.dto;

import com.lzhphantom.user.login.entity.Post;
import com.lzhphantom.user.login.entity.Role;
import com.lzhphantom.user.login.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzhphantom
 * <p>
 * commit('SET_ROLES', data) commit('SET_NAME', data) commit('SET_AVATAR', data)
 * commit('SET_INTRODUCTION', data) commit('SET_PERMISSIONS', data)
 */
@Data
public class UserInfo implements Serializable {
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

    /**
     * 角色集合
     */
    private List<Role> roleList;

    /**
     * 岗位集合
     */
    private Long[] posts;

    /**
     * 岗位集合
     */
    private List<Post> postList;
}
