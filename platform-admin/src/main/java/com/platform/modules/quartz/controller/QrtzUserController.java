/*
 * 项目名称:platform-plus
 * 类名称:OrgUserController.java
 * 包名称:com.platform.modules.quartz.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/25 14:06            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.controller;

import com.platform.common.annotation.SysLog;
import com.platform.modules.cache.CacheFactory;
import com.platform.modules.cache.SysDBCacheLogic;
import com.platform.modules.quartz.entity.QrtzUserEntity;
import com.platform.modules.quartz.service.QrtzUserService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.entity.SysUserRoleEntity;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.sys.service.SysUserRoleService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * OrgUserController
 *
 * @author liuqianru
 * @date 2020/3/25 14:06
 */
@RestController
@Component("qrtzuser")
@Slf4j
public class QrtzUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private QrtzUserService qrtzUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserChannelService sysUserChannelService;

    /**
     * 人员定时任务
     *
     */
    @SysLog("人员定时任务")
    @RequestMapping("/quartzUser")
    public void quartzUser() {
        Map<String, Object> params=new HashMap<String, Object>();
//        params.put("status", 1);
        List<SysUserEntity> users = sysUserService.queryAll(params);  //表中寄存的用户
        try {
            List<QrtzUserEntity> oaUsers = qrtzUserService.queryAll(null);  //OA的人员
            List<SysUserEntity> sysUsers = new ArrayList<>();
            // 将crm系统存在但oa不存在的人员冻结
            this.freezeUser(users, oaUsers);
            for (QrtzUserEntity oauser:oaUsers) {
                Boolean flag = true; //用户是否在crm存在标识
                for (SysUserEntity user:users) {
                    if(oauser.getAccounttype() == 0) {
                        if (user.getOaId() != null && user.getOaId().equals(oauser.getId().toString())) {
                            if (oauser.getStatus() == null || "".equals(oauser.getStatus()) || oauser.getStatus() < 4) {  //正常在职员工
                                //修改人员信息
                                user.setSex((oauser.getSex() == null || "".equals(oauser.getSex().trim())) ? 3 : Integer.parseInt(oauser.getSex().trim()));
                                user.setRealName(oauser.getLastname());
                                user.setUserName(oauser.getLoginid());
                                user.setOrgNo(oauser.getDepartmentid().toString());
                                user.setEmail(oauser.getEmail());
                                user.setMobile(oauser.getMobile());
                                user.setStatus(1);
//                                user.setCreateTime(new Date());
                                user.setDingId(oauser.getHomeaddress());
                                sysUsers.add(user);
                                System.out.println("人员：" + oauser.getLastname() + "已修改");
                            } else {  //离职、退休、无效的员工
                                //修改状态为禁用
                                user.setStatus(0);
                                sysUserService.quartzUpdate(user);
                                System.out.println("人员：" + oauser.getLastname() + "已修改为禁用");
                            }
                            flag = false;
                            break;
                        }
                    }
                }

                if(flag == true && oauser.getAccounttype() == 0 && (oauser.getStatus() == null || "".equals(oauser.getStatus()) || oauser.getStatus() < 4)) {//添加
                    SysUserEntity sysUser = new SysUserEntity();
                    sysUser.setSex((oauser.getSex() == null || "".equals(oauser.getSex().trim())) ? 3 : Integer.parseInt(oauser.getSex().trim()));
                    sysUser.setRealName(oauser.getLastname());
                    sysUser.setUserName(oauser.getLoginid());
                    sysUser.setOrgNo(oauser.getDepartmentid().toString());
                    sysUser.setEmail(oauser.getEmail());
                    sysUser.setMobile(oauser.getMobile());
                    sysUser.setStatus(1);
                    sysUser.setCreateTime(new Date());
                    sysUser.setDingId(oauser.getHomeaddress());
                    sysUser.setOaId(oauser.getId().toString());
                    sysUserService.add(sysUser);
                    // 保存默认角色
                    SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
                    sysUserRoleEntity.setUserId(sysUser.getUserId());
                    sysUserRoleEntity.setRoleId("397076822ac95125c279c18875f8b81c");
                    sysUserRoleService.add(sysUserRoleEntity);
                    // 保存用户默认渠道
                    SysUserChannelEntity userChannelEntity = new SysUserChannelEntity();
                    userChannelEntity.setUserId(sysUser.getUserId());
                    userChannelEntity.setChannelId(313);
                    sysUserChannelService.add(userChannelEntity);
                    System.out.println("人员：" + oauser.getLastname() + "已添加");
                }
            }
            if (sysUsers.size() > 0) {
                sysUserService.quartzBatchUpdate(sysUsers);
            }
            //修改用户父部门列表
            updateFatherDepts(users);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateFatherDepts(List<SysUserEntity> users){
        for (SysUserEntity org:users) {
            String str = (String) CacheFactory.getCacheInstance().get(SysDBCacheLogic.CACHE_DEPT_PREFIX_PARENT + org.getOrgNo());//
            String[] s = (String[]) JSONUtil.toObject(str, String[].class);// 转换成数组
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<s.length;j++){
                sb.append(s[j]+",");
            }
            sb.append(org.getOrgNo());
            org.setFatherDeptList(sb.toString());
        }
        sysUserService.quartzBatchUpdate(users);
    }
    public void freezeUser (List<SysUserEntity> users, List<QrtzUserEntity> oaUsers) {
        for(SysUserEntity user:users){
            Boolean flag = true;  //用户是否在oa存在标识true不存在
            for (QrtzUserEntity oauser:oaUsers) {
                if((user.getOaId() != null && user.getOaId().equals(oauser.getId().toString())) || user.getUserName().contains("admin")) {//存在
                    flag=false;
                    break;
                }
            }
            if(flag == true){
                user.setStatus(0);
                sysUserService.quartzUpdate(user);
                System.out.println("人员：" + user.getRealName() + "已修改为禁用");
            }
        }
    }
}
