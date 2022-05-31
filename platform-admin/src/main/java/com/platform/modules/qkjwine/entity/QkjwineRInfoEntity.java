/*
 * 项目名称:platform-plus
 * 类名称:QkjwineRInfoEntity.java
 * 包名称:com.platform.modules.qkjwine.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-16 09:16:11             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjwine.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 
 * @date 2022-02-16 09:16:11
 */
@Data
@TableName("QKJWINE_R_INFO")
public class QkjwineRInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private String number;
    /**
     * 入库编号
     */
    private String wareporid;
    /**
     * 酒证
     */
    private String winenum;
    /**
     * 资料
     */
    private String wdata;
    /**
     * 订单id
     */
    private String orderid;
    /**
     * 0:销售订单1.赠送订单
     */
    private Integer ordertype;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * add_time
     */
    private Date addTime;

    private String memberid;

    private String receivename;

    private String receivemobile;

    private Integer state;//状态 0 可用 1已送出  2.已被接收

    private String otherwineid;

    private String takeid;

    private String remarks;

    private Date givedate;

    private Date canceldate;

    private String scenephone;  // 封坛时客户手机

    private String scenename;  // 封坛时客户姓名

    private Date scenetime; // 实际入库时间（封坛时间）

    private String ftnumber; // 封坛酒编号

    private Integer disable;

    @TableField(fill = FieldFill.UPDATE)
    private String scenefile;  // 封坛时上传的文件

    private String scenesign;  // 封坛时客户签字图片

    /**
     * 封坛客户签字base64位
     */
    @TableField(exist = false)
    private String signbase64img;

    /**
     * 页面动作 1:赠送 2：取消
     */
    @TableField(exist = false)
    private Integer actiontype;

    /**birthday
     * 产品名称
     */
    @TableField(exist = false)
    private String proname;
    /**
     * 产品图片
     */
    @TableField(exist = false)
    private String productimg;
    /**
     * 藏酒价值（当前价格）
     */
    @TableField(exist = false)
    private Double saleprice;
    /**
     *库名称
     */
    @TableField(exist = false)
    private String housename;
    /**
     * 收藏时的价格
     */
    @TableField(exist = false)
    private Double price;
    /**
     * 封坛日期
     */
    @TableField(exist = false)
    private Date createdate;
    /**
     * 出库日期
     */
    @TableField(exist = false)
    private Date takedate;
    /**
     * 领取时间
     */
    @TableField(exist = false)
    private Date receivedate;
    /**
     * 会员名称
     */
    @TableField(exist = false)
    private String membername;
    /**
     * 手机
     */
    @TableField(exist = false)
    private String mobile;
    /**
     * 提货状态
     */
    @TableField(exist = false)
    private Integer takestate;
    /**
     * 提货方式
     */
    @TableField(exist = false)
    private Integer taketype;
    /**
     * 提货人
     */
    @TableField(exist = false)
    private String takename;
    /**
     * 提货电话
     */
    @TableField(exist = false)
    private String takemobile;
    /**
     * 邮寄地址
     */
    @TableField(exist = false)
    private String takeaddress;
    /**
     * 申请提货
     */
    @TableField(exist = false)
    private Date applytakedate;
}
