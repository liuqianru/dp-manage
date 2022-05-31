/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionMemberController.java
 * 包名称:com.platform.modules.qkjprm.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionMemberService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Component("qrtzPromotionRank")
@RestController
@RequestMapping("qkjprm/promotionmember")
public class QkjprmPromotionMemberController extends AbstractController {
    @Autowired
    private QkjprmPromotionMemberService qkjprmPromotionMemberService;

    @RequestMapping("/updateMemberIntegral")
    public RestResponse updateMemberIntegral(String startdate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        try {
            Date date = simpleDateFormat.parse(startdate);
            if (!qkjprmPromotionMemberService.updateProductIntegral(date)) {
                return RestResponse.error("更新失败");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return RestResponse.error("日期不正确");
        }

        return RestResponse.success();
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjprm:promotionmember:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {

        List<QkjprmPromotionMemberEntity> list = qkjprmPromotionMemberService.queryAll(params);
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
        Integer total = qkjprmPromotionMemberService.getCount(params);
        if (!params.containsKey("page") || Convert.toInt(params.get("page")) <= 0)
            params.put("page", 1);
        if (!params.containsKey("limit") || Convert.toInt(params.get("limit")) <= 0)
            params.put("limit", 10);
        List<QkjprmPromotionMemberEntity> list = qkjprmPromotionMemberService.getList(params);

        int limit = Convert.toInt(params.get("limit"));
        return RestResponse.success().put("List", list)
                .put("total", total).put("current", params.get("page"))
                .put("size", params.get("limit"))
                .put("pages", (total * 0.1) % limit > 0 ? total / limit + 1 : total / limit);
    }

    @GetMapping("/getlist")
    public RestResponse getList(@RequestParam Map<String, Object> params) {
        Map<String, Object> queryParam = new HashMap<>();
        if (params.containsKey("page"))
            queryParam.put("page", params.get("page"));
        if (params.containsKey("limit"))
            queryParam.put("limit", params.get("limit"));
        Page page = qkjprmPromotionMemberService.queryPage(queryParam);
        QkjprmPromotionMemberEntity myEntity = null;
        int myrank = 0;
        if (params.containsKey("unionid") && !params.get("unionid").equals("")) {
            String unionid = (String) params.get("unionid");
            List<QkjprmPromotionMemberEntity> list = page.getRecords();
            if (list != null && list.stream().filter(n -> n.getUnionid().equals(unionid)).count() > 0) {
                myEntity = list.stream().filter(n -> n.getUnionid().equals(unionid)).findFirst().get();
                myrank = list.indexOf(myEntity) + 1;
            } else {
                myEntity = qkjprmPromotionMemberService.getMemberByUnionid(unionid);
                myrank = qkjprmPromotionMemberService.getMemberCount(unionid);
            }
        }
        return RestResponse.success().put("myinfo", myEntity).put("myrank", myrank).put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjprm:promotionmember:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjprmPromotionMemberEntity qkjprmPromotionMember = qkjprmPromotionMemberService.getById(id);

        return RestResponse.success().put("promotionmember", qkjprmPromotionMember);
    }

    /**
     * 新增
     *
     * @param qkjprmPromotionMember qkjprmPromotionMember
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjprm:promotionmember:save")
    public RestResponse save(@RequestBody QkjprmPromotionMemberEntity qkjprmPromotionMember) {

        qkjprmPromotionMemberService.add(qkjprmPromotionMember);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjprmPromotionMember qkjprmPromotionMember
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjprm:promotionmember:update")
    public RestResponse update(@RequestBody QkjprmPromotionMemberEntity qkjprmPromotionMember) {

        qkjprmPromotionMemberService.update(qkjprmPromotionMember);

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
    @RequiresPermissions("qkjprm:promotionmember:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjprmPromotionMemberService.deleteBatch(ids);

        return RestResponse.success();
    }
}
