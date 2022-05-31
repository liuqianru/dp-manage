/*
 * 项目名称:platform-plus
 * 类名称:SysOrgController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-01-21 11:29:22        李鹏军     初版做成
 *
 * Copyright (c) 2018-2019 微同软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.cache.SysDBCacheLogic;
import com.platform.modules.qkjvip.entity.QkjvipMemberDatadepEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberDatadepService;
import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysCacheService;
import com.platform.modules.sys.service.SysOrgService;
import com.platform.modules.sys.service.SysOrgUpdatelogService;
import com.platform.modules.util.JSONUtil;
import com.platform.modules.webservices.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 组织机构Controller
 *
 * @author 李鹏军
 * @date 2019-01-21 11:29:22
 */
@RestController
@RequestMapping("sys/org")
@Component("quartzorg")
public class SysOrgController extends AbstractController {
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private SysOrgUpdatelogService sysOrgUpdatelogService;
    @Autowired
    private QkjvipMemberDatadepService qkjvipMemberDatadepService;
    @Autowired
    SysCacheService sysCacheService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysOrgEntity> list = sysOrgService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 根据主键查询详情
     *
     * @param orgNo 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{orgNo}")
    @RequiresPermissions("sys:org:info")
    public RestResponse info(@PathVariable("orgNo") String orgNo) {
        SysOrgEntity sysOrg = sysOrgService.getById(orgNo);

        return RestResponse.success().put("org", sysOrg);
    }

    /**
     * 保存
     *
     * @param sysOrg sysOrg
     * @return RestResponse
     */
    @SysLog("保存机构")
    @RequestMapping("/save")
    @RequiresPermissions("sys:org:save")
    public RestResponse save(@RequestBody SysOrgEntity sysOrg) {
        SysUserEntity user = getUser();
        sysOrg.setCreateUserId(user.getUserId());
        sysOrgService.add(sysOrg);
        // 更新redis部门列表 where STATUS=1
        List orgList = sysOrgService.list(new QueryWrapper<SysOrgEntity>().eq("STATUS", 1));
        saveDictRedis(orgList);
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param sysOrg sysOrg
     * @return RestResponse
     */
    @SysLog("修改机构")
    @RequestMapping("/update")
    @RequiresPermissions("sys:org:update")
    public RestResponse update(@RequestBody SysOrgEntity sysOrg) {
        sysOrgService.update(sysOrg);
        // 更新redis部门列表 where STATUS=1
        List orgList = sysOrgService.list(new QueryWrapper<SysOrgEntity>().eq("STATUS", 1));
        saveDictRedis(orgList);
        return RestResponse.success();
    }

    /**
     * 删除
     *
     * @param orgNo 机构编码
     * @return RestResponse
     */
    @SysLog("删除机构")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:org:delete")
    public RestResponse delete(@RequestBody String orgNo) {
        orgNo = orgNo.replaceAll("\"", "");
        List<SysOrgEntity> sysOrgEntities = sysOrgService.queryListByOrgNo(orgNo);
        if (sysOrgEntities.size() > 0) {
            return RestResponse.error("请先删除子机构");
        } else {
            sysOrgService.delete(orgNo);
        }
        // 更新redis部门列表 where STATUS=1
        List orgList = sysOrgService.list(new QueryWrapper<SysOrgEntity>().eq("STATUS", 1));
        saveDictRedis(orgList);
        return RestResponse.success();
    }

    /**
     * 组织架构定时任务
     *
     */
    @SysLog("组织架构定时任务")
    @RequestMapping("/quartzOrg")
    public void quartzOrg() {
        Map<String, Object> params=new HashMap<String, Object>();
        List<SysOrgEntity> depts = sysOrgService.queryAll(params);  //表中寄存的部门
        HrmService service = new HrmService();
        List<SysOrgEntity> fathermdydepts = new ArrayList<>();  //父部门修改列表
        try {
            HrmServicePortType clien = service.getHrmServiceHttpPort();
            ArrayOfDepartmentBean arrayDept = clien.getHrmDepartmentInfo("221.207.54.67", "");
            List<DepartmentBean> oaDepts = arrayDept.getDepartmentBean();  //OA的组织架构
            for (DepartmentBean oadept:oaDepts) {
                Boolean flag = true; //部门是否在crm存在标识
                for (SysOrgEntity dept:depts) {
                    if (oadept.getCanceled().getValue() != null && oadept.getCanceled().getValue().equals("1")) {  //OA中废弃的部门
                        if (dept.getOrgNo().equals(oadept.getDepartmentid().getValue()) && dept.getStatus() == 1) {
                            QkjvipMemberDatadepEntity deptEntity = qkjvipMemberDatadepService.queryByOrgNo(dept.getOrgNo());
                            if (deptEntity == null) {  // 表示此部门下没有会员
                                dept.setStatus(0);
                                sysOrgService.update(dept);   //OA中废弃的部门在crm系统改为无效
                                flag = false;
                                System.out.println("部门：" + oadept.getFullname().getValue() + "已修改为无效");
                            } else {
                                dept.setOrgName(dept.getOrgName() + "(OA已删除)");
                                sysOrgService.update(dept);
                            }
                            break;
                        }
                    } else {
                        if (dept.getOrgNo().equals(oadept.getDepartmentid().getValue())) {
                            if (!dept.getOrgName().equals(oadept.getFullname().getValue()) || !dept.getParentNo().equals(oadept.getSupdepartmentid().getValue())) {
                                //父部门修改列表
                                if (!dept.getParentNo().equals(oadept.getSupdepartmentid().getValue())) {
                                    fathermdydepts.add(dept);
                                }
                                SysOrgEntity sysOrg = new SysOrgEntity();
//                                SysUserEntity user = getUser();
                                sysOrg.setOrgNo(oadept.getDepartmentid().getValue());
                                sysOrg.setOrgName(oadept.getFullname().getValue());
                                sysOrg.setParentNo(oadept.getSupdepartmentid().getValue());
                                sysOrg.setStatus(1);
                                sysOrg.setSort((oadept.getShoworder().getValue()==null || "".equals(oadept.getShoworder().getValue())) ? 0 : Integer.parseInt(oadept.getShoworder().getValue()));
//                                sysOrg.setCreateUserId(user.getUserId());
                                sysOrg.setCreateTime(new Date());
                                sysOrgService.update(sysOrg);
                                System.out.println("部门：" + oadept.getFullname().getValue() + "已修改");
                            }
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag == true && !(oadept.getCanceled().getValue() != null && oadept.getCanceled().getValue().equals("1"))) {
                    SysOrgEntity sysOrg = new SysOrgEntity();
//                    SysUserEntity user = getUser();
                    sysOrg.setOrgNo(oadept.getDepartmentid().getValue());
                    sysOrg.setOrgName(oadept.getFullname().getValue());
                    sysOrg.setParentNo(oadept.getSupdepartmentid().getValue());
                    sysOrg.setStatus(1);
                    sysOrg.setSort((oadept.getShoworder().getValue()==null || "".equals(oadept.getShoworder().getValue())) ? 0 : Integer.parseInt(oadept.getShoworder().getValue()));
//                    sysOrg.setCreateUserId(user.getUserId());
                    sysOrg.setCreateTime(new Date());
                    sysOrgService.quartzAdd(sysOrg);
                    System.out.println("部门：" + oadept.getFullname().getValue() + "已添加");
                }
            }

            // 更新部门缓存 孙珊珊
            CacheFactory.CacheFlow("dept");
            //更新部门的父部门 孙珊珊
            updateFatherDepts(depts,fathermdydepts);

            // 更新redis部门列表 where STATUS=1
            List orgList = sysOrgService.list(new QueryWrapper<SysOrgEntity>().eq("STATUS", 1));
            saveDictRedis(orgList);
            depts = null;// 置空方便垃圾回收处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新redis
     */
    @Async
    public void saveDictRedis (List list){
        sysCacheService.saveDictRedis(list,"orgList","MTM_CACHE:ORGLISTALL:ORGLIST");
    }

    public void updateFatherDepts(List<SysOrgEntity> depts,List<SysOrgEntity> fathermdydepts){
        List<SysOrgUpdatelogEntity> logs = new ArrayList<>();
        for (SysOrgEntity org:depts) {
            String str = (String) CacheFactory.getCacheInstance().get(SysDBCacheLogic.CACHE_DEPT_PREFIX_PARENT + org.getOrgNo());//
            String[] s = (String[]) JSONUtil.toObject(str, String[].class);// 转换成数组
            StringBuilder sb = new StringBuilder();
            if (s!=null) {
                for(int j=0;j<s.length;j++){
                    sb.append(s[j]+",");
                }
            }
            sb.append(org.getOrgNo());
            org.setFatherDeptList(sb.toString());
            if(fathermdydepts!=null&&fathermdydepts.size()>0){
                for (SysOrgEntity ore:fathermdydepts) {
                    if(org.getOrgNo().equals(ore.getOrgNo())){//同一部门
                        SysOrgUpdatelogEntity sule = new SysOrgUpdatelogEntity();
                        sule.setDeptcode(org.getOrgNo());
                        sule.setOldfatherlist(ore.getFatherDeptList());
                        sule.setNewfatherlist(sb.toString());
                        sule.setAddTime(new Date());
                        logs.add(sule);
                    }
                }
            }
        }
        sysOrgService.quartzBatchUpdate(depts);
        sysOrgUpdatelogService.batchAdd(logs);
    }
}
