package com.platform.modules.qkjprm.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiveParam implements Serializable {
    public String unionid;

    public String openid;

    /**
     * 昵称
     */
    public String nickname;

    /**
     * 头像
     */
    public String headimg;

    public Integer sex;

    /**
     * 优惠券id
     */
    public String couponid;

    /**
     * 纬度
     */
    public String lat;
    /**
     * 经度
     */
    public String lng;
}
