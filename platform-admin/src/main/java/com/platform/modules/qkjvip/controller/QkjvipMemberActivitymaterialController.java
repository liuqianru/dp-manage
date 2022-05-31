/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivitymaterialController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-08 13:43:57        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberQueryEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymbsService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivitymaterialEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymaterialService;
import com.platform.modules.util.Vars;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-09-08 13:43:57
 */
@RestController
@RequestMapping("qkjvip/memberactivitymaterial")
public class QkjvipMemberActivitymaterialController extends AbstractController {
    @Autowired
    private QkjvipMemberActivitymaterialService qkjvipMemberActivitymaterialService;
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;
    @Autowired
    private MemberService memberService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberActivitymaterialEntity> list = qkjvipMemberActivitymaterialService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) throws IOException {
        if ("http://api.dc.crm.qkj.com.cn/".equals(Vars.APIURL)) {  // 新接口
            Page page = qkjvipMemberActivitymaterialService.queryPage(params);
            List<QkjvipMemberActivitymaterialEntity> list = page.getRecords();
            StringBuffer ids = new StringBuffer();
            String memberids = "";
            if (list.size() > 0) {
                for (QkjvipMemberActivitymaterialEntity materialEntity : list) {
                    ids.append(materialEntity.getMemberid() + ",");
                }
                memberids = ids.substring(0, ids.length() - 1);
                MemberQueryEntity memberQuery = new MemberQueryEntity();
                memberQuery.setIslimitperm(true);
                memberQuery.setIsconsultant(2);
                memberQuery.setMemberids(memberids);
                if (params.containsKey("membername") && params.get("membername") != null) {
                    memberQuery.setRealname(params.get("membername").toString());
                }
                if (params.containsKey("mobile") && params.get("mobile") != null) {
                    memberQuery.setMobile(params.get("mobile").toString());
                }
                List<MemberEntity> memberList = memberService.queryList(memberQuery);
                List<QkjvipMemberActivitymaterialEntity> materialList = new ArrayList<>();
                for (MemberEntity memberEntity : memberList) {  // 因为此处要要按照会员权限查看数据，所以会员的循环在外层
                    for (QkjvipMemberActivitymaterialEntity materialEntity : list) {
                        if (materialEntity.getMemberid() != null && memberEntity.getChildrenIds().contains(materialEntity.getMemberid())) {
                            materialEntity.setMembername(memberEntity.getMemberName());
                            materialEntity.setRealname(memberEntity.getRealName());
                            materialEntity.setMobile(memberEntity.getMobile());
                            materialEntity.setCompanyName(memberEntity.getCompanyName());
                            materialEntity.setJobTitle(memberEntity.getJobTitle());
                            materialList.add(materialEntity);
                        }
                    }
                }
                page.setRecords(materialList);
            }
            return RestResponse.success().put("page", page);
        } else {
            // 权限判断
            if (!getUser().getUserName().contains("admin")) {
                params.put("listorgno", ContextHelper.getPermitDepts("qkjvip:memberbrandconsultant:list")); // 权限部门
                params.put("currentuserid", getUserId());
            }
            Page page = qkjvipMemberActivitymaterialService.queryPage(params);
            return RestResponse.success().put("page", page);
        }
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberActivitymaterialEntity qkjvipMemberActivitymaterial = qkjvipMemberActivitymaterialService.getById(id);

        return RestResponse.success().put("memberactivitymaterial", qkjvipMemberActivitymaterial);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberActivitymaterial qkjvipMemberActivitymaterial
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipMemberActivitymaterialEntity qkjvipMemberActivitymaterial) {

        qkjvipMemberActivitymaterialService.add(qkjvipMemberActivitymaterial);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberActivitymaterial qkjvipMemberActivitymaterial
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipMemberActivitymaterialEntity qkjvipMemberActivitymaterial) {

        qkjvipMemberActivitymaterialService.update(qkjvipMemberActivitymaterial);

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
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberActivitymaterialService.deleteBatch(ids);

        return RestResponse.success();
    }
}
