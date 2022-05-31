/*
 * 项目名称:platform-plus
 * 类名称:QkjsmsRInfoEntity.java
 * 包名称:com.platform.modules.qkjsms.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-15 14:31:17        sun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjsms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author sun
 * @date 2022-03-15 14:31:17
 */
@Data
@TableName("QKJSMS_R_INFO")
public class QkjsmsRInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 酒证id
     */
    private String wineid;
    /**
     * 0绑定订单，现场签字短信 1提货出库短信
     */
    private Integer state;
    /**
     * add_time
     */
    private Date addTime;

    @TableField(exist = false)
    private String orderid;
    /**
     * Id
     */
    @TableId
    private String id;
}
