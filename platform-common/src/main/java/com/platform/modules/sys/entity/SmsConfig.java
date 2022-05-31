package com.platform.modules.sys.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 名称：SmsConfig <br>
 * 描述：短信配置信息<br>
 *
 * @author 孙珊珊2019-08-27改
 * @version 1.0
 * @since 1.0.0
 */
@Data
public class SmsConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：创瑞
     */
    @Range(min = 1, max = 3, message = "类型错误")
    private Integer type;

    /**
     * 短信发送域名
     */
    private String host;

    /**
     * 用户名
     */
    private String appid;

    /**
     * 密码(md5加密)
     */
    private String secretkey;

    /**
     * 加密算法
     */
    private String algorithm;

    /**
     * 编码
     */
    private String encode;

    /**
     * 是否压缩
     */
    private String isGizp;

}
