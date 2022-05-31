/*
 * 项目名称:platform-plus
 * 类名称:QkjvipNewsCarouselController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-10 13:46:41        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipNewsCarouselEntity;
import com.platform.modules.qkjvip.service.QkjvipNewsCarouselService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-05-10 13:46:41
 */
@RestController
@RequestMapping("qkjvip/newscarousel")
public class QkjvipNewsCarouselController extends AbstractController {
    @Autowired
    private QkjvipNewsCarouselService qkjvipNewsCarouselService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:newscarousel:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipNewsCarouselEntity> list = qkjvipNewsCarouselService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 消费者首页轮播图--无权限无需登录
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/carouselList")
    public RestResponse carouselList(@RequestParam Map<String, Object> params) {
        params.put("sort", "addTime");
        List<QkjvipNewsCarouselEntity> list = qkjvipNewsCarouselService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:newscarousel:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipNewsCarouselService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:newscarousel:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipNewsCarouselEntity qkjvipNewsCarousel = qkjvipNewsCarouselService.getById(id);

        return RestResponse.success().put("newscarousel", qkjvipNewsCarousel);
    }

    /**
     * 新增
     *
     * @param qkjvipNewsCarousel qkjvipNewsCarousel
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:newscarousel:save")
    public RestResponse save(@RequestBody QkjvipNewsCarouselEntity qkjvipNewsCarousel) {
        qkjvipNewsCarousel.setAddUser(getUserId());
        qkjvipNewsCarousel.setAddDept(getOrgNo());
        qkjvipNewsCarousel.setAddTime(new Date());
        qkjvipNewsCarouselService.add(qkjvipNewsCarousel);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipNewsCarousel qkjvipNewsCarousel
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:newscarousel:update")
    public RestResponse update(@RequestBody QkjvipNewsCarouselEntity qkjvipNewsCarousel) {

        qkjvipNewsCarouselService.update(qkjvipNewsCarousel);

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
    @RequiresPermissions("qkjvip:newscarousel:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipNewsCarouselService.deleteBatch(ids);

        return RestResponse.success();
    }
}
