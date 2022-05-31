/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 09:37:42        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.QkjvipNewsThumbsupEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsThumbsupService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipNewsEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-05-10 09:37:42
 */
@RestController
@RequestMapping("qkjvip/news")
public class QkjvipNewsController extends AbstractController {
    @Autowired
    private QkjvipNewsService qkjvipNewsService;
    @Autowired
    private QkjvipNewsThumbsupService qkjvipNewsThumbsupService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:news:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipNewsEntity> list = qkjvipNewsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 消费者首页新闻列表--无权限无需登录
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/newsList")
    public RestResponse newsList(@RequestParam Map<String, Object> params) {
        Page page = qkjvipNewsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 消费者新闻详情--无权限无需登录
     *
     * @param qkjvipNews 查询参数
     * @return RestResponse
     */
    @PostMapping("/newsInfo")
    public RestResponse newsInfo(@RequestBody QkjvipNewsEntity qkjvipNews) {
        QkjvipNewsEntity qkjvipNewsEntity = qkjvipNews;  // 先赋值是为了保留参数memberid和openid
        qkjvipNews = qkjvipNewsService.getById(qkjvipNews.getId());
        qkjvipNews.setReadnum(qkjvipNews.getReadnum() + 1);
        qkjvipNewsService.update(qkjvipNews);

        List<QkjvipNewsThumbsupEntity> list = new ArrayList<>();
        Map params = new HashMap();
        params.put("newid", qkjvipNewsEntity.getId());
        list = qkjvipNewsThumbsupService.queryAll(params);
        int thumbsupCnt = list.size();
        qkjvipNews.setThumbsupcnt(thumbsupCnt);
        params.put("openid", qkjvipNewsEntity.getOpenid());
        list = qkjvipNewsThumbsupService.queryAll(params);
        if (list.size() > 0) {  // 已点赞
            qkjvipNews.setIsthumbsup(true);
        } else {
            qkjvipNews.setIsthumbsup(false);
        }
        return RestResponse.success().put("news", qkjvipNews);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:news:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipNewsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:news:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipNewsEntity qkjvipNews = qkjvipNewsService.getById(id);

        return RestResponse.success().put("news", qkjvipNews);
    }

    /**
     * 新增
     *
     * @param qkjvipNews qkjvipNews
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:news:save")
    public RestResponse save(@RequestBody QkjvipNewsEntity qkjvipNews) {
        qkjvipNews.setAddUser(getUserId());
        qkjvipNews.setAddDept(getOrgNo());
        qkjvipNews.setAddTime(new Date());
        qkjvipNews.setReadnum(0);
        qkjvipNewsService.add(qkjvipNews);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipNews qkjvipNews
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:news:update")
    public RestResponse update(@RequestBody QkjvipNewsEntity qkjvipNews) {
        qkjvipNews.setLmUser(getUserId());
        qkjvipNews.setLmDept(getOrgNo());
        qkjvipNews.setLmTime(new Date());
        qkjvipNewsService.update(qkjvipNews);

        return RestResponse.success();
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:news:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipNewsService.deleteBatch(ids);

        return RestResponse.success();
    }
}
