package com.lzhphantom.user.login.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户岗位表
 * </p>
 *
 * @author fxz
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPost extends Model<UserPost> {
    /**
     * 用户ID
     */
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 岗位ID
     */
    @Schema(description = "岗位id")
    private Long postId;
}
