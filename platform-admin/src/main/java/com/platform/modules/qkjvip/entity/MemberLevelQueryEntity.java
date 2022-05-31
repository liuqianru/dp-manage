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
import java.util.List;

/**
 * @author liuqianru
 * @date 2020/10/29 14:21
 */

@Data
public class MemberLevelQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String memberid;
    private Integer memberlevel;
}
