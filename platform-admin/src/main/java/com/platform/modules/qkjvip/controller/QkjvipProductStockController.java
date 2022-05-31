/*
 * 项目名称:platform-plus
 * 类名称:QkjvipProductStockController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-15 15:49:03        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipProductStockEntity;
import com.platform.modules.qkjvip.service.QkjvipProductStockService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@RestController
@RequestMapping("qkjvip/productstock")
public class QkjvipProductStockController extends AbstractController {
    @Autowired
    private QkjvipProductStockService qkjvipProductStockService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:productstock:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipProductStockEntity> list = qkjvipProductStockService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表现存产品
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listStock")
    public RestResponse listStock(@RequestParam Map<String, Object> params) {
        Page page = qkjvipProductStockService.queryPageStock(params);
        List <QkjvipProductStockEntity> details = qkjvipProductStockService.queryAllDetail(params);
        List <QkjvipProductStockEntity> mains = new ArrayList<>();
        mains= page.getRecords();
        List <QkjvipProductStockEntity> newmains = new ArrayList<>();
        for(QkjvipProductStockEntity pse:mains){
            List <QkjvipProductStockEntity> newDetail = new ArrayList<>();
            for (QkjvipProductStockEntity se:details){
                if(pse.getHousename()!=null&&se.getHousename()!=null&&se.getHousename().equals(pse.getHousename())){
                    newDetail.add(se);
                }
            }
            pse.setDetails(newDetail);
            newmains.add(pse);
        }
        page.setRecords(newmains);
        return RestResponse.success().put("page", page);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:productstock:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipProductStockService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:productstock:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipProductStockEntity qkjvipProductStock = qkjvipProductStockService.getById(id);

        return RestResponse.success().put("productstock", qkjvipProductStock);
    }

    /**
     * 新增
     *
     * @param qkjvipProductStock qkjvipProductStock
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:productstock:save")
    public RestResponse save(@RequestBody QkjvipProductStockEntity qkjvipProductStock) {

        qkjvipProductStockService.add(qkjvipProductStock);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipProductStock qkjvipProductStock
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:productstock:update")
    public RestResponse update(@RequestBody QkjvipProductStockEntity qkjvipProductStock) {

        qkjvipProductStockService.update(qkjvipProductStock);

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
    @RequiresPermissions("qkjvip:productstock:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipProductStockService.deleteBatch(ids);

        return RestResponse.success();
    }
}
