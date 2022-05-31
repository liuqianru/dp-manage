/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-30 09:20:27        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.QkjvipMemberActivityService;
import com.platform.modules.qkjvip.service.QkjvipMemberActivitymbsService;
import com.platform.modules.qkjvip.service.QkjvipMemberImportService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.service.QkjvipMemberSignupService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-09-30 09:20:27
 */
@RestController
@RequestMapping("qkjvip/membersignup")
public class QkjvipMemberSignupController extends AbstractController {
    @Autowired
    private QkjvipMemberSignupService qkjvipMemberSignupService;
    @Autowired
    private QkjvipMemberActivityService qkjvipMemberActivityService;
    @Autowired
    private QkjvipMemberImportService qkjvipMemberImportService;
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberSignupEntity> list = qkjvipMemberSignupService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membersignup:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberSignupService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membersignup:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberSignupEntity qkjvipMemberSignup = qkjvipMemberSignupService.getById(id);

        return RestResponse.success().put("membersignup", qkjvipMemberSignup);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberSignup qkjvipMemberSignup
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipMemberSignupEntity qkjvipMemberSignup) {
        Map<String, Object> params=new HashMap<>();
        //params.put("phone",qkjvipMemberSignup.getPhone());
        params.put("memberid",qkjvipMemberSignup.getMemberid());
        params.put("acitvityId",qkjvipMemberSignup.getAcitvityId());
        List<QkjvipMemberSignupEntity> list = qkjvipMemberSignupService.queryTopOne(params);
        if(qkjvipMemberSignup!=null&&qkjvipMemberSignup.getOpenid()!=null){
            logger.info("报名信息："+"memberid:"+qkjvipMemberSignup.getMemberid()+";openid:" + qkjvipMemberSignup.getOpenid());
        }
        if(list.size()>0){
            //return RestResponse.error("已报名成功，谢谢");
            logger.info("已报名成功，谢谢"+ qkjvipMemberSignup.getAcitvityId());
            qkjvipMemberSignup = list.get(0);
        }else{
            MemberEntity member = new MemberEntity();
            //member.setMobile(qkjvipMemberSignup.getPhone());
            member.setScanphoneno(qkjvipMemberSignup.getPhone());
            member.setMemberId(qkjvipMemberSignup.getMemberid());
            member.setSex(qkjvipMemberSignup.getSex());
            member.setMemberName(qkjvipMemberSignup.getUserName());
            member.setIsclient(true);
            try {
                if(qkjvipMemberSignup!=null){
                    Boolean isqxflag = false;//是否清洗
                    if(isnullflag(qkjvipMemberSignup.getOldphone(),qkjvipMemberSignup.getPhone())==true){
                        isqxflag = true;
                    } else if(isnullflag(qkjvipMemberSignup.getOldsex()+"",qkjvipMemberSignup.getSex()+"")==true){
                        isqxflag = true;
                    } else if (isnullflag(qkjvipMemberSignup.getOldname(),qkjvipMemberSignup.getUserName())==true){
                        isqxflag = true;
                    }
                    //清洗
                    if(isqxflag == true){
                        Object obj = JSONArray.toJSON(member);
                        String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                        logger.info(memberJsonStr);
                        String resultPost = HttpClient.sendPost(Vars.MEMBER_UPDATE_URL, memberJsonStr);
                        JSONObject resultObject = JSON.parseObject(resultPost);
                        if (!"200".equals(resultObject.get("resultcode").toString())) {  //修改手机号成功
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //如果没有邀约记录则补充
            qkjvipMemberActivitymbsService.supadd(qkjvipMemberSignup.getAcitvityId(),member.getMemberId());
            //添加报名记录
            qkjvipMemberSignup.setMemberid(member.getMemberId());
            qkjvipMemberSignup.setAddtime(new Date());
            qkjvipMemberSignupService.add(qkjvipMemberSignup);

            try {
                if(qkjvipMemberSignup.getIntegral()!=null&&qkjvipMemberSignup.getIntegral()>0) {
                    Map map = new HashMap();
                    map.put("remark", "活动报名得积分");
                    map.put("crmmemberid", member.getMemberId());
                    map.put("actiontype", 16);
                    map.put("integral", qkjvipMemberSignup.getIntegral());

                    String queryJsonStr = JsonHelper.toJsonString(map);
                    String resultPost = HttpClient.sendPost(Vars.CONTENT_SHARE_URL, queryJsonStr);
                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                        System.out.println("活动报名得积分获得成功！");
                    } else {
                        System.out.println("活动报名得积分获得失败！");
                    }
                }
            } catch (IOException e) {
                System.out.println("scrm活动报名得积分获得失败！");
            }



        }

        //查询活动情况
        //QkjvipMemberActivityEntity qkjvipMemberActivity = qkjvipMemberActivityService.getById(qkjvipMemberSignup.getAcitvityId());
        return RestResponse.success().put("membersignup",qkjvipMemberSignup);
    }

    public Boolean isnullflag(String oldstr,String newstr){
        Boolean isnullflag = false;
        if(newstr!=null&&!newstr.equals("")){
            if(oldstr==null||oldstr.equals("")||oldstr.equals(newstr)){
                isnullflag = true;
            }
            if(oldstr==null)oldstr="";
            logger.info("判断是否清洗："+"旧："+oldstr+"新："+newstr);
        }
        return isnullflag;
    }
    /**
     * 修改
     *
     * @param qkjvipMemberSignup qkjvipMemberSignup
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membersignup:update")
    public RestResponse update(@RequestBody QkjvipMemberSignupEntity qkjvipMemberSignup) {

        qkjvipMemberSignupService.update(qkjvipMemberSignup);

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
    @RequiresPermissions("qkjvip:membersignup:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberSignupService.deleteBatch(ids);

        return RestResponse.success();
    }
}
