/*
 * 项目名称:platform-plus
 * 类名称:QrtzWelfareBirthdayController.java
 * 包名称:com.platform.modules.quartz.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022/4/13 17:12            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareBirthdayEntity;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import com.platform.modules.qkjrts.service.QkjrtsWelfareBirthdayService;
import com.platform.modules.qkjrts.service.QkjrtsWelfareSendrecordService;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberQueryEntity;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

/**
 * QrtzWelfareBirthdayController
 *
 * @author liuqianru
 * @date 2022/4/13 17:12
 */

@RestController
@Component("qrtzWelfare")
@RequestMapping("qrtzWelfare")
@Slf4j
public class QrtzWelfareBirthdayController {
    @Autowired
    private QkjrtsWelfareBirthdayService qkjrtsWelfareBirthdayService;
    @Autowired
    private QkjrtsWelfareSendrecordService qkjrtsWelfareSendrecordService;
    @Autowired
    private SysSmsLogService sysSmsLogService;

    /**
     * 定时发放生日礼包
     *
     */
    @SysLog("定时发送生日福利")
    @RequestMapping("/sendBirthdayWelfare")
    public void sendBirthdayWelfare() throws IOException {
        long start, end;
        start = System.currentTimeMillis();
        Map params = new HashMap<>();
        params.put("status", 0);
        List<QkjrtsWelfareBirthdayEntity> welfareBirthdayList = qkjrtsWelfareBirthdayService.queryAll(params);
        if (welfareBirthdayList.size() > 0) {
            List<QkjrtsWelfareSendrecordEntity> sendrecordList = new ArrayList<>();
            MemberQueryEntity memberQueryEntity = new MemberQueryEntity();
            StringBuffer mobiles = new StringBuffer();
            Date nowDate = new Date();
            for (QkjrtsWelfareBirthdayEntity entity : welfareBirthdayList) {
                memberQueryEntity.setListmemberlevel(entity.getMemberlevel());
//                memberQueryEntity.setMobile("13641005224");  // 测试用
                memberQueryEntity.setQueryall(1);
                memberQueryEntity.setListorgno("-1");
                memberQueryEntity.setListmemberchannel("-1");
                memberQueryEntity.setSpecialBirthday(DateUtils.format(DateUtils.addDateDays(nowDate, entity.getAdvancedays())).substring(5));
                String queryJsonStr = JsonHelper.toJsonString(memberQueryEntity, "yyyy-MM-dd HH:mm:ss");
                String resultPost = HttpClient.sendPost(Vars.MEMBER_GETLIST_URL, queryJsonStr);
                System.out.println("会员检索条件：" + queryJsonStr);
                JSONObject resultObject = JSON.parseObject(resultPost);
                if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
                    List<MemberEntity> memberList = new ArrayList<>();
                    memberList = JSON.parseArray(resultObject.getString("listmember"),MemberEntity.class);
                    if (memberList.size() > 0) {
                        for (MemberEntity memberEntity : memberList) {
                            QkjrtsWelfareSendrecordEntity sendrecord = new QkjrtsWelfareSendrecordEntity();
                            List<QkjrtsWelfareSendrecordEntity> list = new ArrayList<>();
                            params.clear();
                            params.put("memberid", memberEntity.getMemberId());
                            params.put("scenetype", 1); // 场景为生日的
                            params.put("fromqrtz", 1);  // 当年内有发放记录的
                            list = qkjrtsWelfareSendrecordService.queryAll(params);
                            if (list.size() == 0) {  // 表示当年内还未发放过
                                sendrecord.setMainid(entity.getId());
                                sendrecord.setWelfareid(entity.getWelfareid());
                                sendrecord.setWelfarename(entity.getWelfarename());
                                sendrecord.setWelfaretype(entity.getWelfaretype());
                                if (entity.getPeriodtype() != null && entity.getPeriodtype() == 1) { // 相对日期
                                    Date startDate = DateUtils.setDate(nowDate);
                                    sendrecord.setStartvaliddate(startDate);
                                    sendrecord.setEndvaliddate(DateUtils.addDateDays(startDate, entity.getPerioddays()));
                                } else if (entity.getPeriodtype() != null && entity.getPeriodtype() == 0) {  // 绝对日期
                                    sendrecord.setStartvaliddate(entity.getStartvaliddate());
                                    sendrecord.setEndvaliddate(entity.getEndvaliddate());
                                }
                                sendrecord.setMemberid(memberEntity.getMemberId());
                                sendrecord.setScenetype(1);
                                sendrecord.setSendtime(new Date());
                                sendrecordList.add(sendrecord);
                                if (!StringUtils.isEmpty(memberEntity.getMobile())) {
                                    mobiles.append(memberEntity.getMobile() + ",");
                                }
                            }
                        }
                    }
                }
            }
            if (sendrecordList.size() > 0) {
                qkjrtsWelfareSendrecordService.addBatch(sendrecordList);
            }
//            if (!"".equals(mobiles.toString())) {
//                SysSmsLogEntity smsLog = new SysSmsLogEntity();
//                smsLog.setContent("尊敬的天佑德会员，您的生日礼包已发放，请点击链接登录青稞荟小程序查看，查看链接；https://wxaurl.cn/K0vrkhelCbg");
//                smsLog.setMobile(mobiles.toString().substring(0, mobiles.toString().length() - 1));
//                SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
//            }
            end = System.currentTimeMillis();
            System.out.println("生日批量发放礼包定时任务执行完毕，用时：" + (end - start) + "ms");
        }
    }
}
