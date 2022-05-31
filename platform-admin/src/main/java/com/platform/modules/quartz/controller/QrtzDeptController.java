/*
 * 项目名称:platform-plus
 * 类名称:DeptController.java
 * 包名称:com.platform.modules.quartz.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/25 15:39            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.controller;

import com.platform.common.annotation.SysLog;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.cache.SysDBCacheLogic;
import com.platform.modules.qkjvip.entity.QkjvipMemberDatadepEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberDatadepService;
import com.platform.modules.quartz.entity.QrtzDeptEntity;
import com.platform.modules.quartz.service.QrtzDeptService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;
import com.platform.modules.sys.service.SysOrgService;
import com.platform.modules.sys.service.SysOrgUpdatelogService;
import com.platform.modules.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * DeptController
 *
 * @author liuqianru
 * @date 2020/3/25 15:39
 */
@RestController
@Component("qrtzorg")
@Slf4j
public class QrtzDeptController extends AbstractController {
    @Autowired
    private SysOrgService sysOrgService;
    @Autowired
    private QrtzDeptService qrtzDeptService;
    @Autowired
    private QkjvipMemberDatadepService qkjvipMemberDatadepService;
    @Autowired
    private SysOrgUpdatelogService sysOrgUpdatelogService;

    /**
     * 组织架构定时任务
     *
     */
    @SysLog("组织架构定时任务")
    @RequestMapping("/quartzOrg")
    public void quartzOrg() {
        Map<String, Object> params=new HashMap<String, Object>();
        params.put("status", 1);
        List<SysOrgEntity> depts = sysOrgService.queryAll(params);  //表中寄存的部门
        List<SysOrgEntity> fathermdydepts = new ArrayList<>();  //父部门修改列表
        try {
            List<QrtzDeptEntity> oaDepts = qrtzDeptService.queryAll(null);  //OA的组织架构
            for (QrtzDeptEntity oadept:oaDepts) {
                Boolean flag = true; //部门是否在crm存在标识
                for (SysOrgEntity dept:depts) {
                    if (oadept.getCanceled() != null && oadept.getCanceled().equals("1")) {  //OA中废弃的部门
                        if (dept.getOrgNo().equals(oadept.getId().toString()) && dept.getStatus() == 1) {
                            QkjvipMemberDatadepEntity deptEntity = qkjvipMemberDatadepService.queryByOrgNo(dept.getOrgNo());
                            if (deptEntity == null){
                                dept.setStatus(0);
                                sysOrgService.update(dept);   //OA中废弃的部门在crm系统改为无效
                                flag = false;
                                System.out.println("部门：" + oadept.getDepartmentname() + "已修改为无效");
                            } else {
                                dept.setOrgName(dept.getOrgName() + "(OA已删除)");
                                sysOrgService.update(dept);
                            }
                            break;
                        }
                    } else {
                        if (dept.getOrgNo().equals(oadept.getId().toString())) {
                            if (!dept.getOrgName().equals(oadept.getDepartmentname()) || !dept.getParentNo().equals(oadept.getSupdepid().toString())) {
                                //父部门修改列表
                                if (!dept.getParentNo().equals(oadept.getSupdepid().toString())) {
                                    fathermdydepts.add(dept);
                                }
                                SysOrgEntity sysOrg = new SysOrgEntity();
                                sysOrg.setOrgNo(oadept.getId().toString());
                                sysOrg.setOrgName(oadept.getDepartmentname());
                                sysOrg.setParentNo(oadept.getSupdepid().toString());
                                sysOrg.setStatus(1);
                                sysOrg.setSort((oadept.getShoworder() == null || "".equals(oadept.getShoworder().toString())) ? 0 : oadept.getShoworder());
                                sysOrg.setCreateTime(new Date());
                                sysOrgService.update(sysOrg);
                                System.out.println("部门：" + oadept.getDepartmentname() + "已修改");
                            }
                            flag = false;
                            break;
                        }
                    }
                }
                if(flag == true && !(oadept.getCanceled() != null && oadept.getCanceled().equals("1"))) {
                    SysOrgEntity sysOrg = new SysOrgEntity();
                    sysOrg.setOrgNo(oadept.getId().toString());
                    sysOrg.setOrgName(oadept.getDepartmentname());
                    sysOrg.setParentNo(oadept.getSupdepid().toString());
                    sysOrg.setStatus(1);
                    sysOrg.setSort((oadept.getShoworder() == null || "".equals(oadept.getShoworder().toString())) ? 0 : oadept.getShoworder());
                    sysOrg.setCreateTime(new Date());
                    sysOrgService.quartzAdd(sysOrg);
                    System.out.println("部门：" + oadept.getDepartmentname() + "已添加");
                }
            }
            CacheFactory.CacheFlow("dept");
            updateFatherDepts(depts,fathermdydepts);
            depts = null;// 置空方便垃圾回收处理
        } catch (Exception e) {
            e.printStackTrace();
        }
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
