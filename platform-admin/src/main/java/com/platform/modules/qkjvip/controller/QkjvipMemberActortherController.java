/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActortherController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.QkjvipMemberActotherbatchEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActotherbatchService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActortherEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActortherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@RestController
@RequestMapping("qkjvip/memberactorther")
public class QkjvipMemberActortherController extends AbstractController {
    @Autowired
    private QkjvipMemberActortherService qkjvipMemberActortherService;
    @Autowired
    private QkjvipMemberActotherbatchService qkjvipMemberActotherbatchService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:memberactorther:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActortherEntity> list = qkjvipMemberActortherService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:memberactorther:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberActortherService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:memberactorther:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActortherEntity qkjvipMemberActorther = qkjvipMemberActortherService.getById(id);

        //最新一批次人数
        Map<String, Object> params=new HashMap<>();
        params.put("mainId",qkjvipMemberActorther.getId());
        List<QkjvipMemberActotherbatchEntity> bats=qkjvipMemberActotherbatchService.queryAll(params);
        if(bats!=null&&bats.size()>0){
            QkjvipMemberActotherbatchEntity newbat=new QkjvipMemberActotherbatchEntity();
            newbat=bats.get(0);
            qkjvipMemberActorther.setPepnum(newbat.getTotlepeoplenum());
        }

        return RestResponse.success().put("memberactorther", qkjvipMemberActorther);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActorther qkjvipMemberActorther
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:memberactorther:save")
    public RestResponse save(@RequestBody QkjvipMemberActortherEntity qkjvipMemberActorther) {

        qkjvipMemberActortherService.add(qkjvipMemberActorther);
        Integer peplenum=qkjvipMemberActorther.getPepnum();//限制人数
        //添加第一批次
        QkjvipMemberActotherbatchEntity batch = new QkjvipMemberActotherbatchEntity();
        batch.setMainId(qkjvipMemberActorther.getId());
        batch.setBatchNum(1);
        batch.setMdytime(new Date());
        batch.setMdyuser(getUserId());
        batch.setTotlepeoplenum(peplenum);
        qkjvipMemberActotherbatchService.save(batch);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActorther qkjvipMemberActorther
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:memberactorther:update")
    public RestResponse update(@RequestBody QkjvipMemberActortherEntity qkjvipMemberActorther) {

        qkjvipMemberActortherService.update(qkjvipMemberActorther);
        Integer peplenum=qkjvipMemberActorther.getPepnum();//限制人数
        //修改最新一批次人数
        Map<String, Object> params=new HashMap<>();
        params.put("mainId",qkjvipMemberActorther.getId());
        List<QkjvipMemberActotherbatchEntity> bats=qkjvipMemberActotherbatchService.queryAll(params);
        if(bats!=null&&bats.size()>0){
            QkjvipMemberActotherbatchEntity newbat=new QkjvipMemberActotherbatchEntity();
            newbat=bats.get(0);
            newbat.setTotlepeoplenum(peplenum);
            qkjvipMemberActotherbatchService.update(newbat);
        }

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
    @RequiresPermissions("qkjvip:memberactorther:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActortherService.deleteBatch(ids);

        return RestResponse.success();
    }
}
