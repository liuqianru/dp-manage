package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.chanjar.weixin.common.annotation.Required;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("qkjvip_member_memberrank")
public class QkjvipMemberRankEntity implements Serializable {
    @TableId
    public int recordid;
    /**
     * 用户榜值
     */
    public int rank;
    /**
     * 用户等级
     */
    @TableField(exist = false)
    public String memberlevel;
    /**
     * 用户名称
     */
    public String membername;

    /**
     * 头像
     */
    public  String headimg;


    /**
     * 用户unionid
     */
    public String unionid;

    public String memberid;

    /**
     * 排行榜类型： 0 等级排行榜 1 扫码月排行榜 2 扫码年排行榜 3 签到月排行榜 4 签到月排行榜
     */
    public int ranktype;

    public Date createtime;

    public Date lastupdate;
    @TableField(exist = false)
    public  int rownum;



}

