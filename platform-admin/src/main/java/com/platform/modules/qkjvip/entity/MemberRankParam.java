package com.platform.modules.qkjvip.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberRankParam implements Serializable {
    /**
     * 排行榜类型： 0 等级排行榜 1 扫码月排行榜 2 扫码年排行榜 3 签到月排行榜 4 签到月排行榜
     */
    public int ranktype;
    /**
     * 当前用户的memberid
     */
    public String memberid;


    /**
     * 显示数量
     */
    public int pagesize;


}
