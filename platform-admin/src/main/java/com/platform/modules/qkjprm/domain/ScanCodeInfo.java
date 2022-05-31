package com.platform.modules.qkjprm.domain;

import lombok.Data;

import java.util.List;

@Data
public class ScanCodeInfo {
    private ScannerInfo Scannerinfo;
    private Integer result;
    private String descr;
}
