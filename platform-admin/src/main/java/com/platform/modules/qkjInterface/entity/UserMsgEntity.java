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
package com.platform.modules.qkjInterface.entity;

import cn.emay.util.DateUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.qkjvip.entity.MemberTagsQueryEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 发信息实体类
 * @author liuqianru
 * @date 2020/3/9 14:21
 */

@Data
public class UserMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 手机号列表逗号分割
     */
    private String mobilelist;

    /**
     * 消息
     */
    private String msg;

    /**
     * 标题
     */
    private String title;

    /**
     * 钉钉手机号列表逗号分割
     */
    private String dinglist;

    private String url;
}
