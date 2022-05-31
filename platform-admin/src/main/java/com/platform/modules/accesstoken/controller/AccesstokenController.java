/*
 * 项目名称:platform-plus
 * 类名称:AccesstokenController.java
 * 包名称:com.platform.modules.accesstoken.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-12-04 16:54:30        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.accesstoken.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2019-12-04 16:54:30
 */
@RestController
@RequestMapping("accesstoken/")
public class AccesstokenController extends AbstractController {
    @Autowired
    private AccesstokenService accesstokenService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("accesstoken::list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<AccesstokenEntity> list = accesstokenService.queryAll(params);

        return RestResponse.success().put("list", list);
    }


}
