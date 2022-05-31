/*
 * 项目名称:platform-plus
 * 类名称:NewsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/3/11 13:12            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * NewsQueryEntity
 *
 * @author liuqianru
 * @date 2021/3/11 13:12
 */
@Data
public class NewsQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageindex;
    private Integer pagesize;
    private Integer articlemodule;  //频道 0全部 1研究 2营销 3评论 4数据 5法规  6专访  7酒文化
}
