/*
 * 项目名称:platform-plus
 * 类名称:RemoteAjax.java
 * 包名称:com.platform.modules.ajax.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/10/13 10:38            liuqianru    初版做成
 *
 */
package com.platform.modules.ajax.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RemoteAjax
 *
 * @author liuqianru
 * @date 2020/10/13 10:38
 */

@RestController
@CrossOrigin
@RequestMapping("/ajax")
public class RemoteAjax {

    @PostMapping("/test")
    public String test() {
        return "liuqianru";
    }
}
