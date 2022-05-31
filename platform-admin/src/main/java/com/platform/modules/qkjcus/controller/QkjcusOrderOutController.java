/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderOutController.java
 * 包名称:com.platform.modules.qkjcus.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-28 10:14:18             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareproService;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjcus.entity.QkjcusOrderOutEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderOutService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 
 * @date 2021-12-28 10:14:18
 */
@RestController
@RequestMapping("qkjcus/orderout")
public class QkjcusOrderOutController extends AbstractController {
    @Autowired
    private QkjcusOrderOutService qkjcusOrderOutService;
    @Autowired
    private QkjvipOrderWareproService qkjvipOrderWareproService;
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;
    @Autowired
    private QkjtakeRWineService qkjtakeRWineService;
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjcus:orderout:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjcusOrderOutEntity> list = qkjcusOrderOutService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjcus:orderout:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjcusOrderOutService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjcus:orderout:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjcusOrderOutEntity qkjcusOrderOut = qkjcusOrderOutService.getById(id);

        return RestResponse.success().put("orderout", qkjcusOrderOut);
    }

    /**
     * 新增
     *
     * @param qkjcusOrderOut qkjcusOrderOut
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(@RequestBody QkjcusOrderOutEntity qkjcusOrderOut) {
        if (qkjcusOrderOut!=null && qkjcusOrderOut.getWineid()!=null&&!qkjcusOrderOut.getWineid().equals("") && qkjcusOrderOut.getWdata()!=null && !qkjcusOrderOut.getWdata().equals("")) {
            //修改酒证资料
            QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
            wine.setWdata(qkjcusOrderOut.getWdata());
            wine.setId(qkjcusOrderOut.getWineid());
            qkjwineRInfoService.update(wine);
        }
        qkjcusOrderOut.setAddUser(getUserId());
        qkjcusOrderOut.setAddTime(new Date());
        qkjcusOrderOutService.add(qkjcusOrderOut);

        String wareproid = qkjcusOrderOut.getWareproid();
        // 更新提货表状态
        QkjtakeRWineEntity qwe = new QkjtakeRWineEntity();
        qwe.setState(2);
        qwe.setWareproid(wareproid);
        qwe.setOperationtime(new Date());
        qwe.setOperator(getUserId());
        qkjtakeRWineService.updateTakeStateById(qwe);


        QkjvipOrderWareproEntity qow = new QkjvipOrderWareproEntity();
        qow.setId(wareproid);
        qow.setDisabled(1);
        qkjvipOrderWareproService.update(qow);

        //历史
        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
        qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjcusOrderOut.getStockmsg()), QkjvipOrderWareprohistoryEntity.class);
        if (qkjvipOrderWareprohistory!=null && qkjvipOrderWareprohistory.getId()!=null&&!qkjvipOrderWareprohistory.getId().equals(""))qkjvipOrderWareprohistory.setId(null);
        if (qkjvipOrderWareprohistory==null) {
            qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
            qkjvipOrderWareprohistory.setWareid("000");
        }
        qkjvipOrderWareprohistory.setState(6); // 赠送出库
        qkjvipOrderWareprohistory.setCreateon(new Date());
        qkjvipOrderWareprohistory.setCreator(getUserId());
        qkjvipOrderWareprohistory.setOrderid(qkjcusOrderOut.getId());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjcusOrderOut qkjcusOrderOut
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjcus:orderout:update")
    public RestResponse update(@RequestBody QkjcusOrderOutEntity qkjcusOrderOut) {

        qkjcusOrderOutService.update(qkjcusOrderOut);

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
    @RequiresPermissions("qkjcus:orderout:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjcusOrderOutService.deleteBatch(ids);

        return RestResponse.success();
    }
}
