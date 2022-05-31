/*
 * 项目名称:platform-plus
 * 类名称:MemberController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-05-25 09:37:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.JedisUtil;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.common.validator.ValidatorUtils;
import com.platform.datascope.ContextHelper;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.oss.entity.UploadData;
import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportGroupdistributeService;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareSendrecordService;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysDictEntity;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysDictService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Controller
 *
 * @author liuqianru
 * @date 2022-05-25 09:37:24
 */
@RestController
@RequestMapping("/qkjvip/member")
public class MemberController_xin extends AbstractController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysDictService sysDictService;
    @Autowired
    private MemberTagsService memberTagsService;
    @Autowired
    private QkjvipTaglibsService qkjvipTaglibsService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserChannelService sysUserChannelService;
    @Autowired
    private QkjrptReportGroupdistributeService qkjrptReportGroupdistributeService;
    @Autowired
    private QkjrtsWelfareSendrecordService qkjrtsWelfareSendrecordService;
    @Autowired
    JedisUtil jedisUtil;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<MemberEntity> list = memberService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 所有会员列表
     *
     * @param memberQuery 查询参数
     * @return RestResponse
     */
    @PostMapping("/list")
    @RequiresPermissions(value = {"qkjvip:member:list", "qkjvip:memberbrandconsultant:list", "qkjvip:kolmember:list"}, logical = Logical.OR)
    public RestResponse list(@RequestBody MemberQueryEntity memberQuery) throws IOException {
        // 标签里置null以防参数过大
        if (memberQuery.getMembertags() != null && memberQuery.getMembertags().size() > 0) {
            for (int i = 0; i < memberQuery.getMembertags().size(); i++) {
                memberQuery.getMembertags().get(i).setTagList(null);
            }
        }
        // 部门权限
        if (memberQuery.getIsconsultant() != null && memberQuery.getIsconsultant() == 2) {
            memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:memberbrandconsultant:list"));
        } else {
            memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:member:list"));
        }
        // 添加人、介绍人、维护人权限判断
        if (!getUser().getUserName().contains("admin")) {  // 空默认是全部所有权限
            memberQuery.setCurrentmemberid(getUserId());
        }
        String queryJsonStr = JsonHelper.toJsonString(memberQuery, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
        System.out.println("会员检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            List<MemberEntity> memberList = new ArrayList<>();
            memberList = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
            Page page = new Page();
            page.setRecords(memberList);
            page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
            page.setSize(memberQuery.getPagesize() == null? 0 : memberQuery.getPagesize());
            page.setCurrent(memberQuery.getPageindex() == null? 0 : memberQuery.getPageindex());
            return RestResponse.success().put("page", page);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 根据主键查询详情
     *
     * @param memberId 主键
     * @return RestResponse
     */
    @GetMapping("/info/{memberId}")
    @RequiresPermissions(value = {"qkjvip:member:info", "qkjvip:memberbrandconsultant:info", "qkjvip:kolmember:info"}, logical = Logical.OR)
    public RestResponse info(@PathVariable("memberId") String memberId) throws IOException {
        Map params = new HashMap();
        params.put("memberId", memberId);
        String queryJsonStr = JsonHelper.toJsonString(params);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETINFO_URL, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            MemberEntity member = new MemberEntity();
            member = JSON.parseObject(resultObject.getString("memberentity"),MemberEntity.class);
            if (member.getSex() != null && (member.getSex() == 0 || member.getSex() == 3)) {
                member.setSex(null);
            }
            return RestResponse.success().put("member", member);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 根据手机号查询详情
     *
     * @param mobile
     * @return RestResponse
     */
    @RequestMapping("/getInfo")
    @RequiresPermissions(value = {"qkjvip:member:info", "qkjvip:memberbrandconsultant:info", "qkjvip:kolmember:info"}, logical = Logical.OR)
    public RestResponse getInfo(@RequestParam("mobile") String mobile) throws IOException {
        MemberQueryEntity memberQuery = new MemberQueryEntity();
        memberQuery.setMobile(mobile);
        String queryJsonStr = JsonHelper.toJsonString(memberQuery);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            List<MemberEntity> list = new ArrayList<>();
            list = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
            MemberEntity member = new MemberEntity();
            if (list.size() > 0) {  // 会员存在的情况下
                member = list.get(0);
                if (member.getSex() != null && (member.getSex() == 0 || member.getSex() == 3)) {
                    member.setSex(null);
                }
                // 查询该会员已发放的礼包
                Map<String, Object> params = new HashMap<>();
                params.put("memberid", member.getMemberId());
                params.put("scenetype", 2);
                List<QkjrtsWelfareSendrecordEntity> sendrecordList = qkjrtsWelfareSendrecordService.queryAll(params);
                if (sendrecordList.size() > 0) {
                    member.setWelfareid(sendrecordList.get(0).getWelfareid());
                    member.setWelfarename(sendrecordList.get(0).getWelfarename());
                    member.setStartvaliddate(sendrecordList.get(0).getStartvaliddate());
                    member.setEndvaliddate(sendrecordList.get(0).getEndvaliddate());
                }
            } else {  // 会员不存在返回检索的手机号
                member.setMobile(mobile);
                List<QkjvipMemberDatadepEntity> deptlist = new ArrayList<>();
                List<QkjvipMemberOrguserEntity> userlist = new ArrayList<>();
                member.setDeptlist(deptlist);
                member.setUserlist(userlist);
            }
            // 判断是否有添加会员权限
            boolean isaddpermission = false;
            // 判断手机号是否存在
            boolean ismobileexist = false;
            Map map = new HashMap();
            map.put("currentmemberid", getUserId());
            map.put("mobile", mobile);
            queryJsonStr = JsonHelper.toJsonString(map);
            resultPost = HttpClient.sendPost(Vars.MEMBER_ADDPERM_URL, queryJsonStr);
            resultObject = JSON.parseObject(resultPost);
            if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                // 大客户事业部（1215）及子部门和高端酒业务部（1308）及子部门的人默认就会有添加会员的权限，主责人就会变成他们自己
                isaddpermission = Boolean.parseBoolean(resultObject.get("isaddpermission").toString());
                ismobileexist = Boolean.parseBoolean(resultObject.get("ismobileexist").toString());
            } else {
                return RestResponse.error(resultObject.get("descr").toString());
            }
            return RestResponse.success().put("member", member).put("isaddpermission", isaddpermission).put("ismobileexist", ismobileexist);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 根据主键查询会员标签
     *
     * @param memberId 主键
     * @return RestResponse
     */
    @GetMapping("/otherTabInfo/{memberId}")
    @RequiresPermissions(value = {"qkjvip:member:info", "qkjvip:memberbrandconsultant:info", "qkjvip:kolmember:info"}, logical = Logical.OR)
    public RestResponse otherTabInfo(@PathVariable("memberId") String memberId) throws IOException {
        MemberEntity member = new MemberEntity();
        //获取会员标签
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        List<MemberTagsEntity> memberTagsEntities = memberTagsService.queryTagsList(params);
        List<MemberTagsQueryEntity> membertags = new ArrayList<>();
        if (memberTagsEntities.size() > 0) {   //会员打了标签的情况下
            for (int i = 0; i < memberTagsEntities.size(); i++) {
                if (i > 0 && memberTagsEntities.get(i).getTagGroupId().equals(memberTagsEntities.get(i - 1).getTagGroupId())) {
                    continue;
                }
                MemberTagsQueryEntity memberTagsQueryEntity = new MemberTagsQueryEntity();
                memberTagsQueryEntity.setTagGroupId(memberTagsEntities.get(i).getTagGroupId());
                memberTagsQueryEntity.setTagGroupName(memberTagsEntities.get(i).getTagGroupName());
                memberTagsQueryEntity.setTagType(memberTagsEntities.get(i).getTagType());
                memberTagsQueryEntity.setOptiontype(memberTagsEntities.get(i).getOptiontype());
                params.put("tagGroupId", memberTagsEntities.get(i).getTagGroupId());
                List<QkjvipTaglibsEntity> tagList = qkjvipTaglibsService.queryAll(params);
                memberTagsQueryEntity.setTagList(tagList);
                if (memberTagsEntities.get(i).getTagType() != null && memberTagsEntities.get(i).getTagType() == 2) {  // 选择型的，用户可再编辑
                    memberTagsQueryEntity.setTagIdList(Arrays.asList(memberTagsEntities.get(i).getItems().split(",")));
                    memberTagsQueryEntity.setTagValue("");
                } else if (memberTagsEntities.get(i).getTagType() != null && memberTagsEntities.get(i).getTagType() == 4) {  // 只读选择型的，用户不可编辑
                    List<String> tagIdList = new ArrayList<>();
                    memberTagsQueryEntity.setTagIdList(tagIdList);
                    memberTagsQueryEntity.setTagValue(memberTagsEntities.get(i).getTagValueText());
                } else {  // 输入型和只读型
                    List<String> tagIdList = new ArrayList<>();
                    memberTagsQueryEntity.setTagIdList(tagIdList);
                    memberTagsQueryEntity.setTagValue(memberTagsEntities.get(i).getTagValue());
                }
                membertags.add(memberTagsQueryEntity);
            }
        }
        member.setMembertags(membertags);
        return RestResponse.success().put("member", member);
    }

    /**
     * 保存会员信息
     *
     * @param member
     * @return RestResponse
     */
    @SysLog("保存会员信息")
    @PostMapping("/save")
    @RequiresPermissions(value = {"qkjvip:member:save", "qkjvip:memberbrandconsultant:save", "qkjvip:kolmember:save"}, logical = Logical.OR)
    public RestResponse save(@RequestBody MemberEntity member) {
        if (member.getMembertags() != null && member.getMembertags().size() > 0) {
            for (int i = 0; i < member.getMembertags().size(); i++) {
                member.getMembertags().get(i).setTagList(null);
            }
        }
        member.setAddUser(getUserId());
        member.setAddDept(getOrgNo());
        member.setAddTime(new Date());
        //调用数据清洗接口
        try {
            Object obj = JSONArray.toJSON(member);
            String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
            String resultPost = HttpClient.sendPost(Vars.MEMBER_ADD_URL, memberJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            if ("200".equals(resultObject.get("resultcode").toString())) {  //清洗成功
                member.setMemberId(resultObject.get("memberid").toString());
                // check该会员是否已发放了礼包福利
                // 用户设置了福利包
                if (!StringUtils.isEmpty(member.getWelfareid())) {
                    Map params = new HashMap();
                    params.put("memberid", member.getMemberId());
                    params.put("scenetype", 2);
                    List<QkjrtsWelfareSendrecordEntity> sendrecordList = qkjrtsWelfareSendrecordService.queryAll(params);
                    if (sendrecordList.size() == 0) {
                        // 发放福利
                        QkjrtsWelfareSendrecordEntity sendrecord = new QkjrtsWelfareSendrecordEntity();
                        sendrecord.setMemberid(member.getMemberId());
                        sendrecord.setWelfareid(member.getWelfareid());
                        sendrecord.setWelfarename(member.getWelfarename());
                        sendrecord.setScenetype(2);
                        sendrecord.setWelfaretype(8);
                        if (member.getPeriodtype() != null && member.getPeriodtype() == 1) { // 相对日期
                            Date startDate = DateUtils.setDate(new Date());
                            sendrecord.setStartvaliddate(startDate);
                            sendrecord.setEndvaliddate(DateUtils.addDateDays(startDate, member.getPerioddays()));
                        } else if (member.getPeriodtype() != null && member.getPeriodtype() == 0) {  // 绝对日期
                            sendrecord.setStartvaliddate(member.getStartvaliddate());
                            sendrecord.setEndvaliddate(member.getEndvaliddate());
                        }
                        qkjrtsWelfareSendrecordService.add(sendrecord);
                    }
                }
            } else {
                return RestResponse.error(resultObject.get("descr").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResponse.success().put("member", member);
    }

    /**
     * 修改用户
     *
     * @param member user
     * @return RestResponse
     */
    @SysLog("修改用户")
    @PostMapping("/update")
    @RequiresPermissions(value = {"qkjvip:member:update", "qkjvip:memberbrandconsultant:update", "qkjvip:kolmember:update"}, logical = Logical.OR)
    public RestResponse update(@RequestBody MemberEntity member) {
        if (member.getMembertags() != null && member.getMembertags().size() > 0) {
            for (int i = 0; i < member.getMembertags().size(); i++) {
                member.getMembertags().get(i).setTagList(null);
            }
        }
        try {
            Object obj = JSONArray.toJSON(member);
            String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
            String resultPost = HttpClient.sendPost(Vars.MEMBER_UPDATE_URL, memberJsonStr);
            System.out.println("会员信息更新：" + memberJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            if (!"200".equals(resultObject.get("resultcode").toString())) {  //修改不成功
                return RestResponse.error(resultObject.get("descr").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResponse.success().put("member", member);
    }

    /**
     * 添加协同业务员
     *
     * @param member user
     * @return RestResponse
     */
    @SysLog("添加协同业务员和协同部门")
    @PostMapping("/addTeamWorker")
    public RestResponse addTeamWorker(@RequestBody MemberEntity member) throws IOException {
        Map params = new HashMap();
        params.clear();
        params.put("currentmemberid", getUserId());
        params.put("memberid", member.getMemberId());
        try {
            String memberJsonStr = JsonHelper.toJsonString(params);
            String resultPost = HttpClient.sendPost(Vars.MEMBER_ADDUSER_URL, memberJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            if (!"200".equals(resultObject.get("resultcode").toString())) {  //添加不成功
                return RestResponse.error(resultObject.get("descr").toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RestResponse.success();
    }

    /**
     * 删除用户
     *
     * @param memberIds memberIds
     * @return RestResponse
     */
    @SysLog("删除用户")
    @PostMapping("/delete")
    @RequiresPermissions(value = {"qkjvip:member:delete", "qkjvip:memberbrandconsultant:delete", "qkjvip:kolmember:delete"}, logical = Logical.OR)
    public RestResponse delete(@RequestBody String[] memberIds) throws IOException {
        memberService.deleteBatch(memberIds);
        return RestResponse.success();
    }

    /**
     * 导出会员数据
     */
    @SysLog("导出会员")
    @RequestMapping("/export")
    @RequiresPermissions(value = {"qkjvip:member:export", "qkjvip:memberbrandconsultant:export", "qkjvip:kolmember:export"}, logical = Logical.OR)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, MemberQueryEntity memberQuery) throws IOException {
        if (memberQuery.getIsconsultant() != null && memberQuery.getIsconsultant() == 2) {
            memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:memberbrandconsultant:list"));
        } else {
            memberQuery.setListorgno(ContextHelper.getPermitDepts("qkjvip:member:list"));
        }
        if (!getUser().getUserName().contains("admin")) {  // 空默认是全部所有权限
            memberQuery.setCurrentmemberid(getUserId());
            memberQuery.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId())); // 0代表选择的是全部渠道传-1
        } else {
            memberQuery.setListmemberchannel("-1");
        }
        String queryJsonStr = JsonHelper.toJsonString(memberQuery, "yyyy-MM-dd HH:mm:ss");
        try {
            String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
            System.out.println("会员检索条件：" + queryJsonStr);
            //插入会员标签
            JSONObject resultObject = JSON.parseObject(resultPost);
            List<MemberExcelBcEntity> list = new ArrayList<>();
            if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                list = JSON.parseArray(resultObject.getString("listmember"), MemberExcelBcEntity.class);
            }
            ExportExcelUtils.exportExcel(list,"会员信息表","会员信息",MemberExcelBcEntity.class,"会员信息",response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出普通会员数据模板
     */
    @SysLog("普通会员导出模板")
    @RequestMapping("/exportTpl")
    @RequiresPermissions("qkjvip:member:exportTpl")
    public void exportTplExcel(HttpServletRequest request, HttpServletResponse response) {
        List<MemberExportEntity> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        List<SysDictEntity> dictList = new ArrayList<>();
        List<QkjvipMemberChannelEntity> memberChannelList = new ArrayList<>();
        List<SysUserChannelEntity> permChannelList = new ArrayList<>();
        String[] dictAttr = null;
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("会员信息表(*红色区域为必填信息)", "会员信息"), MemberExcelEntity.class, list);
            Sheet sheet = workbook.getSheet("会员信息");
            //获取第三行
            Row titlerow = sheet.getRow(1);
            //有多少列
            int cellNum = titlerow.getLastCellNum();
            for (int k = 0; k < cellNum; k++) {
                List<QkjvipTaglibsEntity> tagList = new ArrayList<>();
                //根据索引获取对应的列
                Cell cell = titlerow.getCell(k);
                String cellTitle = cell != null? cell.toString() : "";
                if (cell != null && !"".equals(cell.toString())) {
                    switch (cellTitle) {
                        case "联系方式":
                        case "会员名称":
                        case "维护人手机号":
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "会员渠道":
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
                                ExcelSelectListUtil.ExcelTo255(workbook, "hidden", 1, dictAttr, 2, 65535, k, k);
                            }
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "性别":
                            ExcelSelectListUtil.selectList(workbook, k, k, new String[]{"男","女"});
                            break;
                        case "是否潜在客户":
                            ExcelSelectListUtil.selectList(workbook, k, k, new String[]{"是","否"});
                            break;
                        case "会员性质":
                            params.clear();
                            params.put("code", "MEMBERNATURE");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            break;
                        case "会员来源":
                            params.clear();
                            params.put("code", "MEMBERSOURCE");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            break;
                        case "生日":
                            ExcelUtil.addComment(cell, "格式：2021-01-01", "xls");
                            break;
                        case "注册时间":
                            ExcelUtil.addComment(cell, "格式：2021-01-01", "xls");
                            break;
                        default:
                            break;
                    }
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "会员信息表." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入普通会员数据
     */
    @SysLog("普通会员数据导入")
    @RequestMapping("/import")
    @RequiresPermissions("qkjvip:member:import")
    public RestResponse importExcel(MultipartFile file, UploadData uploadData) {
        String fileName = file.getOriginalFilename();
        List<SysUserChannelEntity> permissionChannels = new ArrayList<>();
        if (uploadData == null) uploadData = new UploadData();
        String taskNo = "";
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            try {
                String channelIds = "";
                channelIds = sysUserChannelService.queryChannelIdByUserId(getUserId());
                Map params = new HashMap();
                params.put("userId", getUserId());
                if ("0".equals(channelIds) || getUser().getUserName().contains("admin")) {  // 所有渠道权限
                    params.put("queryPermission", "all");
                }
                permissionChannels = sysUserChannelService.queryPermissionChannels(params);
                List<MemberExcelEntity> list = ExportExcelUtils.importExcel(file, 1, 1,MemberExcelEntity.class);
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        int rownum = i + 4;
                        if (StringUtils.isBlank(list.get(i).getMobile())) {
                            return RestResponse.error("第" + rownum + "行手机号为空，请修改后重新上传！");
                        } else if (!ValidateUtil.validateMobilePhone(list.get(i).getMobile())) {
                            return RestResponse.error("第" + rownum + "行手机号不正确，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getServicename())) {
                            return RestResponse.error("第" + rownum + "行渠道为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysUserChannelEntity userChannel : permissionChannels) {
                                if (list.get(i).getServicename().equals(userChannel.getServicename())) {
                                    list.get(i).setMemberchannel(userChannel.getChannelId());
                                    ishave = true;
                                    break;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行渠道请选择下拉的渠道,请修改后重新上传！");
                            }
                        }
                        if (StringUtils.isBlank(list.get(i).getMemberName())) {
                            return RestResponse.error("第" + rownum + "行会员名称为空,请修改后重新上传！");
                        }
                        // 关系维护人手机校验
                        if (StringUtils.isBlank(list.get(i).getUserMobile())) {
                            return RestResponse.error("第" + rownum + "行维护人手机号为空，请修改后重新上传！");
                        } else if (!ValidateUtil.validateMobilePhone(list.get(i).getUserMobile().trim())) {
                            return RestResponse.error("第" + rownum + "行维护人手机号不正确，请修改后重新上传！");
                        } else {
                            Map userMap = new HashMap();
                            userMap.put("mobile", list.get(i).getUserMobile().trim());
                            List<SysUserEntity> userlist = sysUserService.queryAll(userMap);
                            if (userlist != null && userlist.size() > 0) {
                                list.get(i).setOrgUserid(userlist.get(0).getUserId());  // 主责业务员id
                                list.get(i).setOrgNo(userlist.get(0).getOrgNo()); // 主责业务员部门
                            } else {
                                return RestResponse.error("第" + rownum + "行维护人手机号在组织架构中不存在，请联系管理员！");
                            }
                        }
                        if (list.get(i).getBirthday() != null && list.get(i).getBirthday() != "") {
                            if (!ValidateUtil.validateDate(list.get(i).getBirthday())) {
                                return RestResponse.error("第" + rownum + "行生日格式不正确,请修改后重新上传！");
                            }
                        }
                        if (list.get(i).getRegTime() != null && list.get(i).getRegTime() != "") {
                            if (!ValidateUtil.validateDate(list.get(i).getRegTime())) {
                                return RestResponse.error("第" + rownum + "行注册时间格式不正确,请修改后重新上传！");
                            }
                        }
                        if (uploadData.getIscheckpass()) {  // true:非必填项做校验
                            if (StringUtils.isNotBlank(list.get(i).getIdcard())) {
                                String idCard = list.get(i).getIdcard();
                                if (!ValidateUtil.isIDCard(idCard.trim())) {  // 身份证校验不成功
                                    return RestResponse.error("第" + rownum + "行的身份证号不正确,请修改后重新上传！");
                                }
                            }
                        }
                        List<QkjvipTaglibsEntity> tagList = new ArrayList<>();
                        List<MemberTagsQueryEntity> membertags = new ArrayList<>();
                        if (StringUtils.isNotBlank(list.get(i).getTag1())) {
                            params.clear();
                            params.put("tagName", list.get(i).getTag1().trim());
                            params.put("tagGroupId", "4bd7f98607e44647bf409589b16836b4");
                            tagList = qkjvipTaglibsService.queryToCheck(params);
                            if (tagList.size() > 0) {
                                MemberTagsQueryEntity memberTagsQueryEntity = new MemberTagsQueryEntity();
                                memberTagsQueryEntity.setTagGroupId(tagList.get(0).getTagGroupId());
                                memberTagsQueryEntity.setTagGroupName(tagList.get(0).getTagGroupName());
                                List<String> tagIdList = new ArrayList<>();
                                tagIdList.add(tagList.get(0).getTagId());
                                memberTagsQueryEntity.setTagIdList(tagIdList);
                                memberTagsQueryEntity.setTagType(tagList.get(0).getTagType());
                                memberTagsQueryEntity.setOptiontype(tagList.get(0).getOptiontype());
                                memberTagsQueryEntity.setTagValue("");
                                membertags.add(memberTagsQueryEntity);
                            } else {
                                return RestResponse.error("第" + rownum + "行省份标签不正确,请修改后重新上传！");
                            }
                        }
                        if (StringUtils.isNotBlank(list.get(i).getTag2())) {
                            params.clear();
                            params.put("tagName", list.get(i).getTag2().trim());
                            params.put("tagGroupId", "9af1533bea3d4c89b856ad80e9d0e457");
                            tagList = qkjvipTaglibsService.queryToCheck(params);
                            if (tagList.size() > 0) {
                                MemberTagsQueryEntity memberTagsQueryEntity = new MemberTagsQueryEntity();
                                memberTagsQueryEntity.setTagGroupId(tagList.get(0).getTagGroupId());
                                memberTagsQueryEntity.setTagGroupName(tagList.get(0).getTagGroupName());
                                List<String> tagIdList = new ArrayList<>();
                                tagIdList.add(tagList.get(0).getTagId());
                                memberTagsQueryEntity.setTagIdList(tagIdList);
                                memberTagsQueryEntity.setTagType(tagList.get(0).getTagType());
                                memberTagsQueryEntity.setOptiontype(tagList.get(0).getOptiontype());
                                memberTagsQueryEntity.setTagValue("");
                                membertags.add(memberTagsQueryEntity);
                            } else {
                                return RestResponse.error("第" + rownum + "行市标签不正确,请修改后重新上传！");
                            }
                        }
                        list.get(i).setRealName(list.get(i).getMemberName());
                        list.get(i).setMembertags(membertags);
                        list.get(i).setAddUser(getUserId());
                        list.get(i).setAddDept(getOrgNo());
                        list.get(i).setAddTime(new Date());
                    }

                    Map map = new HashMap();
                    map.put("datalist", list);

                    //调用批量导入接口
                    Object obj = JSONObject.toJSON(map);
                    String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                    String resultPost = HttpClient.sendPost(Vars.MEMBER_IMPORT_URL, memberJsonStr);

                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if ("200".equals(resultObject.get("resultcode").toString())) {
                        taskNo = resultObject.getString("taskNo");
                    } else {
                        return RestResponse.error(resultObject.get("descr").toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return RestResponse.error("导入接口异常！");
            }
        }
        return RestResponse.success().put("msg", "导入成功！").put("batchno", taskNo);
    }

    /**
     * 导出政商务会员数据模板
     */
    @SysLog("政商务客户导出模板")
    @RequestMapping("/exportTpl2")
    @RequiresPermissions(value = {"qkjvip:memberbrandconsultant:exportTpl", "qkjvip:kolmember:exportTpl"}, logical = Logical.OR)
    public void exportTpl2(Boolean iskol, HttpServletResponse response) {
        List<MemberExcelBcEntity> list = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        List<SysDictEntity> dictList = new ArrayList<>();
        List<QkjvipMemberChannelEntity> memberChannelList = new ArrayList<>();
        List<SysUserChannelEntity> permChannelList = new ArrayList<>();
        String[] dictAttr = null;
        try {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("会员信息表(*红色区域为必填信息)", "会员信息"), MemberExcelBcEntity.class, list);
            Sheet sheet = workbook.getSheet("会员信息");
            //获取第三行
            Row titlerow = sheet.getRow(1);
            //有多少列
            int cellNum = titlerow.getLastCellNum();
            for (int k = 0; k < cellNum; k++) {
                //根据索引获取对应的列
                Cell cell = titlerow.getCell(k);
                String cellTitle = cell != null? cell.toString() : "";
                if (cell != null && !"".equals(cell.toString())) {
                    switch (cellTitle) {
                        case "收货地址-省":
                        case "收货地址-市":
                        case "收货地址-区县":
                        case "收货地址":
                        case "客户姓名":
                        case "客户手机号":
                        case "职务":
                        case "介绍人姓名":
                        case "介绍人手机号":
                        case "维护人姓名":
                        case "维护人手机号":
                        case "单位名称":
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "身份类型":
                            params.clear();
                            params.put("code", "IDENTITYGROUP");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "身份等级":
                            params.clear();
                            params.put("code", "IDENTITYLEVEL");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            if (dictAttr != null && dictAttr.length > 0) {
                                ExcelSelectListUtil.ExcelTo255(workbook, "hidden", 1, dictAttr, 2, 65535, k, k);
                            }
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "区域":
                            params.clear();
                            params.put("code", "AREAONE");
                            dictList = sysDictService.queryVNByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                String name = "";
                                if (StringUtils.isNotBlank(dictList.get(i).getName1())) {
                                    name += dictList.get(i).getName1();
                                }
                                if (StringUtils.isNotBlank(dictList.get(i).getName2())) {
                                    name += ("/" + dictList.get(i).getName2());
                                }
                                if (StringUtils.isNotBlank(dictList.get(i).getName3())) {
                                    name += ("/" + dictList.get(i).getName3());
                                }
                                dictAttr[i] = name;
                            }
                            if (dictAttr != null && dictAttr.length > 0) {
                                ExcelSelectListUtil.ExcelTo255(workbook, "hidden1", 1, dictAttr, 2, 65535, k, k);
                            }
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "获客渠道":
                            params.clear();
                            params.put("code", "GROUPORG");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            ExcelUtil.setStyles(cell, workbook);
                            break;
                        case "核心店/团购经销商":
                            params.clear();
                            List<QkjrptReportGroupdistributeEntity> distributeList = qkjrptReportGroupdistributeService.queryAll(params);
                            if (distributeList != null && distributeList.size() > 0) {
                                dictAttr = new String[distributeList.size()];
                                for (int i = 0; i < distributeList.size(); i++) {
                                    dictAttr[i] = distributeList.get(i).getDistributername();
                                }
                                ExcelSelectListUtil.ExcelTo255(workbook, "hidden2", 1, dictAttr, 2, 65535, k, k);
                            }
                            if (!iskol) {
                                ExcelUtil.setStyles(cell, workbook);
                            }
                            break;
                        case "民族":
                            params.clear();
                            params.put("code", "NATION");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            break;
                        case "性别":
                            ExcelSelectListUtil.selectList(workbook, k, k, new String[]{"男","女"});
                            break;
                        case "是否喜欢其他名酒":
                            ExcelSelectListUtil.selectList(workbook, k, k, new String[]{"否","是"});
                            break;
                        case "消费能力（价位段）":
                            params.clear();
                            params.put("code", "PRICESEGMENT");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            break;
                        case "兴趣爱好":
                            params.clear();
                            params.put("code", "HOBBY");
                            dictList = sysDictService.queryByCode(params);
                            dictAttr = new String[dictList.size()];
                            for (int i = 0; i < dictList.size(); i++) {
                                dictAttr[i] = dictList.get(i).getName();
                            }
                            ExcelSelectListUtil.selectList(workbook, k, k, dictAttr);
                            break;
                        default:
                            break;
                    }
                }
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode( "会员信息表." + ExportExcelUtils.ExcelTypeEnum.XLS.getValue(), "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入政商务会员数据
     */
    @SysLog("政商务会员导入")
    @RequestMapping("/import2")
    @RequiresPermissions(value = {"qkjvip:memberbrandconsultant:import", "qkjvip:kolmember:import"}, logical = Logical.OR)
    public RestResponse import2(MultipartFile file, UploadData uploadData) {
        String fileName = file.getOriginalFilename();
        if (uploadData == null) uploadData = new UploadData();
        String taskNo = "";
        if (StringUtils.isBlank(fileName)) {
            throw new BusinessException("请选择要导入的文件");
        } else {
            try {
                // 字段列表
                List<SysDictEntity> dictList = new ArrayList<>();
                Map params = new HashMap();
                params.put("status", 1);
                dictList = sysDictService.queryAll(params);
                // 三级区域级联列表
                List<SysDictEntity> cascadeDictList = new ArrayList<>();
                params.clear();
                params.put("code", "AREAONE");
                cascadeDictList = sysDictService.queryVNByCode(params);
                // 核心店/团购经销商列表
                params.clear();
                List<QkjrptReportGroupdistributeEntity> distributeList = new ArrayList<>();
                distributeList = qkjrptReportGroupdistributeService.queryAll(params);

                List<MemberExcelBcEntity> list = ExportExcelUtils.importExcel(file, 1, 1,MemberExcelBcEntity.class);
                if (list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        int rownum = i + 3;
                        // 单位名称校验
                        if (StringUtils.isBlank(list.get(i).getCompanyName())) {
                            return RestResponse.error("第" + rownum + "行单位名称为空,请修改后重新上传！");
                        }

                        // 省市区、详细地址校验
                        if (StringUtils.isBlank(list.get(i).getProvince())) {
                            return RestResponse.error("第" + rownum + "行收货地址-省为空，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getCity())) {
                            return RestResponse.error("第" + rownum + "行收货地址-市为空，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getDistrict())) {
                            return RestResponse.error("第" + rownum + "行收货地址-区县为空，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getAddress())) {
                            return RestResponse.error("第" + rownum + "行详细地址为空，请修改后重新上传！");
                        }

                        // 姓名、电话、职务校验
                        if (StringUtils.isBlank(list.get(i).getRealName())) {
                            return RestResponse.error("第" + rownum + "行客户姓名为空，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getMobile())) {
                            return RestResponse.error("第" + rownum + "行客户手机号为空，请修改后重新上传！");
                        } else if (!ValidateUtil.validateMobilePhone(list.get(i).getMobile().trim())) {
                            return RestResponse.error("第" + rownum + "行客户手机号不正确，请修改后重新上传！");
                        }
                        if (StringUtils.isBlank(list.get(i).getJobTitle())) {
                            return RestResponse.error("第" + rownum + "行职务为空，请修改后重新上传！");
                        }

                        // 身份类型校验
                        if (StringUtils.isBlank(list.get(i).getIdentitygroupname())) {
                            return RestResponse.error("第" + rownum + "行身份类型为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysDictEntity dict : dictList) {
                                if ("IDENTITYGROUP".equals(dict.getCode()) && list.get(i).getIdentitygroupname().equals(dict.getName())) {
                                    if (StringUtils.isNotBlank(dict.getValue())) {
                                        list.get(i).setIdentitygroup(Integer.parseInt(dict.getValue()));
                                    }
                                    ishave = true;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行身份类型不存在,请修改后重新上传！");
                            }
                            if (list.get(i).getIdentitygroupname().indexOf("A") == 0 || list.get(i).getIdentitygroupname().indexOf("B") == 0) {
                                list.get(i).setMembergroup("30"); // 政务
                            } else {
                                list.get(i).setMembergroup("20"); // 商务
                            }
                        }

                        // 身份级别校验
                        if (StringUtils.isBlank(list.get(i).getIdentitylevelname())) {
                            return RestResponse.error("第" + rownum + "行身份等级为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysDictEntity dict : dictList) {
                                if ("IDENTITYLEVEL".equals(dict.getCode()) && list.get(i).getIdentitylevelname().equals(dict.getName())) {
                                    if (StringUtils.isNotBlank(dict.getValue())) {
                                        list.get(i).setIdentitylevel(Integer.parseInt(dict.getValue()));
                                    }
                                    ishave = true;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行身份等级不存在,请修改后重新上传！");
                            }
                        }

                        // 三级区域校验
                        if (StringUtils.isBlank(list.get(i).getAreaname())) {
                            return RestResponse.error("第" + rownum + "行区域为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysDictEntity dict : cascadeDictList) {
                                String name = "";
                                if (StringUtils.isNotBlank(dict.getName1())) {
                                    name += dict.getName1();
                                }
                                if (StringUtils.isNotBlank(dict.getName2())) {
                                    name += ("/" + dict.getName2());
                                }
                                if (StringUtils.isNotBlank(dict.getName3())) {
                                    name += ("/" + dict.getName3());
                                }
                                if (list.get(i).getAreaname().equals(name)) {
                                    list.get(i).setAreaone(dict.getValue1());
                                    list.get(i).setAreatwo(dict.getValue2());
                                    list.get(i).setAreathree(dict.getValue3());
                                    ishave = true;
                                    break;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行请选择下拉的区域,请修改后重新上传！");
                            }
                        }

                        // 介绍人校验
                        if (StringUtils.isBlank(list.get(i).getReferrermobile())) {
                            return RestResponse.error("第" + rownum + "行介绍人手机号为空，请修改后重新上传！");
                        } else if (!ValidateUtil.validateMobilePhone(list.get(i).getReferrermobile().trim())) {
                            return RestResponse.error("第" + rownum + "行介绍人手机号不正确，请修改后重新上传！");
                        } else {
                            Map userMap = new HashMap();
                            userMap.put("mobile", list.get(i).getReferrermobile().trim());
                            List<SysUserEntity> userlist = sysUserService.queryAll(userMap);
                            if (userlist != null && userlist.size() > 0) {
                                list.get(i).setReferrer(userlist.get(0).getUserId()); // 介绍人姓名
                                list.get(i).setReferrerDept(userlist.get(0).getOrgNo()); // 介绍人部门
                            } else {
                                return RestResponse.error("第" + rownum + "行介绍人手机号在组织架构中不存在，请联系管理员！");
                            }
                        }

                        // 获客渠道校验
                        if (StringUtils.isBlank(list.get(i).getGrouporgname())) {
                            return RestResponse.error("第" + rownum + "行获客渠道为空,请修改后重新上传！");
                        } else {
                            boolean ishave = false;
                            for (SysDictEntity dict : dictList) {
                                if ("GROUPORG".equals(dict.getCode()) && list.get(i).getGrouporgname().equals(dict.getName())) {
                                    list.get(i).setGrouporg(dict.getValue());
                                    ishave = true;
                                    break;
                                }
                            }
                            if (!ishave) {
                                return RestResponse.error("第" + rownum + "行获客渠道不存在,请修改后重新上传！");
                            }
                        }

                        // 关系维护人手机校验
                        if (StringUtils.isBlank(list.get(i).getUserMobile())) {
                            return RestResponse.error("第" + rownum + "行维护人手机号为空，请修改后重新上传！");
                        } else if (!ValidateUtil.validateMobilePhone(list.get(i).getUserMobile().trim())) {
                            return RestResponse.error("第" + rownum + "行维护人手机号不正确，请修改后重新上传！");
                        } else {
                            Map userMap = new HashMap();
                            userMap.put("mobile", list.get(i).getUserMobile().trim());
                            List<SysUserEntity> userlist = sysUserService.queryAll(userMap);
                            if (userlist != null && userlist.size() > 0) {
                                list.get(i).setOrgUserid(userlist.get(0).getUserId());  // 主责业务员id
                                list.get(i).setOrgNo(userlist.get(0).getOrgNo()); // 主责业务员部门
                            } else {
                                return RestResponse.error("第" + rownum + "行维护人手机号在组织架构中不存在，请联系管理员！");
                            }
                        }

                        if (!uploadData.getIskol()) {
                            // 核心店/团购经销商校验
                            if (list.get(i).getGrouporg() != null && ("20".equals(list.get(i).getGrouporg()) || "40".equals(list.get(i).getGrouporg()))) {
                                if (StringUtils.isBlank(list.get(i).getDistributename())) {
                                    return RestResponse.error("第" + rownum + "行核心店或团购经销商为空,请修改后重新上传！");
                                } else {
                                    boolean ishave = false;
                                    for (QkjrptReportGroupdistributeEntity distribute : distributeList) {
                                        if (distribute.getDistributername().equals(list.get(i).getDistributename())) {
                                            list.get(i).setDistributeid(distribute.getId());
                                            ishave = true;
                                            break;
                                        }
                                    }
                                    if (!ishave) {
                                        return RestResponse.error("第" + rownum + "行核心店或团购经销商不存在,请修改后重新上传！");
                                    }
                                }
                            }
                        }

                        list.get(i).setMemberchannel(313);
                        list.get(i).setServicename("社会关系渠道");
                        list.get(i).setMemberName(list.get(i).getRealName());
                        list.get(i).setAddUser(getUserId());
                        list.get(i).setAddDept(getOrgNo());
                        list.get(i).setAddTime(new Date());
                    }

                    Map map = new HashMap();
                    map.put("datalist", list);

                    //调用批量导入接口
                    Object obj = JSONObject.toJSON(map);
                    String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                    String resultPost = HttpClient.sendPost(Vars.MEMBER_IMPORT_URL, memberJsonStr);

                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if ("200".equals(resultObject.get("resultcode").toString())) {
                        taskNo = resultObject.getString("taskNo");
                    } else {
                        return RestResponse.error(resultObject.get("descr").toString());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                return RestResponse.error("导入接口异常！");
            }
        }
        return RestResponse.success().put("msg", "导入成功！").put("batchno", taskNo);
    }

    /**
     * 根据手机查询列表
     */
    @RequestMapping("/queryAllByMobile")
    public RestResponse queryAllByMobile(@RequestParam Map<String, Object> params) throws IOException {
        String queryJsonStr = JsonHelper.toJsonString(params);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        List<MemberEntity> list = new ArrayList<>();
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            list = JSON.parseArray(resultObject.getString("listmember"), MemberEntity.class);
        }

        return RestResponse.success().put("list", list);
    }

    /**
     * 根据openid查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/selectMemByOpenid")
    public RestResponse selectMemByOpenid(@RequestParam Map<String, Object> params) {
        List<MemberEntity> list = memberService.selectMemberByOpenid(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 根据openid查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/selectMemByJueruMemberid")
    public RestResponse selectMemberByJuruMemberid(@RequestParam Map<String, Object> params) {
        List<MemberEntity> list = memberService.selectMemberByJuruMemberid(params);
        return RestResponse.success().put("list", list);
    }

    /**
     * 根据batchno获取会员信息
     * @param memberImportQueryEntity 查询参数
     * @return RestResponse
     */
    @RequestMapping("/getMemberInfoByBatchno")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse getMemberInfoByBatchno(@RequestBody MemberImportQueryEntity memberImportQueryEntity) throws IOException {
        Map params = new HashMap();
        params.put("batchno", memberImportQueryEntity.getBatchno());
        String queryJsonStr = JsonHelper.toJsonString(params);
        String resultPost = HttpClient.sendPost(Vars.MEMBER_IMPORTINFO_URL, queryJsonStr);
        System.out.println("导入批次查询：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            //查询进度
            Integer taskprogress = Integer.parseInt(resultObject.get("taskprogress") + "");
            if (taskprogress != null && taskprogress == 100) { // 进度100
                List<MemberEntity> memberlist = new ArrayList<>();
                memberlist = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
                CacheFactory.getCacheInstance().del("member-import-data");
                CacheFactory.getCacheInstance().put("member-import-data", resultObject.getString("listmember"));
                // 成功条数
                Integer completecount = Integer.parseInt(resultObject.get("completecount") + "");
                // 失败条数
                Integer uncompletecount = Integer.parseInt(resultObject.get("uncompletecount") + "");
                // 忽略条数
                Integer passcount = Integer.parseInt(resultObject.get("passcount") + "");
                // 说明配置了礼包
                if (!StringUtils.isEmpty(memberImportQueryEntity.getWelfareid())) {
                    // 异步批量给会员发送礼包
                    qkjrtsWelfareSendrecordService.addBatchByMember(memberImportQueryEntity, memberlist);
                }
                return RestResponse.success().put("taskprogress",100).put("memberlist", memberlist).put("completecount", completecount).put("uncompletecount", uncompletecount).put("passcount", passcount);
            } else {
                return RestResponse.success().put("taskprogress",taskprogress);
            }
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }
}
