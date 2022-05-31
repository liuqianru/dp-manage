/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareproEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.modules.qkjcus.entity.QkjcusOrderEntity;
import com.platform.modules.qkjcus.entity.QkjcusOrderProEntity;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@Data
@TableName("QKJVIP_ORDER_WAREPRO")
public class QkjvipOrderWareproEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 0 未封坛 1未认购 2已认购 3已借用
     */
    private Integer state;
    /**
     * 数量
     */
    private Integer num;
    /**
     * Disabled
     */
    private Integer disabled;
    /**
     * wareid
     */
    private String wareid;
    /**
     * Remark
     */
    private String remark;
    /**
     * Id
     */
    @TableId
    private String id;
    /**
     * CreateOn
     */
    private Date createon;
    /**
     * Creator
     */
    private String creator;
    /**
     * updatetime
     */
    private Date updatetime;

    private String proid;

    private String proname;

    @TableField(exist = false)
    private String housename;
    @TableField(exist = false)
    private BigDecimal saleprice;
    @TableField(exist = false)
    private List<QkjcusOrderProEntity> cusorders;
    @TableField(exist = false)
    private List<QkjvipOrderOrderdetailEntity> orders;
    @TableField(exist = false)
    private List<QkjtakeRWineEntity> takes;
    @TableField(exist = false)
    private String membername;
    @TableField(exist = false)
    private String mainid;
    @TableField(exist = false)
    private String orderid;

    /**转移仓库**/
    @TableField(exist = false)
    private String newwareid;
    @TableField(exist = false)
    private String ordertype;
    @TableField(exist = false)
    private String oldwareid;

    @TableField(exist = false)
    private Date scenetime; // 酒证的实际入库时间
    @TableField(exist = false)
    private String ftnumber;// 封坛酒编号


    @TableField(exist = false)
    private List<QkjcusOrderEntity> cusmainorders;
    @TableField(exist = false)
    private List<QkjvipOrderOrderEntity> mainorders;
}
