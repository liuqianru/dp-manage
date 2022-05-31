/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWarehouseController.java
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
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareproService;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderWarehouseEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWarehouseService;
import com.platform.modules.util.ToolsUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-15 15:49:03
 */
@RestController
@RequestMapping("qkjvip/orderwarehouse")
public class QkjvipOrderWarehouseController extends AbstractController {
    @Autowired
    private QkjvipOrderWarehouseService qkjvipOrderWarehouseService;
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:orderwarehouse:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderWarehouseEntity> list = qkjvipOrderWarehouseService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看自提地点
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAllStock")
    public RestResponse queryAllStock(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderWarehouseEntity> list = qkjvipOrderWarehouseService.queryAllStock(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryList")
    @RequiresPermissions("qkjvip:orderwarehouse:list")
    public RestResponse queryList(@RequestParam Map<String, Object> params) {// 仓库权限
        Set<String> warlist = new HashSet<>();
        warlist= ContextHelper.setWarelistsm("qkjvip:orderwarehouse:list",getUserId());
        if (warlist.size()>0) params.put("warlists",warlist);

        List<QkjvipOrderWarehouseEntity> list = qkjvipOrderWarehouseService.queryList(params);

        StringBuilder str=new StringBuilder();
        list.forEach(item -> {
            str.append(item.getId() + ",");
        });
        Map<String, Object> map = new HashMap<>();
        map.put("wareidlist",str.toString());
        List<QkjvipOrderWarehouseEntity> statistcslist = qkjvipOrderWarehouseService.queryStatisticsList(map);
        return RestResponse.success().put("list", list).put("statistcslist",statistcslist);
    }

    /**
     * 查看一级所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryOneLevalList")
    public RestResponse queryOneLevalList(@RequestParam Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        // 仓库权限
        Set<String> warlist = new HashSet<>();
        warlist= ContextHelper.setWarelistsm("qkjvip:orderwarehouse:list",getUserId());
        if (warlist.size()>0) {
            map.put("warlists",warlist);
        }
        map.put("onelevel",0);
        List<QkjvipOrderWarehouseEntity> warelist = qkjvipOrderWarehouseService.queryAll(map);
        return RestResponse.success().put("warelist",warelist);
    }

    /**
     * 查看所有列表
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/querySelectList")
    public RestResponse querySelectList(@RequestBody  QkjvipOrderWarehouseEntity qkjvipOrderWarehouse) {
        Map<String, Object> params = new HashMap<>();
        params.putAll(ToolsUtil.getMapByBean(qkjvipOrderWarehouse));
        StringBuilder str=new StringBuilder();
        if(qkjvipOrderWarehouse.getWareidlist()!=null&&qkjvipOrderWarehouse.getWareidlist().length > 0&& !qkjvipOrderWarehouse.getWareidlist()[0].equals("")) {
            for (int i=0;i<qkjvipOrderWarehouse.getWareidlist().length;i++) {
                str.append(qkjvipOrderWarehouse.getWareidlist()[i] + ",");
            }
        }
        params.put("wareidlist",str.toString());
        List<QkjvipOrderWarehouseEntity> list = qkjvipOrderWarehouseService.querySelectList(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:orderwarehouse:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipOrderWarehouseService.queryPage(params);

        return RestResponse.success().put("page", page);
    }


    /**
     * 分页查询台账
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listmag")
    @RequiresPermissions("qkjvip:orderwarehouse:list")
    public RestResponse listmag(@RequestParam Map<String, Object> params) {
        Page page = qkjvipOrderWarehouseService.queryMagPage(params);
        List<QkjvipOrderWarehouseEntity> whs = page.getRecords();
        List<QkjvipOrderWareprohistoryEntity>  his = qkjvipOrderWareprohistoryService.queryMagAll(params);
        whs.stream().forEach(item ->{
            List<QkjvipOrderWareprohistoryEntity> hiss = his.stream().filter(hitem -> hitem.getWareid()!=null&&hitem.getWareid().equals(item.getId())).collect(Collectors.toList());
            item.setHistorys(hiss);
        });
        page.setRecords(whs);

        Map<String, Object> map = new HashMap<>();
        map.put("onelevel",0);
        List<QkjvipOrderWarehouseEntity> warelist = qkjvipOrderWarehouseService.queryAll(map);
        return RestResponse.success().put("page", page).put("warelist",warelist);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderwarehouse:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderWarehouseEntity qkjvipOrderWarehouse = qkjvipOrderWarehouseService.getById(id);
        Map<String, Object> params = new HashMap<>();
        List<QkjvipOrderWarehouseEntity> listall = qkjvipOrderWarehouseService.queryAll(params);
        return RestResponse.success().put("orderwarehouse", qkjvipOrderWarehouse).put("wareall",listall);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderWarehouse qkjvipOrderWarehouse
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:orderwarehouse:save")
    public RestResponse save(@RequestBody QkjvipOrderWarehouseEntity qkjvipOrderWarehouse) {
        qkjvipOrderWarehouse.setCreator(getUserId());
        qkjvipOrderWarehouse.setCreateon(new Date());
        qkjvipOrderWarehouseService.add(qkjvipOrderWarehouse);
        qkjvipOrderWarehouseService.updateRoots();
        return RestResponse.success();
    }

    // 修改所属于根目录
    /**
     * 修改
     *
     * @param qkjvipOrderWarehouse qkjvipOrderWarehouse
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderwarehouse:update")
    public RestResponse update(@RequestBody QkjvipOrderWarehouseEntity qkjvipOrderWarehouse) {

        qkjvipOrderWarehouseService.update(qkjvipOrderWarehouse);
        qkjvipOrderWarehouseService.updateRoots();
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
    @RequiresPermissions("qkjvip:orderwarehouse:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipOrderWarehouseService.deleteBatch(ids);

        return RestResponse.success();
    }
}
