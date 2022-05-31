package com.platform.modules.qkjprm.domain;

import lombok.Data;

@Data
public class ScanSendPackResult {
    /**
     * 流水号
     */
    private String data;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 响应码，当成功时值为0
     */
    private Integer respCode;
}
