/*
 * 项目名称:platform-plus
 * 类名称:MemberEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 14:21            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuqianru
 * @date 2020/10/29 14:21
 */

@Data
public class MemberLayerEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 圈层ID
     */
    private String memberLayerId;
    /**
     * 圈层名称
     */
    private String memberLayerName;
}
