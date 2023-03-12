package com.lzhphantom.user.vo;

import com.lzhphantom.user.login.entity.SystemLog;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lzhphantom
 * @date 2019/2/1
 */
@Data
public class LogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private SystemLog sysLog;

    private String username;

}
