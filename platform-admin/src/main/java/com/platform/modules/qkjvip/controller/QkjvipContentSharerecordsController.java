/*
 * 项目名称:platform-plus
 * 类名称:QkjvipContentSharerecordsController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-01 17:01:46        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegralruleEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberIntegralruleService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipContentSharerecordsEntity;
import com.platform.modules.qkjvip.service.QkjvipContentSharerecordsService;
import com.platform.modules.util.EntityUtils;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-04-01 17:01:46
 */
@RestController
@RequestMapping("qkjvip/contentsharerecords")
public class QkjvipContentSharerecordsController extends AbstractController {
    @Autowired
    private QkjvipContentSharerecordsService qkjvipContentSharerecordsService;
    @Autowired
    private QkjvipMemberIntegralruleService qkjvipMemberIntegralruleService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:contentsharerecords:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipContentSharerecordsEntity> list = qkjvipContentSharerecordsService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:contentsharerecords:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipContentSharerecordsService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:contentsharerecords:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipContentSharerecordsEntity qkjvipContentSharerecords = qkjvipContentSharerecordsService.getById(id);

        return RestResponse.success().put("contentsharerecords", qkjvipContentSharerecords);
    }

    /**
     * 新增
     *
     * @param qkjvipContentSharerecords qkjvipContentSharerecords
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:contentsharerecords:save")
    public RestResponse save(@RequestBody QkjvipContentSharerecordsEntity qkjvipContentSharerecords) {

        qkjvipContentSharerecordsService.add(qkjvipContentSharerecords);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipContentSharerecords qkjvipContentSharerecords
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:contentsharerecords:update")
    public RestResponse update(@RequestBody QkjvipContentSharerecordsEntity qkjvipContentSharerecords) {

        qkjvipContentSharerecordsService.update(qkjvipContentSharerecords);

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
    @RequiresPermissions("qkjvip:contentsharerecords:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipContentSharerecordsService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 分享得积分
     *
     * @param contentSharerecordsEntity contentSharerecordsEntity
     * @return RestResponse
     */
    @RequestMapping("/addIntegral")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse addIntegral(@RequestBody QkjvipContentSharerecordsEntity contentSharerecordsEntity) throws IOException {
        List<QkjvipContentSharerecordsEntity> list = qkjvipContentSharerecordsService.queryAll(EntityUtils.entityToMap(contentSharerecordsEntity));
        if (list.size() == 0) {  // 还未加分
            Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();  //设置回滚点
            qkjvipContentSharerecordsService.add(contentSharerecordsEntity);

            Map map = new HashMap();
            QkjvipMemberIntegralruleEntity qkjvipMemberIntegralrule = new QkjvipMemberIntegralruleEntity();
            if (contentSharerecordsEntity.getRecordValue() == 1) {  // 阅读文章积分
                map.put("remark", "阅读文章得积分");
                qkjvipMemberIntegralrule = qkjvipMemberIntegralruleService.getById("8c585e6ae207b1aa8e0d72a9f56d1dc0");
            } else if (contentSharerecordsEntity.getRecordValue() == 2) {  // 分享得积分
                map.put("remark", "分享文章得积分");
                qkjvipMemberIntegralrule = qkjvipMemberIntegralruleService.getById("37f41bf2a2605825fd9642b1ff104655");
            }
            map.put("crmmemberid", contentSharerecordsEntity.getMemberId());
            map.put("actiontype", 15);
            map.put("integral", qkjvipMemberIntegralrule.getIntegral());

            if (qkjvipMemberIntegralrule.getIntegral() != null && qkjvipMemberIntegralrule.getIntegral() > 0) {
                String queryJsonStr = JsonHelper.toJsonString(map);
                String resultPost = HttpClient.sendPost(Vars.CONTENT_SHARE_URL, queryJsonStr);
                JSONObject resultObject = JSON.parseObject(resultPost);
                if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                    System.out.println((contentSharerecordsEntity.getRecordValue() == 1 ? "阅读文章" : "分享文章") + "获得积分成功！");
                    return RestResponse.success();
                } else {
                    System.out.println((contentSharerecordsEntity.getRecordValue() == 1 ? "阅读文章" : "分享文章") + "获得积分失败！");
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                    return RestResponse.error(resultObject.get("descr").toString());
                }
            }
        }

        return RestResponse.success();
    }
}
