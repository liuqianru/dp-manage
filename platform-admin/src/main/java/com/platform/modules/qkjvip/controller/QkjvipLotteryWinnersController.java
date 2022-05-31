/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryWinnersController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-07 16:06:26        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.service.QkjvipLotteryWinnersService;
import com.platform.modules.util.ExportExcelUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-07-07 16:06:26
 */
@RestController
@RequestMapping("qkjvip/lotterywinners")
public class QkjvipLotteryWinnersController extends AbstractController {
    @Autowired
    private QkjvipLotteryWinnersService qkjvipLotteryWinnersService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:lotterywinners:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipLotteryWinnersEntity> list = qkjvipLotteryWinnersService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:lotterywinners:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipLotteryWinnersService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:lotterywinners:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipLotteryWinnersEntity qkjvipLotteryWinners = qkjvipLotteryWinnersService.getById(id);

        return RestResponse.success().put("lotterywinners", qkjvipLotteryWinners);
    }

    /**
     * 新增
     *
     * @param qkjvipLotteryWinners qkjvipLotteryWinners
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:lotterywinners:save")
    public RestResponse save(@RequestBody QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {

        qkjvipLotteryWinnersService.add(qkjvipLotteryWinners);

        return RestResponse.success();
    }

    /**
     * 批量新增
     *
     * @param winnerList winnerList
     * @return RestResponse
     */
    @SysLog("批量新增")
    @RequestMapping("/addBatch")
    public RestResponse addBatch(@RequestBody List<QkjvipLotteryWinnersEntity> winnerList) {
        qkjvipLotteryWinnersService.addBatch(winnerList);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipLotteryWinners qkjvipLotteryWinners
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:lotterywinners:update")
    public RestResponse update(@RequestBody QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {

        qkjvipLotteryWinnersService.update(qkjvipLotteryWinners);

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
    @RequiresPermissions("qkjvip:lotterywinners:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipLotteryWinnersService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 根据mainid删除
     *
     * @param mainid mainid
     * @return RestResponse
     */
    @RequestMapping("/deleteByMainId")
    public RestResponse deleteByMainId(@RequestParam("mainid") String mainid) {
        qkjvipLotteryWinnersService.deleteByMainId(mainid);
        return RestResponse.success();
    }

    /**
     * 根据条件删除
     *
     * @return RestResponse
     */
    @RequestMapping("/delete2")
    public RestResponse deleteByMainId(@RequestBody QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {
        qkjvipLotteryWinnersService.delete2(qkjvipLotteryWinners);
        return RestResponse.success();
    }

    /**
     * 导出中奖人员
     */
    @SysLog("导出中奖人员")
    @RequestMapping("/export")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> params) {
        List<QkjvipLotteryWinnersEntity> list = qkjvipLotteryWinnersService.queryAll(params);
        try {
            ExportExcelUtils.exportExcel(list,"中奖人员信息表","中奖人员",QkjvipLotteryWinnersEntity.class,"中奖信息表",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
