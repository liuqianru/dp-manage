/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberVisitController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-01 11:39:36        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.oss.entity.UploadData;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.service.SysDictService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * @date 2020-12-01 11:39:36
 */
@RestController
@RequestMapping("qkjvip/membervisit")
public class QkjvipMemberVisitController extends AbstractController {
    @Autowired
    private QkjvipMemberVisitService qkjvipMemberVisitService;
    @Autowired
    private QkjvipMemberVisitMaterialService qkjvipMemberVisitMaterialService;
    @Autowired
    private QkjvipMemberIntentionorderService qkjvipMemberIntentionorderService;
    @Autowired
    private SysUserChannelService sysUserChannelService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private QkjvipMemberImportService qkjvipMemberImportService;
    @Autowired
    private QkjvipProductService qkjvipProductService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membervisit:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        params.put("dataScope", getDataScopeContex("qkjvip:membervisit:list", "T.add_user", "T.add_dept"));
        List<QkjvipMemberVisitEntity> list = qkjvipMemberVisitService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membervisit:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        // 每个人只可查看自己添加的数据和对应部门的数据
        params.put("dataScope", getDataScopeContex("qkjvip:membervisit:list", "T.add_user", "T.add_dept"));
        Page page = qkjvipMemberVisitService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membervisit:info")
    public RestResponse info(@PathVariable("id") String id) throws IOException {
//        QkjvipMemberVisitEntity qkjvipMemberVisit = qkjvipMemberVisitService.getById(id);  //因为要查询一些非此表的字段所以改为以下形式
        Map params = new HashMap();
        params.put("id", id);
        List<QkjvipMemberVisitEntity> list = qkjvipMemberVisitService.queryAll(params);
        QkjvipMemberVisitEntity qkjvipMemberVisit = new QkjvipMemberVisitEntity();
        if (list.size() > 0) {
            qkjvipMemberVisit = list.get(0);
        }
        //查询物料
        params.clear();
        params.put("visitId", id);
        qkjvipMemberVisit.setMaterialList(qkjvipMemberVisitMaterialService.queryAll(params));
        //查询订单
        QkjvipOrderOrderQuaryEntity order = new QkjvipOrderOrderQuaryEntity();
        order.setVisitid(id);
        if (!getUser().getUserName().contains("admin")) {  // 空默认是全部所有权限
            order.setCurrentmemberid(getUserId());
            order.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId())); // 0代表选择的是全部渠道传-1
        } else {
            order.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(order);
        String queryJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_LIST, queryJsonStr);
        System.out.println("订单检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        List<QkjvipOrderOrderEntity> orderList = new ArrayList<>();
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            orderList = JSON.parseArray(resultObject.getString("listorder"),QkjvipOrderOrderEntity.class);
        }
        qkjvipMemberVisit.setOrderList(orderList);
        return RestResponse.success().put("membervisit", qkjvipMemberVisit);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberVisit qkjvipMemberVisit
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membervisit:save")
    public RestResponse save(@RequestBody QkjvipMemberVisitEntity qkjvipMemberVisit) {
        Date nowDate = new Date();
        if (qkjvipMemberVisit.getVisitStartDate().after(nowDate) || qkjvipMemberVisit.getVisitEndDate().after(nowDate)) {
            if (StringUtils.isNotBlank(qkjvipMemberVisit.getContent())) {
                qkjvipMemberVisit.setVisitStatus(2);
            } else {
                qkjvipMemberVisit.setVisitStatus(1);  // 计划拜访
            }
        }
        if (qkjvipMemberVisit.getVisitEndDate().before(nowDate)) {
            if (StringUtils.isNotBlank(qkjvipMemberVisit.getContent())) {  // 时间在今天之前并且总结已填为已完成
                qkjvipMemberVisit.setVisitStatus(2);
            } else {
                qkjvipMemberVisit.setVisitStatus(3);  //总结未填为未拜访
            }
        }
        qkjvipMemberVisit.setAddUser(getUserId());
        qkjvipMemberVisit.setAddDept(getOrgNo());
        qkjvipMemberVisit.setAddTime(new Date());
        qkjvipMemberVisitService.add(qkjvipMemberVisit);

        // 新增物料
//        if (qkjvipMemberVisit.getMaterialList().size() > 0) {
//            for(QkjvipMemberVisitMaterialEntity mvm : qkjvipMemberVisit.getMaterialList()){
//                mvm.setVisitId(qkjvipMemberVisit.getId());
//                mvm.setAddUser(getUserId());
//                mvm.setAddDept(getOrgNo());
//                mvm.setAddTime(new Date());
//            }
//            qkjvipMemberVisitMaterialService.addBatch(qkjvipMemberVisit.getMaterialList());
//        }

        // 新增订单
//        if (qkjvipMemberVisit.getOrderList().size() > 0) {
//            for(QkjvipMemberIntentionorderEntity mio : qkjvipMemberVisit.getOrderList()){
//                mio.setVisitId(qkjvipMemberVisit.getId());
//                mio.setMemberId(qkjvipMemberVisit.getMemberId());
//                mio.setAddUser(getUserId());
//                mio.setAddDept(getOrgNo());
//                mio.setAddTime(new Date());
//            }
//            qkjvipMemberIntentionorderService.addBatch(qkjvipMemberVisit.getOrderList());
//        }

        return RestResponse.success().put("membervisit", qkjvipMemberVisit);
//        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberVisit qkjvipMemberVisit
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membervisit:update")
    public RestResponse update(@RequestBody QkjvipMemberVisitEntity qkjvipMemberVisit) {
        Date nowDate = new Date();
        if (qkjvipMemberVisit.getVisitStartDate().after(nowDate) || qkjvipMemberVisit.getVisitEndDate().after(nowDate)) {
            if (StringUtils.isNotBlank(qkjvipMemberVisit.getContent())) {
                qkjvipMemberVisit.setVisitStatus(2);
            } else {
                qkjvipMemberVisit.setVisitStatus(1);  // 计划拜访
            }
        }
        if (qkjvipMemberVisit.getVisitEndDate().before(nowDate)) {
            if (StringUtils.isNotBlank(qkjvipMemberVisit.getContent())) {  // 时间在今天之前并且总结已填为已完成
                qkjvipMemberVisit.setVisitStatus(2);
            } else {
                qkjvipMemberVisit.setVisitStatus(3);  //总结未填为未拜访
            }
        }
        qkjvipMemberVisit.setLmUser(getUserId());
        qkjvipMemberVisit.setLmDept(getOrgNo());
        qkjvipMemberVisit.setLmTime(new Date());
        qkjvipMemberVisitService.update(qkjvipMemberVisit);

        // 1.先删除物料表对应的数据
//        qkjvipMemberVisitMaterialService.deleteByVisitId(qkjvipMemberVisit.getId());
        // 2.再插入物料
//        if (qkjvipMemberVisit.getMaterialList().size() > 0) {
//            for(QkjvipMemberVisitMaterialEntity mvm : qkjvipMemberVisit.getMaterialList()){
//                mvm.setVisitId(qkjvipMemberVisit.getId());
//                mvm.setAddUser(getUserId());
//                mvm.setAddDept(getOrgNo());
//                mvm.setAddTime(new Date());
//            }
//            qkjvipMemberVisitMaterialService.addBatch(qkjvipMemberVisit.getMaterialList());
//        }

        // 1.先删除订单表对应的数据
//        qkjvipMemberIntentionorderService.deleteByVisitId(qkjvipMemberVisit.getId());
        // 2.再插入订单
//        if (qkjvipMemberVisit.getOrderList().size() > 0) {
//            for(QkjvipMemberIntentionorderEntity mio : qkjvipMemberVisit.getOrderList()){
//                mio.setVisitId(qkjvipMemberVisit.getId());
//                mio.setMemberId(qkjvipMemberVisit.getMemberId());
//                mio.setAddUser(getUserId());
//                mio.setAddDept(getOrgNo());
//                mio.setAddTime(new Date());
//            }
//            qkjvipMemberIntentionorderService.addBatch(qkjvipMemberVisit.getOrderList());
//        }

//        return RestResponse.success();
        return RestResponse.success().put("membervisit", qkjvipMemberVisit);
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:membervisit:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberVisitService.deleteBatch(ids);
        // 删除物料
        qkjvipMemberVisitMaterialService.deleteByVisitIds(ids);
        // 删除订单
        qkjvipMemberIntentionorderService.deleteByVisitIds(ids);
        return RestResponse.success();
    }

    /**
     * 根据主键关闭拜访
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/close/{id}")
    @RequiresPermissions("qkjvip:membervisit:close")
    public RestResponse close(@PathVariable("id") String id) {
        qkjvipMemberVisitService.closeById(id);
        return RestResponse.success();
    }

    /**
     * 导出拜访数据模板
     */
    @SysLog("导出模板")
    @RequestMapping("/exportTpl")
    @RequiresPermissions("qkjvip:membervisit:exportTpl")
    public void exportTplExcel(HttpServletRequest request, HttpServletResponse response) {
        List<QkjvipMemberVisitExportEntity> list = new ArrayList<>();
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
            deptExportParams.setSheetName("拜访信息");
            // 设置表格title
            deptExportParams.setTitle("拜访信息表");
            // 创建sheet1使用得map
            Map<String, Object> deptExportMap = new HashMap<>();
            // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
            deptExportMap.put("title", deptExportParams);
            // 模版导出对应得实体类型
            deptExportMap.put("entity", QkjvipMemberVisitExportEntity.class);
            // sheet中要填充得数据
            deptExportMap.put("data", list);

            ExportParams empExportParams = new ExportParams();
            empExportParams.setSheetName("参考格式");
            empExportParams.setTitle("拜访信息表");
            // 创建sheet2使用得map
            Map<String, Object> empExportMap = new HashMap<>();
            empExportMap.put("title", empExportParams);
            empExportMap.put("entity", QkjvipMemberVisitExportEntity.class);
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
            List<QkjvipProductEntity> productList = qkjvipProductService.queryAll(params);
            if (productList != null && productList.size() > 0) {
                dictAttr = new String[productList.size()];
                for (int i = 0; i < productList.size(); i++) {
                    dictAttr[i] = productList.get(i).getProductname().trim();
                }
                if (dictAttr != null && dictAttr.length > 0) {
                    ExcelSelectListUtil.ExcelTo255(workbook, "hidden2", 3, dictAttr, 3, 65535, 7, 7);
                }
            }

            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "拜访信息表." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入拜访数据
     */
    @SysLog("导入拜访会员")
    @RequestMapping("/import")
    @RequiresPermissions("qkjvip:membervisit:import")
    public RestResponse importExcel(MultipartFile file, UploadData uploadData) {
        String fileName = file.getOriginalFilename();
        String batchno = "";  // 批次号
        List<QkjvipMemberVisitExportEntity> list = new ArrayList<>();
        if (uploadData == null) uploadData = new UploadData();
        if (org.apache.commons.lang3.StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            try {
                List<SysUserChannelEntity> permissionChannels = new ArrayList<>();
                String channelIds = "";
                channelIds = sysUserChannelService.queryChannelIdByUserId(getUserId());
                Map params = new HashMap();
                params.put("userId", getUserId());
                if ("0".equals(channelIds) || getUser().getUserName().contains("admin")) {  // 所有渠道权限
                    params.put("queryPermission", "all");
                }
                permissionChannels = sysUserChannelService.queryPermissionChannels(params);
                list = ExportExcelUtils.importExcel(file, 1, 2, QkjvipMemberVisitExportEntity.class);
                List<QkjvipMemberImportEntity> importList = new ArrayList<>();
                if (list.size() > 0) {
                    batchno = UUID.randomUUID().toString().replaceAll("-", "");  // 批次号
                    for (int i = 0; i < list.size(); i++) {
                        QkjvipMemberImportEntity memberImport = new QkjvipMemberImportEntity();
                        int rownum = i + 4;
                        if (StringUtils.isBlank(list.get(i).getMobile())) {
                            return RestResponse.error("第" + rownum + "行手机号为空，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getServicename())) {
                            return RestResponse.error("第" + rownum + "渠道为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysUserChannelEntity userChannel : permissionChannels) {
                                if (list.get(i).getServicename().equals(userChannel.getServicename())) {
                                    memberImport.setServicename(userChannel.getServicename());
                                    memberImport.setMemberchannel(userChannel.getChannelId());
                                    ishave = true;
                                    break;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行渠道请选择下拉的渠道,请修改后重新上传！");
                            }
                        }
                        memberImport.setMobile(list.get(i).getMobile());
                        memberImport.setMemberName(list.get(i).getMemberName());
                        memberImport.setBatchno(batchno);
                        memberImport.setOrgUserid(getUserId());  // 导入默认所属人
                        memberImport.setOrgNo(getOrgNo()); //导入默认所属人部门
                        memberImport.setAddUser(getUserId());
                        memberImport.setAddDept(getOrgNo());
                        memberImport.setAddTime(new Date());
                        memberImport.setOfflineflag(1);
                        importList.add(memberImport);
                    }
                    if (importList.size() > 0) {
                        qkjvipMemberImportService.addBatch(importList); //批量导入临时表

                        Map map = new HashMap();
                        map.put("ischeckpass", uploadData.getIscheckpass());
                        map.put("importtype", uploadData.getImporttype());
                        map.put("datalist", importList);

                        //调用数据清洗接口
                        Object obj = JSONObject.toJSON(map);
                        String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                        String resultPost = HttpClient.sendPost(Vars.MEMBER_IMPORT_URL, memberJsonStr);

                        JSONObject resultObject = JSON.parseObject(resultPost);
                        if (!"200".equals(resultObject.get("resultcode").toString())) {  //清洗失败
                            return RestResponse.error(resultObject.get("descr").toString());
                        }
                        CacheFactory.getCacheInstance().del("visit-import-data");
                        CacheFactory.getCacheInstance().put("visit-import-data", JSON.toJSONString(list));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return RestResponse.error("导入接口异常！");
            }
        }
        return RestResponse.success().put("msg", "导入成功！").put("batchno", batchno);
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
        for (int i = 0; i < importList.size(); i++) {
            for (int j = 0; j < memberList.size(); j++) {
                if (memberList.get(j).getMobile().equals(importList.get(i).getMobile())) {
                    importList.get(i).setMemberId(memberList.get(j).getMemberId());
                    importList.get(i).setAddUser(getUserId());
                    importList.get(i).setAddDept(getOrgNo());
                    importList.get(i).setAddTime(new Date());
                    importList.get(i).setVisitStatus(2);  // 已拜访
                    break;
                }
            }
        }
        if (importList.size() > 0) {
            qkjvipMemberVisitService.addBatch(importList);
        }

        return RestResponse.success().put("msg", "导入成功！");
    }

    private List<QkjvipMemberVisitExportEntity> exampleList() throws ParseException {
        List<QkjvipMemberVisitExportEntity> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 3; i++) {
            QkjvipMemberVisitExportEntity memberVisit = new QkjvipMemberVisitExportEntity();
            if (i == 0 || i == 1) {
                memberVisit.setMobile("13621255469");
                memberVisit.setMemberName("天使");
                memberVisit.setServicename("青稞荟小程序");
                memberVisit.setVisitStartDate(sdf.parse("2021-06-15 09:00:00"));
                memberVisit.setVisitEndDate(sdf.parse("2021-06-15 09:15:00"));
                memberVisit.setVisitType("电话");
                memberVisit.setContent("天使测试");
            } else {
                memberVisit.setMobile("13621255468");
                memberVisit.setMemberName("天使1");
                memberVisit.setServicename("青稞荟小程序1");
                memberVisit.setVisitStartDate(sdf.parse("2021-06-16 09:00:00"));
                memberVisit.setVisitEndDate(sdf.parse("2021-06-16 09:15:00"));
                memberVisit.setVisitType("现场");
                memberVisit.setContent("天使测试1");
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
