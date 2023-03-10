package com.lzhphantom.core.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LzhphantomResult<T> implements Serializable {
    private Boolean success = Boolean.TRUE;
    private Integer errCode;
    private String errMsg;
    private T data;
}
