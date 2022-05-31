/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponreceiveController.java
 * 包名称:com.platform.modules.qkjprm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:49        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.controller;

import cn.emay.util.JsonHelper;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjprm.domain.CouponInfo;
import com.platform.modules.qkjprm.domain.LocationAddress;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionAssistrecordEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionAssistrecordService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponreceiveService;
import com.platform.modules.util.HttpClient;
import oracle.sql.DATE;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-12-09 10:46:49
 */
@RestController
@RequestMapping("qkjprm/promotioncouponreceive")
public class QkjprmPromotionCouponreceiveController extends AbstractController {
    @Autowired
    private QkjprmPromotionCouponreceiveService qkjprmPromotionCouponreceiveService;
    @Autowired
    private QkjprmPromotionCouponService couponService;
    @Autowired
    private QkjprmPromotionAssistrecordService assistrecordService;
    @Autowired
    private QkjprmPromotionAssistrecordService qkjprmPromotionAssistrecordService;

    /**
     * 领取优惠券
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/receiveCoupon", method = RequestMethod.POST)
    public RestResponse receiveCoupon(@RequestBody ReceiveParam param) {
        if (param == null)
            return RestResponse.error("参数不正确");
        if (StringUtils.isBlank(param.getUnionid()))
            return RestResponse.error("unionid不允许为空");
        if (StringUtils.isBlank(param.getOpenid()))
            return RestResponse.error("openid不允许为空");
        if (StringUtils.isBlank(param.getCouponid()))
            return RestResponse.error("优惠券不正确");
        if (StringUtils.isBlank(param.getLat()) || StringUtils.isBlank(param.getLng()))
            return RestResponse.error("地址不正确");

        //检验领取地址
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?key=PE5BZ-R2ICF-X3PJC-N4H24-UXPYE-64FKV";
        url += "&location=" + param.getLat() + "," + param.getLng();
        String result = HttpClient.sendGet(url);
        LocationAddress address = JsonHelper.fromJson(LocationAddress.class, result);
        if (address == null || !address.getStatus().equals(0))
            return RestResponse.error("领取失败,地址查询不正确");
        String adcode = address.getResult().getAd_info().getAdcode().substring(0, 4);
        if (!adcode.equals("6301") && !adcode.equals("6302"))
            return RestResponse.error("不在活动区域内");

        //校验优惠券库存
        QkjprmPromotionCouponEntity couponEntity = couponService.getById(param.couponid);
        if (couponEntity == null)
            return RestResponse.error("优惠券不存在,请重新领取");
        if (couponEntity.getStock() <= 0)
            return RestResponse.error("该优惠券已被领完了");
        if (couponEntity.getActionenddate().compareTo(new Date()) < 0)
            return RestResponse.error("该优惠券已过期");

        Calendar calendar = Calendar.getInstance();

        HashMap<String, Object> queryParam = new HashMap<>();
        queryParam.put("date", calendar.getTime());
        queryParam.put("unionid", param.unionid);
        //判断是否已超过领取限制
        List<QkjprmPromotionCouponreceiveEntity> receiveList = qkjprmPromotionCouponreceiveService.queryAll(queryParam);
        if (receiveList != null && receiveList.size() >= 15) {
            return RestResponse.error("你今天已经领取15次了哦，明天再来吧");
        }
        try {
            QkjprmPromotionCouponreceiveEntity entity = qkjprmPromotionCouponreceiveService.receiveCoupon(param);
            if (entity == null)
                return RestResponse.error("领取失败,请稍后重试");
            return RestResponse.success().put("couponsn", entity.getId());
        } catch (Exception e) {
            return RestResponse.error("领取失败,请稍后重试");
        }

    }


    /**
     * 根据优惠券券码 获取详情
     *
     * @param params
     * @return
     */
    @GetMapping("/getcouponinfo")
    public RestResponse getCouponInfo(@RequestParam Map<String, Object> params) {
        String couponsn = params.get("couponsn").toString();
        QkjprmPromotionCouponreceiveEntity entity = qkjprmPromotionCouponreceiveService.getById(couponsn);
        if (entity == null)
            return RestResponse.error("返现券不存在");

        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("couponsn", couponsn);
        queryParam.put("isvalid", 1);
        List<QkjprmPromotionAssistrecordEntity> list = assistrecordService.queryAll(queryParam);

        boolean isassisted = false;
        if (params.get("unionid") != null && !StringUtils.isBlank(params.get("unionid").toString())) {
            Map map = new HashMap();
            map.put("couponreceiveid", couponsn);
            map.put("unionid", params.get("unionid").toString());
            List<QkjprmPromotionAssistrecordEntity> assistedList = qkjprmPromotionAssistrecordService.queryAll(params);
            if (assistedList != null && assistedList.size() > 0) {
                isassisted = true;
            }
        }
        return RestResponse.success().put("couponsn", entity.getId())
                .put("couponamount", entity.getCouponamount())
                .put("assistcount", entity.getAssistcount())
                .put("isdouble", entity.getIsdouble().equals(1))
                .put("assistedunionid", entity.getUnionid())
                .put("assistlist", list)
                .put("isassisted", isassisted);
    }

    /**
     * 获取个人优惠券列表
     *
     * @param params
     * @return
     */
    @GetMapping("/getmembercouponlist")
    public RestResponse getMemberCouponList(@RequestParam Map<String, Object> params) {

        if (params.containsKey("pageindex") && !"".equals(params.get("pageindex"))) {
            params.put("pageindex", Convert.toInt(params.get("pageindex")));
        }
        if (params.containsKey("pagesize") && !"".equals(params.get("pagesize"))) {
            params.put("pagesize", Convert.toInt(params.get("pagesize")));
        }
        List<CouponInfo> list = qkjprmPromotionCouponreceiveService.getCouponList(params);
        int count = qkjprmPromotionCouponreceiveService.getCouponCount(params);


        return RestResponse.success().put("count", count).put("list", list);
    }

    /**
     * 获取个人优惠券列表
     *
     * @param params
     * @return
     */
    @GetMapping("/getcouponlist")
    public RestResponse getCouponList(@RequestBody Map<String, Object> params) {
        return RestResponse.success();
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjprm:promotioncouponreceive:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjprmPromotionCouponreceiveEntity> list = qkjprmPromotionCouponreceiveService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjprm:promotioncouponreceive:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjprmPromotionCouponreceiveService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjprm:promotioncouponreceive:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjprmPromotionCouponreceiveEntity qkjprmPromotionCouponreceive = qkjprmPromotionCouponreceiveService.getById(id);

        return RestResponse.success().put("promotioncouponreceive", qkjprmPromotionCouponreceive);
    }


    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjprm:promotioncouponreceive:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjprmPromotionCouponreceiveService.deleteBatch(ids);

        return RestResponse.success();
    }
}
