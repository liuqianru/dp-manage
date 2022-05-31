/*
 * 项目名称:platform-plus
 * 类名称:MemberCommuController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 14:18            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.qkjvip.entity.MemberActiveEntity;
import com.platform.modules.qkjvip.entity.MemberCommuEntity;
import com.platform.modules.qkjvip.service.MemberCommuService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MemberCommuController
 *
 * @author liuqianru
 * @date 2020/3/13 14:18
 */
@RestController
@RequestMapping("/qkjvip/mcommu")
public class MemberCommuController extends AbstractController {
    @Autowired
    private MemberCommuService memberCommuService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:mactive:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MemberCommuEntity> list = memberCommuService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 所有沟通列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:mcommu:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
//        params.put("dataScope", getDataScope());

        Page page = memberCommuService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param uuid 主键
     * @return RestResponse
     */
    @GetMapping("/info/{uuid}")
    @RequiresPermissions("qkjvip:mcommu:info")
    public RestResponse info(@PathVariable("uuid") String uuid) {
        MemberCommuEntity mcommu = memberCommuService.getById(uuid);
        return RestResponse.success().put("mcommu", mcommu);
    }

    /**
     * 保存沟通信息
     *
     * @param mcommu mcommu
     * @return RestResponse
     */
    @SysLog("保存沟通记录信息")
    @PostMapping("/save")
    @RequiresPermissions("qkjvip:mcommu:save")
    public RestResponse save(@RequestBody MemberCommuEntity mcommu) {

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        mcommu.setAddUser(getUserId());
        mcommu.setAddDept(getOrgNo());
        mcommu.setAddTime(new Date());
        memberCommuService.add(mcommu, params);

        return RestResponse.success();
    }

    /**
     * 修改沟通信息
     *
     * @param mcommu mcommu
     * @return RestResponse
     */
    @SysLog("修改沟通记录")
    @PostMapping("/update")
    @RequiresPermissions("qkjvip:mmcommu:update")
    public RestResponse update(@RequestBody MemberCommuEntity mcommu) {
        ValidatorUtils.validateEntity(mcommu, UpdateGroup.class);

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        memberCommuService.update(mcommu, params);

        return RestResponse.success();
    }

    /**
     * 删除沟通信息
     *
     * @param uuids uuids
     * @return RestResponse
     */
    @SysLog("删除沟通信息")
    @PostMapping("/delete")
    @RequiresPermissions("qkjvip:mcommu:delete")
    public RestResponse delete(@RequestBody String[] uuids) {
        memberCommuService.deleteBatch(uuids);

        return RestResponse.success();
    }
}
