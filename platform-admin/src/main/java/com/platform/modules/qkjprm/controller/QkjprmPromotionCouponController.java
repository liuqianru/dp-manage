/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponController.java
 * 包名称:com.platform.modules.qkjprm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@RestController
@RequestMapping("/qkjprm/promotioncoupon")
public class QkjprmPromotionCouponController extends AbstractController {
    @Autowired
    private QkjprmPromotionCouponService qkjprmPromotionCouponService;

    /**
     * 获取可用优惠券列表
     *
     * @param params
     * @return
     */
    @GetMapping("/getvaildcoupon")
    public RestResponse getVaildCoupon(@RequestParam Map<String, Object> params) {
        String unionid = "";
        if (params.containsKey("unionid"))
            unionid = (String) params.get("unionid");
        List<QkjprmPromotionCouponEntity> list = qkjprmPromotionCouponService.getVaildList(unionid);
        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjprmPromotionCouponEntity> list = qkjprmPromotionCouponService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjprmPromotionCouponService.queryPage(params);
        return RestResponse.success().put("page", page);
    }


    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjprm:promotioncoupon:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjprmPromotionCouponEntity qkjprmPromotionCoupon = qkjprmPromotionCouponService.getById(id);

        return RestResponse.success().put("promotioncoupon", qkjprmPromotionCoupon);
    }

    /**
     * 新增
     *
     * @param qkjprmPromotionCoupon qkjprmPromotionCoupon
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjprm:promotioncoupon:save")
    public RestResponse save(@RequestBody QkjprmPromotionCouponEntity qkjprmPromotionCoupon) {

        qkjprmPromotionCouponService.add(qkjprmPromotionCoupon);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjprmPromotionCoupon qkjprmPromotionCoupon
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjprm:promotioncoupon:update")
    public RestResponse update(@RequestBody QkjprmPromotionCouponEntity qkjprmPromotionCoupon) {

        qkjprmPromotionCouponService.update(qkjprmPromotionCoupon);

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
    @RequiresPermissions("qkjprm:promotioncoupon:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjprmPromotionCouponService.deleteBatch(ids);

        return RestResponse.success();
    }
}
