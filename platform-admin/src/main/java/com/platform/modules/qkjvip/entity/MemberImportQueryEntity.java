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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.platform.common.validator.group.AddGroup;
import com.platform.common.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员导入参数实体
 * @author liuqianru
 * @date 2022/4/15 11:21
 */

@Data
public class MemberImportQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 批次号
     */
    private String batchno;
    /**
     * 礼包id
     */
    private Integer welfareid;
    /**
     * 礼包名称
     */
    private String welfarename;
    /**
     * 礼包日期类型（0：绝对 1：相对）
     */
    private Integer periodtype;
    /**
     * 礼包相对天数
     */
    private Integer perioddays;
    /**
     * 礼包绝对开始日期
     */
    private Date startvaliddate;
    /**
     * 礼包绝对结束日期
     */
    private Date endvaliddate;
}
