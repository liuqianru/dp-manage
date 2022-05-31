package com.platform.modules.qkjprm.domain;

import lombok.Data;

@Data
public class LocationAddress {
    private Integer status;
    private  String message;
    private String request_id;
    private AddressModel result;
}
