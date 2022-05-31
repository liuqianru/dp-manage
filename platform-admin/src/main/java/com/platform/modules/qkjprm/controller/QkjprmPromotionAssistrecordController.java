/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionAssistrecordController.java
 * 包名称:com.platform.modules.qkjprm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponexchangeEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponexchangeService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponreceiveService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjprm.entity.QkjprmPromotionAssistrecordEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionAssistrecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.Cleaner;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@RestController
@RequestMapping("qkjprm/promotionassistrecord")
public class QkjprmPromotionAssistrecordController extends AbstractController {
    @Autowired
    private QkjprmPromotionAssistrecordService qkjprmPromotionAssistrecordService;
    @Autowired
    private QkjprmPromotionCouponService couponService;
    @Autowired
    private QkjprmPromotionCouponexchangeService exchangeService;
    @Autowired
    private QkjprmPromotionCouponreceiveService receiveService;

    /**
     * 助力优惠券
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/assistcoupon", method = RequestMethod.POST)
    public RestResponse assistCoupon(@RequestBody HashMap<String, Object> params) {
        //优惠券领取记录id
        if (!params.containsKey("couponsn") || params.get("couponsn").equals(""))
            return RestResponse.error("助力失败,优惠券不正确");
        if (!params.containsKey("unionid") || params.get("unionid").equals(""))
            return RestResponse.error("助力失败,当前助力人为空");
        String couponsn = (String) params.get("couponsn");

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("couponsn", couponsn);
        //优惠券是否已过期

        QkjprmPromotionCouponEntity couponEntity = couponService.getCouponByReceive(couponsn);
        if (couponEntity == null)
            return RestResponse.error("优惠券不存在");
        //是否为自己助力
        QkjprmPromotionCouponreceiveEntity receiveEntity = receiveService.getById(couponsn);
        if (receiveEntity == null) {
            return RestResponse.error("优惠券不存在");
        }
        SimpleDateFormat fmt=new SimpleDateFormat("yyyyMMdd");
        if (!fmt.format(receiveEntity.getCreatetime()).equals(fmt.format(new Date())))
            return RestResponse.error("优惠券已过期");
        if (receiveEntity.getUnionid().equals((String) params.get("unionid")))
            return RestResponse.error("不可以为自己助力");
        //优惠券是否已兑现
        List<QkjprmPromotionCouponexchangeEntity> exchangeList = exchangeService.queryAll(queryParam);
        if (exchangeList != null && exchangeList.size() > 0)
            return RestResponse.error("优惠券已经兑现了");

        //是否已助力
        queryParam.put("unionid", params.get("unionid"));
        List<QkjprmPromotionAssistrecordEntity> assistList = qkjprmPromotionAssistrecordService.queryAll(queryParam);
        if (assistList != null && assistList.size() > 0) {
            return RestResponse.error("已经助力过了");
        }
        ReceiveParam assist = new ReceiveParam();
        assist.setUnionid((String) params.get("unionid"));
        assist.setOpenid((String) params.get("openid"));
        assist.setNickname((String) params.get("nickname"));
        assist.setHeadimg((String) params.get("headimg"));
        try {
            qkjprmPromotionAssistrecordService.assistCoupon(assist, couponsn);
        } catch (Exception e) {
            return RestResponse.error("助力失败");
        }
        return RestResponse.success("助力成功");
    }

    /**
     * 助力排行榜的积分
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/assistintegralrank", method = RequestMethod.POST)
    public RestResponse assistIntegralRank(@RequestBody HashMap<String, Object> params) {

        if (params == null || params.size() == 0)
            return RestResponse.error("助力失败，参数不正确");
        if (params.get("unionid").equals(params.get("assistedunionid")))
            return RestResponse.error("不可以为自己助力");
        //活动是否已经结束
        Calendar endCalender = Calendar.getInstance();
        endCalender.set(2021, 12, 31, 23, 59, 59);
        Calendar nowCalender = Calendar.getInstance();
        if (endCalender.before(nowCalender))
            return RestResponse.error("活动已结束");
        ReceiveParam assist = new ReceiveParam();
        assist.setUnionid((String) params.get("unionid"));
        assist.setOpenid((String) params.get("openid"));
        assist.setNickname((String) params.get("nickname"));
        assist.setHeadimg((String) params.get("headimg"));
        try {
            if (!qkjprmPromotionAssistrecordService.assistIntegralRank(assist, (String) params.get("assistedunionid"))) {
                return RestResponse.error("助力失败");
            }
        } catch (Exception e) {
            return RestResponse.error("助力失败");
        }

        return RestResponse.success("助力成功");
    }


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjprmPromotionAssistrecordEntity> list = qkjprmPromotionAssistrecordService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjprm:promotionassistrecord:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjprmPromotionAssistrecordService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjprm:promotionassistrecord:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord = qkjprmPromotionAssistrecordService.getById(id);

        return RestResponse.success().put("promotionassistrecord", qkjprmPromotionAssistrecord);
    }

    /**
     * 新增
     *
     * @param qkjprmPromotionAssistrecord qkjprmPromotionAssistrecord
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjprm:promotionassistrecord:save")
    public RestResponse save(@RequestBody QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord) {

        qkjprmPromotionAssistrecordService.add(qkjprmPromotionAssistrecord);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjprmPromotionAssistrecord qkjprmPromotionAssistrecord
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjprm:promotionassistrecord:update")
    public RestResponse update(@RequestBody QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord) {

        qkjprmPromotionAssistrecordService.update(qkjprmPromotionAssistrecord);

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
    @RequiresPermissions("qkjprm:promotionassistrecord:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjprmPromotionAssistrecordService.deleteBatch(ids);

        return RestResponse.success();
    }
}
