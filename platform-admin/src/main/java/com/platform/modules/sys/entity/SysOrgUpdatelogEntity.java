/*
 * 项目名称:platform-plus
 * 类名称:SysOrgUpdatelogEntity.java
 * 包名称:com.platform.modules.sys.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 13:52:13        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体
 *
 * @author 孙珊珊
 * @date 2021-08-04 13:52:13
 */
@Data
@TableName("SYS_ORG_UPDATELOG")
public class SysOrgUpdatelogEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * deptcode
     */
    @TableId
    private String deptcode;
    /**
     * add_time
     */
    private Date addTime;
    /**
     * oldfatherlist
     */
    private String oldfatherlist;
    /**
     * newfatherlist
     */
    private String newfatherlist;
}
