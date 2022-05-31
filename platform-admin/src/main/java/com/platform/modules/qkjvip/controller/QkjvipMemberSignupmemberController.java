/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberSignupmemberController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-10-26 13:18:34        孙珊珊     初版做成
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
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-10-26 13:18:34
 */
@RestController
@RequestMapping("qkjvip/membersignupmember")
public class QkjvipMemberSignupmemberController extends AbstractController {
    @Autowired
    private QkjvipMemberSignupmemberService qkjvipMemberSignupmemberService;
    @Autowired
    private QkjvipMemberActivitymbsService qkjvipMemberActivitymbsService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private QkjvipMemberSignupService qkjvipMemberSignupService;
    @Autowired
    private QkjvipMemberImportService qkjvipMemberImportService;
    @Autowired
    private QkjvipMemberActivityService qkjvipMemberActivityService;
    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    @RequiresPermissions("qkjvip:membersignupmember:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipMemberSignupmemberEntity> list = qkjvipMemberSignupmemberService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjvip:membersignupmember:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjvipMemberSignupmemberService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:membersignupmember:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember = qkjvipMemberSignupmemberService.getById(id);

        return RestResponse.success().put("membersignupmember", qkjvipMemberSignupmember);
    }

    /**
     * 新增
     *
     * @param qkjvipMemberSignupmember qkjvipMemberSignupmember
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @RequiresPermissions("qkjvip:membersignupmember:save")
    public RestResponse save(@RequestBody QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) {

        qkjvipMemberSignupmemberService.add(qkjvipMemberSignupmember);

        return RestResponse.success();
    }

    /**
     * 批量签到
     *
     * @param qkjvipMemberSignupmember qkjvipMemberSignupmember
     * @return RestResponse
     */
    @SysLog("批量签到")
    @RequestMapping("/saveMems")
    public RestResponse saveMems(@RequestBody QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) {
        if(qkjvipMemberSignupmember.getMemlist().size()>0){
            int tonum=0;
            for(QkjvipMemberActivitymbsEntity mems:qkjvipMemberSignupmember.getMemlist()){
                if(mems!=null){
                    String bmid="";
                    if(mems.getQdstatus()!=null&&mems.getQdstatus().equals(1)){//已签到

                    }else{//未签到
                        if(mems.getBmstatus()!=null&&mems.getBmstatus().equals(1)){//已报名
                            bmid=mems.getBmid();
                        }else{//补充报名
                            bmid=qkjvipMemberSignupService.supadd(qkjvipMemberSignupmember.getActivityId(),mems.getMemberId(),"");
                        }
                        //添加签到
                        if(bmid!=""){
                            Date date=new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date2=sdf.format(date);
                            QkjvipMemberSignupmemberEntity newsingup =new QkjvipMemberSignupmemberEntity();
                            newsingup.setActivityId(qkjvipMemberSignupmember.getActivityId());
                            newsingup.setMemberId(mems.getMemberId());
                            newsingup.setTime(date2);
                            newsingup.setSignupId(bmid);
                            qkjvipMemberSignupmemberService.add(newsingup);
                        }

                    }
                }else {
                    tonum+=1;
                }

            }
        }
        //qkjvipMemberSignupmemberService.add(qkjvipMemberSignupmember);

        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipMemberSignupmember qkjvipMemberSignupmember
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:membersignupmember:update")
    public RestResponse update(@RequestBody QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) {

        qkjvipMemberSignupmemberService.update(qkjvipMemberSignupmember);

        return RestResponse.success();
    }

    /**
     * 签到
     *
     * @param qkjvipMemberSignupmember qkjvipMemberSignupmember
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/savesign")
    public RestResponse savesign(@RequestBody QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember) throws IOException {
        // 清洗会员(微信id有则不清洗,无清洗)
        Map<String, Object> params=new HashMap<>();
        String openid=qkjvipMemberSignupmember.getOpenId();//模拟的微信id
        String mobile=qkjvipMemberSignupmember.getMobile();//模拟的手机号

        MemberEntity member = new MemberEntity();
        //member.setMobile(mobile);
        member.setScanphoneno(mobile);
        member.setMemberId(qkjvipMemberSignupmember.getMemberId());
        member.setMemberName(qkjvipMemberSignupmember.getUserName());
        member.setSex(qkjvipMemberSignupmember.getSex());
        member.setIsclient(true);
        //是否已签到
        params.clear();
        params.put("memberId",member.getMemberId());
        params.put("activityId",qkjvipMemberSignupmember.getActivityId());
        List<QkjvipMemberSignupmemberEntity> list = qkjvipMemberSignupmemberService.queryTopOne(params);
        if(list.size()>0){//已签到
            //return RestResponse.error("已签到成功，谢谢");
            //已签到显示行程安排、参加的活动记录、用户的积分、积分商城
            if(qkjvipMemberSignupmember!=null&&qkjvipMemberSignupmember.getOpenId()!=null){
                logger.info("已签到信息："+"memberid:"+qkjvipMemberSignupmember.getMemberId()+";openid:" + qkjvipMemberSignupmember.getOpenId());
            }
        } else {
            long start, end2;
            start = System.currentTimeMillis();
            //修改此会员手机号
            updateMember(qkjvipMemberSignupmember,member);
            //邀请补充
            qkjvipMemberActivitymbsService.supadd(qkjvipMemberSignupmember.getActivityId(),member.getMemberId());
            //报名补充
            String bmid=qkjvipMemberSignupService.supadd(qkjvipMemberSignupmember.getActivityId(),member.getMemberId(),mobile);
            //添加签到
            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date2=sdf.format(date);
            qkjvipMemberSignupmember.setMemberId(member.getMemberId());
            qkjvipMemberSignupmember.setTime(date2);
            qkjvipMemberSignupmember.setSignupId(bmid);
            qkjvipMemberSignupmemberService.add(qkjvipMemberSignupmember);
            System.out.println("签到成功活动id："+qkjvipMemberSignupmember.getActivityId());
            if(qkjvipMemberSignupmember!=null&&qkjvipMemberSignupmember.getOpenId()!=null){
                logger.info("正常签到信息："+"memberid:"+qkjvipMemberSignupmember.getMemberId()+";openid:" + qkjvipMemberSignupmember.getOpenId());
            }
            // 送积分
            updateMemberIntegral(qkjvipMemberSignupmember,member);
            end2 = System.currentTimeMillis();
            System.out.println("签到成功，耗费了" + (end2 - start) + "ms");
        }

        return RestResponse.success();
    }

    @Async
    public  void updateMemberIntegral (QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember,MemberEntity member) {
        if(qkjvipMemberSignupmember.getIntegral()!=null&&qkjvipMemberSignupmember.getIntegral()>0){
            Map map = new HashMap();
            map.put("remark", "活动签到得积分");
            map.put("crmmemberid", member.getMemberId());
            map.put("actiontype", 17);
            map.put("integral", qkjvipMemberSignupmember.getIntegral());

            String queryJsonStr = JsonHelper.toJsonString(map);
            String resultPost = null;
            try {
                resultPost = HttpClient.sendPost(Vars.CONTENT_SHARE_URL, queryJsonStr);
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("签到送积分接口调用失败");
            }
            JSONObject resultObject = JSON.parseObject(resultPost);
            if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                System.out.println("活动签到得积分获得成功！");
            } else {
                System.out.println("活动签到得积分获得失败！");
            }
        }
    }

    @Async
    public  void updateMember (QkjvipMemberSignupmemberEntity qkjvipMemberSignupmember,MemberEntity member) {
        if(qkjvipMemberSignupmember!=null&&qkjvipMemberSignupmember.getIsphone()!=null&&qkjvipMemberSignupmember.getIsphone()==1){

            Object obj = JSONArray.toJSON(member);
            String memberJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
            String resultPost = null;
            try {
                resultPost = HttpClient.sendPost(Vars.MEMBER_UPDATE_URL, memberJsonStr);
            } catch (IOException e) {
                //e.printStackTrace();
                logger.error("签到接口调用失败");
            }
            JSONObject resultObject = JSON.parseObject(resultPost);
            if (!"200".equals(resultObject.get("resultcode").toString())) {  //修改手机号成功
                logger.info("签到接口数据清洗失败");
            }
        }
    }

    /**
     * 根据主键删除
     *
     * @param ids ids
     * @return RestResponse
     */
    @SysLog("删除")
    @RequestMapping("/delete")
    @RequiresPermissions("qkjvip:membersignupmember:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjvipMemberSignupmemberService.deleteBatch(ids);

        return RestResponse.success();
    }
}
