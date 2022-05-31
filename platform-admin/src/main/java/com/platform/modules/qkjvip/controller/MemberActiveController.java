/*
 * 项目名称:platform-plus
 * 类名称:MemberActiveController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/13 13:27            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.qkjvip.entity.MemberActiveEntity;
import com.platform.modules.qkjvip.service.MemberActiveService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MemberActiveController
 *
 * @author liuqianru
 * @date 2020/3/13 13:27
 */
@RestController
@RequestMapping("/qkjvip/mactive")
public class MemberActiveController extends AbstractController {
    @Autowired
    private MemberActiveService memberActiveService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:mactive:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MemberActiveEntity> list = memberActiveService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 所有拜访列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:mactive:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {

        //如需数据权限，在参数中添加DataScope
//        params.put("dataScope", getDataScope());

        Page page = memberActiveService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param uuid 主键
     * @return RestResponse
     */
    @GetMapping("/info/{uuid}")
    @RequiresPermissions("qkjvip:mactive:info")
    public RestResponse info(@PathVariable("uuid") String uuid) {
        MemberActiveEntity mactive = memberActiveService.getById(uuid);
        return RestResponse.success().put("mactive", mactive);
    }

    /**
     * 保存活动信息
     *
     * @param mactive mactive
     * @return RestResponse
     */
    @SysLog("保存活动记录信息")
    @PostMapping("/save")
    @RequiresPermissions("qkjvip:mactive:save")
    public RestResponse save(@RequestBody MemberActiveEntity mactive) {

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        mactive.setAddUser(getUserId());
        mactive.setAddDept(getOrgNo());
        mactive.setAddTime(new Date());
        memberActiveService.add(mactive, params);

        return RestResponse.success();
    }

    /**
     * 修改活动信息
     *
     * @param mactive mactive
     * @return RestResponse
     */
    @SysLog("修改活动记录")
    @PostMapping("/update")
    @RequiresPermissions("qkjvip:mactive:update")
    public RestResponse update(@RequestBody MemberActiveEntity mactive) {
        ValidatorUtils.validateEntity(mactive, UpdateGroup.class);

        Map<String, Object> params = new HashMap<>(2);
        params.put("dataScope", getDataScope());

        memberActiveService.update(mactive, params);

        return RestResponse.success();
    }

    /**
     * 删除活动信息
     *
     * @param uuids uuids
     * @return RestResponse
     */
    @SysLog("删除活动信息")
    @PostMapping("/delete")
    @RequiresPermissions("qkjvip:mactive:delete")
    public RestResponse delete(@RequestBody String[] uuids) {
        memberActiveService.deleteBatch(uuids);

        return RestResponse.success();
    }
}
