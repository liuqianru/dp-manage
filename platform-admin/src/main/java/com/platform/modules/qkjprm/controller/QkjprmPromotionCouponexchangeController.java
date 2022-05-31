/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponexchangeController.java
 * 包名称:com.platform.modules.qkjprm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.controller;

import cn.emay.util.AES;
import cn.emay.util.JsonHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjprm.domain.ScanCodeInfo;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponexchangeEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponexchangeService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.RandomGUID;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.emay.util.Md5.md5;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@RestController
@RequestMapping("qkjprm/promotioncouponexchange")
public class QkjprmPromotionCouponexchangeController extends AbstractController {
    @Autowired
    private QkjprmPromotionCouponexchangeService qkjprmPromotionCouponexchangeService;
    @Autowired
    private QkjprmPromotionCouponService couponService;


    @GetMapping("/scanexchange")
    public RestResponse scanExchange(@RequestParam Map<String, Object> params) {
        if (!params.containsKey("marketcode") || "".equals(params.get("marketcode")))
            return RestResponse.error("二维码不正确");
        if (!params.containsKey("unionid") || "".equals(params.get("unionid")))
            return RestResponse.error("用户unionid不正确");
        if (!params.containsKey("couponsn") || "".equals(params.get("couponsn")))
            return RestResponse.error("优惠券码不正确");

        String marketcode = (String) params.get("marketcode");
        String unionid = (String) params.get("unionid");
        String couponsn = (String) params.get("couponsn");

        String bottlecode = marketcode.substring(marketcode.lastIndexOf("/") >= 0 ? marketcode.lastIndexOf("/")+1 : 0);
        if (!StringUtils.isNotBlank(bottlecode))
            return RestResponse.error("二维码内容不正确");
        QkjprmPromotionCouponEntity couponEntity = couponService.getCouponByReceive(couponsn);

        //验证scan表是否已存在扫码记录
        List<String> scanList = qkjprmPromotionCouponexchangeService.getScanUnion(bottlecode);
        if (scanList != null && scanList.stream().filter(n -> !n.equals(unionid)).count() > 0)
            return RestResponse.error("该码已被扫");

        //验证云码是否已扫码
        ScanCodeInfo ymScanInfo = qkjprmPromotionCouponexchangeService.getYMScanInfo(bottlecode);
        if (ymScanInfo == null)
            return RestResponse.error("扫码验证失败");
        if (!ymScanInfo.getResult().equals(0) || ymScanInfo.getScannerinfo() == null)
            return RestResponse.error("请查看活动规则，扫对应二维码");
        if (ymScanInfo.getScannerinfo().getUnionId() != null && !ymScanInfo.getScannerinfo().getUnionId().equals(unionid))
            return RestResponse.error("该码已被扫");
        if (!ymScanInfo.getScannerinfo().getProductId().toString().equals(couponEntity.getProductid()))
            return RestResponse.error("该码与红包商品不一致");

        //是否已兑换
        Map<String, Object> query = new HashMap<>();
        query.put("date", new Date());
        query.put("exchangeresult", 1);
        query.put("unionid",unionid);
        List<QkjprmPromotionCouponexchangeEntity> exchangeList = qkjprmPromotionCouponexchangeService.queryAll(query);
        if (exchangeList != null && exchangeList.size() >= 15)
            return RestResponse.error("今天兑换次数已用完");
        query = new HashMap<>();
        query.put("marketcode", bottlecode);
        query.put("exchangeresult", 1);
        exchangeList=qkjprmPromotionCouponexchangeService.queryAll(query);
        if (exchangeList != null && exchangeList.size() > 0)
            return RestResponse.error("该码已兑换");
        //兑换
        try {
            if (!qkjprmPromotionCouponexchangeService.exchangeScanCode(bottlecode, unionid, couponsn)) {
                return RestResponse.error("兑换失败");
            }
        } catch (Exception e) {
            return RestResponse.error("兑换失败");
        }
        return RestResponse.success("兑换成功");
    }


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjprm:promotioncouponexchange:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjprmPromotionCouponexchangeEntity> list = qkjprmPromotionCouponexchangeService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjprm:promotioncouponexchange:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjprmPromotionCouponexchangeService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjprm:promotioncouponexchange:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange = qkjprmPromotionCouponexchangeService.getById(id);

        return RestResponse.success().put("promotioncouponexchange", qkjprmPromotionCouponexchange);
    }

    /**
     * 新增
     *
     * @param qkjprmPromotionCouponexchange qkjprmPromotionCouponexchange
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjprm:promotioncouponexchange:save")
    public RestResponse save(@RequestBody QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange) {

        qkjprmPromotionCouponexchangeService.add(qkjprmPromotionCouponexchange);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjprmPromotionCouponexchange qkjprmPromotionCouponexchange
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjprm:promotioncouponexchange:update")
    public RestResponse update(@RequestBody QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange) {

        qkjprmPromotionCouponexchangeService.update(qkjprmPromotionCouponexchange);

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
    @RequiresPermissions("qkjprm:promotioncouponexchange:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjprmPromotionCouponexchangeService.deleteBatch(ids);

        return RestResponse.success();
    }
}
