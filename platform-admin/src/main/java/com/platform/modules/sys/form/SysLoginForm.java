/*
 * 项目名称:platform-plus
 * 类名称:SysLoginForm.java
 * 包名称:com.platform.modules.sys.form
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.form;

import lombok.Data;

/**
 * 登录表单
 *
 * @author 李鹏军
 */
@Data
public class SysLoginForm {
    private String userName;
    private String password;
    private String captcha;
    private String uuid;
    private String oaId;
    private String dingId;
    private Integer loginType;  // 1:oa登录 2：钉钉登录
}
