package com.platform.modules.qkjprm.domain;

import lombok.Data;

@Data
public class AddressInfo {
    private String nation_code;
    /**
     * 行政区划代码
     */
    private String adcode;
    /**
     * 城市编号由国家码+行政区划代码（提出城市级别）组合而来，总共为9位
     */
    private  String city_code;
    /**
     * 完整地址
     */
    private String name;
    /**
     * 国家
     */
    private String nation;
    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private  String city;
    /**
     * 区县
     */
    private  String district;
}
