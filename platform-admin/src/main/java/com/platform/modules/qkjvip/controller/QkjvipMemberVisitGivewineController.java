/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitGivewineController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-24 09:16:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.quartz.entity.TmpQkjvipMemberBasicEntity;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.service.SysDictService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.ExcelSelectListUtil;
import com.platform.modules.util.ExportExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2021-08-24 09:16:08
 */
@RestController
@RequestMapping("qkjvip/membervisitgivewine")
public class QkjvipMemberVisitGivewineController extends AbstractController {
    @Autowired
    private QkjvipMemberVisitGivewineService qkjvipMemberVisitGivewineService;
    @Autowired
    private QkjvipMemberVisitGivedetailService qkjvipMemberVisitGivedetailService;
    @Autowired
    private QkjvipMemberVisitFilesService qkjvipMemberVisitFilesService;
    @Autowired
    private SysUserChannelService sysUserChannelService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private QkjvipProductService qkjvipProductService;
    @Autowired
    private QkjvipMemberVisitService qkjvipMemberVisitService;
    @Autowired
    private QkjvipMemberVisitMaterialService qkjvipMemberVisitMaterialService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membervisitgivewine:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        // 每个人只可查看自己添加的数据和对应部门的数据
        params.put("dataScope", getDataScopeContex("qkjvip:membervisitgivewine:list", "T.addUser", "T.addDept"));
        List<QkjvipMemberVisitGivewineEntity> list = qkjvipMemberVisitGivewineService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membervisitgivewine:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        // 每个人只可查看自己添加的数据和对应部门的数据
        params.put("dataScope", getDataScopeContex("qkjvip:membervisitgivewine:list", "T.addUser", "T.addDept"));
        Page page = qkjvipMemberVisitGivewineService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membervisitgivewine:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine = qkjvipMemberVisitGivewineService.getById(id);
        Map map = new HashMap();
        map.put("mainid", id);
        List<QkjvipMemberVisitGivedetailEntity> list = new ArrayList<>();
        list = qkjvipMemberVisitGivedetailService.queryAll(map);
        qkjvipMemberVisitGivewine.setDetaillist(list);
        return RestResponse.success().put("membervisitgivewine", qkjvipMemberVisitGivewine);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberVisitGivewine qkjvipMemberVisitGivewine
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membervisitgivewine:save")
    public RestResponse save(@RequestBody QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine) {
        qkjvipMemberVisitGivewine.setAdduser(getUserId());
        qkjvipMemberVisitGivewine.setAdddept(getOrgNo());
        qkjvipMemberVisitGivewine.setAddtime(new Date());
        qkjvipMemberVisitGivewineService.add(qkjvipMemberVisitGivewine);

        for (QkjvipMemberVisitGivedetailEntity detailEntity : qkjvipMemberVisitGivewine.getDetaillist()) {
            detailEntity.setMainid(qkjvipMemberVisitGivewine.getId());
            detailEntity.setAdduser(getUserId());
            detailEntity.setAdddept(getOrgNo());
            detailEntity.setAddtime(new Date());
        }
        qkjvipMemberVisitGivedetailService.addBatch(qkjvipMemberVisitGivewine.getDetaillist());

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberVisitGivewine qkjvipMemberVisitGivewine
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membervisitgivewine:update")
    public RestResponse update(@RequestBody QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine) {
        qkjvipMemberVisitGivewine.setLmuser(getUserId());
        qkjvipMemberVisitGivewine.setLmdept(getOrgNo());
        qkjvipMemberVisitGivewine.setLmtime(new Date());
        qkjvipMemberVisitGivewineService.update(qkjvipMemberVisitGivewine);

        // 先删除产品列表
        qkjvipMemberVisitGivedetailService.deleteByMainId(qkjvipMemberVisitGivewine.getId());
        for (QkjvipMemberVisitGivedetailEntity detailEntity : qkjvipMemberVisitGivewine.getDetaillist()) {
            detailEntity.setMainid(qkjvipMemberVisitGivewine.getId());
            detailEntity.setAdduser(getUserId());
            detailEntity.setAdddept(getOrgNo());
            detailEntity.setAddtime(new Date());
        }
        qkjvipMemberVisitGivedetailService.addBatch(qkjvipMemberVisitGivewine.getDetaillist());

        return RestResponse.success();
    }

    /**
     * 更新签批文件
     *
     * @param qkjvipMemberVisitGivewine qkjvipMemberVisitGivewine
     * @return RestResponse
     */
    @SysLog("更新签批文件")
    @RequestMapping("/updateFile")
    @RequiresPermissions("qkjvip:membervisitgivewine:update")
    public RestResponse updateFile(@RequestBody QkjvipMemberVisitGivewineEntity qkjvipMemberVisitGivewine) {
        // 先删除签批文件
        qkjvipMemberVisitFilesService.deleteByMainId(qkjvipMemberVisitGivewine.getId());
        String filePath = "";
        for (QkjvipMemberVisitFilesEntity fileEntity : qkjvipMemberVisitGivewine.getFilelist()) {
            fileEntity.setMainid(qkjvipMemberVisitGivewine.getId());
            fileEntity.setAdduser(getUserId());
            fileEntity.setAdddept(getOrgNo());
            fileEntity.setAddtime(new Date());
            filePath += fileEntity.getFilepath() + ",";
        }
        qkjvipMemberVisitFilesService.addBatch(qkjvipMemberVisitGivewine.getFilelist());

        // 签批通过添加到拜访表
        Map map = new HashMap();
        map.put("mainid", qkjvipMemberVisitGivewine.getId());
        List<QkjvipMemberVisitGivedetailEntity> list = new ArrayList<>();
        list = qkjvipMemberVisitGivedetailService.queryAll(map);

        List<QkjvipMemberVisitEntity> importList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            QkjvipMemberVisitEntity visitEntity = new QkjvipMemberVisitEntity();
            visitEntity.setMemberId(list.get(i).getMemberid());
            visitEntity.setMobile(list.get(i).getMobile());
            visitEntity.setVisitStartDate(qkjvipMemberVisitGivewine.getVisittime());
            visitEntity.setVisitEndDate(qkjvipMemberVisitGivewine.getVisittime());
            visitEntity.setImages("".equals(filePath) ? "" : filePath.substring(0, filePath.length() - 1));
            visitEntity.setVisitType(qkjvipMemberVisitGivewine.getVisittype());  // 公关赠酒或节日赠礼
            visitEntity.setAddUser(getUserId());
            visitEntity.setAddDept(getOrgNo());
            visitEntity.setAddTime(new Date());
            visitEntity.setMaterialList(JSON.parseArray(list.get(i).getDetail(), QkjvipMemberVisitMaterialEntity.class));
            Date nowDate = new Date();
            if (qkjvipMemberVisitGivewine.getVisittime().after(nowDate)) {
                visitEntity.setVisitStatus(1);  // 计划拜访
            } else {
                visitEntity.setVisitStatus(2);  // 已完成
            }
            importList.add(visitEntity);
        }
        if (importList.size() > 0) {
            qkjvipMemberVisitService.addBatch(importList);
        }

        qkjvipMemberVisitGivewine.setStatus(1);  // 已签批
        qkjvipMemberVisitGivewine.setLmuser(getUserId());
        qkjvipMemberVisitGivewine.setLmdept(getOrgNo());
        qkjvipMemberVisitGivewine.setLmtime(new Date());
        qkjvipMemberVisitGivewineService.update(qkjvipMemberVisitGivewine);

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
    @RequiresPermissions("qkjvip:membervisitgivewine:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberVisitGivewineService.deleteBatch(ids);

        return RestResponse.success();
    }

    /**
     * 导出拜访数据模板
     */
    @SysLog("导出模板")
    @RequestMapping("/exportTpl")
    @RequiresPermissions("qkjvip:membervisit:exportTpl")
    public void exportTplExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam Map<String, Object> paramsMap) {
        List<QkjvipMemberVisitGwExportEntity> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        List<SysDictEntity> dictList = new ArrayList<>();
        List<QkjvipMemberChannelEntity> memberChannelList = new ArrayList<>();
        List<SysUserChannelEntity> permChannelList = new ArrayList<>();
        String[] dictAttr = null;
        try {
//            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("拜访信息表", "拜访信息"), QkjvipMemberVisitExportEntity.class, list);

            // 创建参数对象（用来设定excel得sheet得内容等信息）
            ExportParams deptExportParams = new ExportParams();
            // 设置sheet得名称
            deptExportParams.setSheetName("5".equals(paramsMap.get("visittype"))? "公关赠酒信息" : "节日赠礼信息");
            // 设置表格title
            deptExportParams.setTitle("5".equals(paramsMap.get("visittype"))? "公关赠酒信息表" : "节日赠礼信息表");
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", QkjvipMemberVisitGwExportEntity.class);
            // sheet中要填充得数据
            deptExportMap.put("data", list);

            ExportParams empExportParams = new ExportParams();
            empExportParams.setSheetName("参考格式");
            empExportParams.setTitle("5".equals(paramsMap.get("visittype"))? "公关赠酒信息表" : "节日赠礼信息表");
            // 创建sheet2使用得map
            Map<String, Object> empExportMap = new HashMap<>();
            empExportMap.put("title", empExportParams);
            empExportMap.put("entity", QkjvipMemberVisitGwExportEntity.class);
            empExportMap.put("data", this.exampleList());

            // 将sheet1、sheet2、sheet3使用得map进行包装
            List<Map<String, Object>> sheetsList = new ArrayList<>();
            sheetsList.add(deptExportMap);
            sheetsList.add(empExportMap);
            // 执行方法
            Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);

            //会员渠道
            String channelIds = "";
            channelIds = sysUserChannelService.queryChannelIdByUserId(getUserId());
            params.clear();
            params.put("userId", getUserId());
            if ("0".equals(channelIds) || getUser().getUserName().contains("admin")) {  // 所有渠道权限
                params.put("queryPermission", "all");
            }
            permChannelList = sysUserChannelService.queryPermissionChannels(params);
            dictAttr = new String[permChannelList.size()];
            for (int i = 0; i < permChannelList.size(); i++) {
                dictAttr[i] = permChannelList.get(i).getServicename().trim();
            }
            if (dictAttr != null && dictAttr.length > 0) {
                ExcelSelectListUtil.ExcelTo255(workbook, "hidden", 2, dictAttr, 3, 65535, 1, 1);
            }

            // 拜访方式
            params.clear();
            params.put("code", "VISITTYPE");
            dictList = sysDictService.queryByCode(params);
            dictAttr = new String[dictList.size()];
            for (int i = 0; i < dictList.size(); i++) {
                dictAttr[i] = dictList.get(i).getName();
            }
            ExcelSelectListUtil.selectList(workbook, 5, 5, dictAttr);

            // 物料名称
            params.clear();
            params.put("status", 1);
            if ("5".equals(paramsMap.get("visittype"))) {  // 公关赠酒
                params.put("producttype", 1);
            }
            List<QkjvipProductEntity> productList = qkjvipProductService.queryAll(params);
            if (productList != null && productList.size() > 0) {
                dictAttr = new String[productList.size()];
                for (int i = 0; i < productList.size(); i++) {
                    dictAttr[i] = productList.get(i).getProductname().trim();
                }
                if (dictAttr != null && dictAttr.length > 0) {
                    ExcelSelectListUtil.ExcelTo255(workbook, "hidden2", 3, dictAttr, 3, 65535, 6, 6);
                }
            }

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( ("5".equals(paramsMap.get("visittype"))? "公关赠酒信息表" : "节日赠礼信息表") + "." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入拜访数据
     *
     * @param params 参数
     * @return RestResponse
     */
    @SysLog("导入拜访数据")
    @RequestMapping("/importDates")
    public RestResponse importDates(@RequestParam Map<String, Object> params) {
        List<MemberEntity> memberList = JSON.parseArray((String) CacheFactory.getCacheInstance().get("member-import-data"), MemberEntity.class);
        List<QkjvipMemberVisitEntity> importList = JSON.parseArray((String) CacheFactory.getCacheInstance().get("visit-import-data"), QkjvipMemberVisitEntity.class);
        List<QkjvipMemberVisitGivedetailEntity> detaillist = new ArrayList<>();
        for (int i = 0; i < importList.size(); i++) {
            for (int j = 0; j < memberList.size(); j++) {
                if (memberList.get(j).getMobile().equals(importList.get(i).getMobile())) {
                    QkjvipMemberVisitGivedetailEntity givedetail = new QkjvipMemberVisitGivedetailEntity();
                    givedetail.setMemberid(memberList.get(j).getMemberId());
                    if (memberList.get(j).getMemberName() != null && memberList.get(j).getMemberName() != "") {
                        givedetail.setMembername(memberList.get(j).getMemberName());
                    } else {
                        givedetail.setMembername(memberList.get(j).getRealName());
                    }
                    givedetail.setMobile(memberList.get(j).getMobile());
                    givedetail.setDetail(JSON.toJSONString(importList.get(i).getMaterialList()));

                    givedetail.setAdduser(getUserId());
                    givedetail.setAdddept(getOrgNo());
                    givedetail.setAddtime(new Date());
                    detaillist.add(givedetail);
                    break;
                }
            }
        }
        return RestResponse.success().put("msg", "导入成功！").put("detaillist", detaillist);
    }

    private List<QkjvipMemberVisitGwExportEntity> exampleList() throws ParseException {
        List<QkjvipMemberVisitGwExportEntity> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 3; i++) {
            QkjvipMemberVisitGwExportEntity memberVisit = new QkjvipMemberVisitGwExportEntity();
            if (i == 0 || i == 1) {
                memberVisit.setMobile("13621255469");
                memberVisit.setMemberName("天使");
                memberVisit.setServicename("青稞荟小程序");
                memberVisit.setVisitStartDate(sdf.parse("2021-06-15 09:00:00"));
                memberVisit.setVisitEndDate(sdf.parse("2021-06-15 09:15:00"));
                memberVisit.setVisitType("电话");
            } else {
                memberVisit.setMobile("13621255468");
                memberVisit.setMemberName("天使1");
                memberVisit.setServicename("青稞荟小程序1");
                memberVisit.setVisitStartDate(sdf.parse("2021-06-16 09:00:00"));
                memberVisit.setVisitEndDate(sdf.parse("2021-06-16 09:15:00"));
                memberVisit.setVisitType("现场");
            }

            List<QkjvipMemberVisitMaterialEntity> materialList = new ArrayList<>();
            QkjvipMemberVisitMaterialEntity memberVisitMaterial = new QkjvipMemberVisitMaterialEntity();
            memberVisitMaterial.setName("金宝" + i);
            memberVisitMaterial.setNumber(2);
            memberVisitMaterial.setUnit("瓶");
            memberVisitMaterial.setUnitprice(388.0);
            materialList.add(memberVisitMaterial);
            memberVisit.setMaterialList(materialList);
            list.add(memberVisit);
        }
        return list;
    }
}
