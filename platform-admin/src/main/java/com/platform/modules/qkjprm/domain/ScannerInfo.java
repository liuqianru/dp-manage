package com.platform.modules.qkjprm.domain;

import lombok.Data;

import java.util.Date;

@Data
public class ScannerInfo {
    private int RecordId;
    private Date ScanTime;
    private String OpenId;
    private String UnionId;
    private String MaterialCode;
    private Integer ProductId;
    private String ProductName;
}
