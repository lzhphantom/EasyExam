package com.lzhphantom.codegen.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lzhphantom.mybatis.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 生成记录
 *
 * @author lzhphantom
 */
@Data
@TableName("lzhphantom_gen_form_conf")
@Schema(description = "生成记录")
@EqualsAndHashCode(callSuper = true)
public class GenFormConf extends BaseEntity {

    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "ID")
    private Long id;

    /**
     * 表名称
     */
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 表单信息
     */
    @Schema(description = "表单信息")
    private String formInfo;

    /**
     * 删除标记
     */
    @Schema(description = "删除标记")
    private String delFlag;

}