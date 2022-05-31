/*
 * 项目名称:platform-plus
 * 类名称:MemberAddressController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/12 11:36            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.validator.ValidatorUtils;
import com.platform.common.validator.group.UpdateGroup;
import com.platform.modules.qkjvip.entity.MemberAddressEntity;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.service.MemberAddressService;
import com.platform.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MemberAddressController
 *
 * @author liuqianru
 * @date 2020/3/12 11:36
 */
@RestController
@RequestMapping("/qkjvip/maddress")
public class MemberAddressController extends AbstractController {
    @Autowired
    private MemberAddressService memberAddressService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:maddress:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MemberAddressEntity> list = memberAddressService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 所有会员地址列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:maddress:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = memberAddressService.queryPage(params);
        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param uuid 主键
     * @return RestResponse
     */
    @GetMapping("/info/{uuid}")
    @RequiresPermissions("qkjvip:maddress:info")
    public RestResponse info(@PathVariable("uuid") String uuid) {
        MemberAddressEntity maddress = memberAddressService.getById(uuid);
        return RestResponse.success().put("maddress", maddress);
    }

    /**
     * 保存会员地址信息
     *
     * @param maddress maddress
     * @return RestResponse
     */
    @SysLog("保存会员地址信息")
    @PostMapping("/save")
    @RequiresPermissions("qkjvip:maddress:save")
    public RestResponse save(@RequestBody MemberAddressEntity maddress) {
        Map<String, Object> params = new HashMap<>(2);
        maddress.setAddUser(getUserId());
        maddress.setAddDept(getOrgNo());
        maddress.setAddTime(new Date());
        memberAddressService.add(maddress, params);
        return RestResponse.success();
    }

    /**
     * 修改地址
     *
     * @param maddress maddress
     * @return RestResponse
     */
    @SysLog("修改地址")
    @PostMapping("/update")
    @RequiresPermissions("qkjvip:maddress:update")
    public RestResponse update(@RequestBody MemberAddressEntity maddress) {
        ValidatorUtils.validateEntity(maddress, UpdateGroup.class);
        Map<String, Object> params = new HashMap<>(2);
        memberAddressService.update(maddress, params);
        return RestResponse.success();
    }

    /**
     * 删除地址
     *
     * @param uuids uuids
     * @return RestResponse
     */
    @SysLog("删除地址")
    @PostMapping("/delete")
    @RequiresPermissions("qkjvip:maddress:delete")
    public RestResponse delete(@RequestBody String[] uuids) {
        memberAddressService.deleteBatch(uuids);
        return RestResponse.success();
    }

    /**
     * 删除地址
     *
     * @param memberId memberId
     * @return RestResponse
     */
    @SysLog("删除地址")
    @PostMapping("/deleteByMemberId")
    public RestResponse delete(String memberId) {
        memberAddressService.deleteByMemberId(memberId);
        return RestResponse.success();
    }
}
