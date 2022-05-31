package com.platform.modules.qkjprm.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CouponInfo {
    private String couponsn;
    private BigDecimal couponAmount;
    private Date validDate;
    private Integer productId;
    private String productName;
    private boolean isexchange;
    private boolean isvalid;
    private boolean isdouble;
    private String couponName;
}
