/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-03 09:49:29        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjInterface.controller;


import com.platform.common.utils.RestResponse;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import com.platform.modules.qkjInterface.entity.UserMsgEntity;
import com.platform.modules.qkjluck.entity.QkjluckDrawResultEntity;
import com.platform.modules.qkjluck.service.QkjluckDrawResultService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.sys.service.SysUserService;
import com.platform.modules.util.DingMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@RestController
@RequestMapping("qkjvip/sendmsgbatch")
public class QkjvipSendController extends AbstractController {
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AccesstokenService accesstokenService;
    @Autowired
    private QkjluckDrawResultService qkjluckDrawResultService;

    /**
     * 发短信及钉钉消息
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/sendmsg")
    @ResponseBody
    public RestResponse sendmsg(@RequestBody UserMsgEntity userMsgEntity) {
        if (userMsgEntity!=null && userMsgEntity.getMobilelist() != null && !userMsgEntity.getMobilelist().equals("")) {
            // 发短信
            SysSmsLogEntity smsLog = new SysSmsLogEntity();
            smsLog.setContent(userMsgEntity.getMsg() + userMsgEntity.getUrl());
            smsLog.setMobile(userMsgEntity.getMobilelist());
            SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
        }

        if (userMsgEntity!=null && userMsgEntity.getDinglist()!= null && !userMsgEntity.getDinglist().equals("")) {
            //发钉钉消息
            AccesstokenEntity ak=accesstokenService.queryAll(null).get(0);
            DingMsg demo=new DingMsg();
            String result = demo.sendLinkMessageResult(userMsgEntity.getTitle(),userMsgEntity.getMsg(), userMsgEntity.getDinglist(),"",ak.getAccessToken(),userMsgEntity.getUrl());
            if (!result.equals("0")) { // 失败
                return RestResponse.error(result);
            }
        }

        return RestResponse.success();
    }


    /**
     * 验证是否中奖
     *
     * @param
     * @return RestResponse
     */
    @RequestMapping("/luckCheck")
    public RestResponse luckCheck(@RequestParam Map<String, Object> params) {
        Boolean flag = false;
        String msg ="";
        if (params.get("unionid")!=null && params.get("productid")!=null && params.get("itemid")!=null&&params.get("pronum")!=null) {
            List<QkjluckDrawResultEntity> list = qkjluckDrawResultService.queryAll(params);
            if (list!=null && list.size()>0) {
                QkjluckDrawResultEntity debean = list.get(0);
                //判断当日期是否大于最后领奖日期
                String checkdate= debean.getTakedate();
                if (checkdate!=null&&!checkdate.equals("")) {
                    Date date=new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date date2= null;
                    try {
                        date2 = sdf.parse(checkdate);
                    } catch (ParseException e) {
                        //e.printStackTrace();
                    }
                    if(date.getTime()>date2.getTime()){
                        msg="领奖时间已过期。";
                    }else{
                        flag = true;
                    }
                }else{
                    flag = true;
                }
            }
        }
        return RestResponse.success().put("luckresult",flag).put("msg",msg);
    }

    public static void main(String[] args) {

    }


}
