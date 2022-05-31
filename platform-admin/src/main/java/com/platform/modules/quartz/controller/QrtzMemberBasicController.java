/*
 * 项目名称:platform-plus
 * 类名称:QrtzMemberController.java
 * 包名称:com.platform.modules.quartz.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/6 9:41            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.quartz.entity.QrtzLastUpdateTimeEntity;
import com.platform.modules.quartz.entity.QrtzMemberBasicEntity;
import com.platform.modules.quartz.entity.TmpQkjvipMemberBasicEntity;
import com.platform.modules.quartz.service.QrtzLastUpdateTimeService;
import com.platform.modules.quartz.service.QrtzMemberBasicService;
import com.platform.modules.quartz.service.TmpQkjvipMemberBasicService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.MD5Utils;
import com.platform.modules.util.RabbitMQUtil;
import com.platform.modules.util.Vars;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * QrtzMemberController
 *
 * @author liuqianru
 * @date 2020/8/6 9:41
 */

@RestController
@Component("qrtzMember")
@Slf4j
public class QrtzMemberBasicController extends AbstractController {
    @Autowired
    private QrtzMemberBasicService memberBasicService;
    @Autowired
    private TmpQkjvipMemberBasicService tmpQkjvipMemberBasicService;
    @Autowired
    private QrtzLastUpdateTimeService qrtzLastUpdateTimeService;
    private List<QrtzMemberBasicEntity> addList;

    /**
     * 会员定时任务
     *
     */
    @SysLog("会员读取定时任务")
    @RequestMapping("/getMembers")
    public void getMembers(String params) throws IOException, NoSuchAlgorithmException {
        String url = "http://open.api.zhongjiu.cn/DashboardOpenAPI/ShopMeberDataSync";
        QrtzLastUpdateTimeEntity updateTimeEntity = new QrtzLastUpdateTimeEntity();
        String timeStamp = DateUtils.getTimeStamp();  //时间戳
        Date nowDate = new Date();
        String endtime = DateUtils.format(nowDate, "yyyy-MM-dd HH:mm:ss");  //现在时间
        String starttime = DateUtils.format(DateUtils.addDateMinutes(nowDate, -2), "yyyy-MM-dd HH:mm:ss"); //默认是2分钟前
        SortedMap<String, String> map = new TreeMap<>();
        String urlParam = "";
        String sign = "";
        String resultPost = "";  //返回结果
        if (!StringUtils.isEmpty(params) && params.split(",").length == 2) {   //当定时任务有参数时，按照参数指定日期同步数据
            String[] paramArr = new String[params.split(",").length];
            paramArr = params.split(",");
            starttime = paramArr[0].trim();
            endtime = paramArr[1].trim();
        } else {
            Map paramMap = new HashMap();
            paramMap.put("type", 1);
            List<QrtzLastUpdateTimeEntity> updateTimeList = qrtzLastUpdateTimeService.queryAll(paramMap);
            updateTimeEntity = updateTimeList.get(0);
            if (updateTimeList.get(0).getLastDatetime() != null) {
                starttime = DateUtils.format(updateTimeList.get(0).getLastDatetime(), "yyyy-MM-dd HH:mm:ss");
            }
            updateTimeEntity.setLastDatetime(nowDate);
        }
        map.clear();
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("timestamp", timeStamp);
        map.put("app_key", Vars.APP_KEY);
        for(int i = 0; i <= 1; i++) {
            map.put("timetype", i + "");
            sign = MD5Utils.getMD5Sign(map);
            urlParam = "?starttime=" + starttime
                    + "&endtime=" + endtime
                    + "&timetype=" + i+""
                    + "&timestamp=" + timeStamp
                    + "&app_key=" + Vars.APP_KEY
                    + "&sign=" + sign;
            System.out.println("key:sign" + " vlaue:" + sign);
            resultPost = HttpClient.doPost(url + urlParam);
            if (resultPost != null && !"[]".equals(resultPost)) {
                this.saveOrUpdateMember(resultPost, i + "");
            }
        }
        //将最后更新时间存入数据库
        if (!StringUtils.isEmpty(updateTimeEntity.getId())) {
            qrtzLastUpdateTimeService.updateLastDatetime(updateTimeEntity);
        }
    }

    public void saveOrUpdateMember(String resultPost, String timeType) {
        List<QrtzMemberBasicEntity> mbList = JSON.parseArray(resultPost, QrtzMemberBasicEntity.class);
        List<TmpQkjvipMemberBasicEntity> tmpList = JSON.parseArray(resultPost, TmpQkjvipMemberBasicEntity.class);
        Map map = new HashMap();
        map.put("QueueData", mbList);
        String jsonData = JsonHelper.toJsonString(map, "yyyy-MM-dd HH:mm:ss");
        List<QrtzMemberBasicEntity> fromDbList = new ArrayList<QrtzMemberBasicEntity>();
        if (tmpList.size() > 0) {
            tmpQkjvipMemberBasicService.addBatch(tmpList);  //批量插入会员临时表
            fromDbList = memberBasicService.queryList();  //取出会员表与临时表的交集(插入是为重复数据，更新时即为要更新的数据)
        }
        if ("0".equals(timeType)) {  //注册
            addList = new ArrayList<QrtzMemberBasicEntity>();
            if (mbList.size() > 0) {
                mbList.removeAll(fromDbList); //将中酒取出的数据去除表中已存在的数据
                addList = mbList;
                if (addList.size() > 0) {
                    memberBasicService.addBatch(addList);  //会员批量插入
                }
            }
        } else if ("1".equals(timeType)) {
            if (fromDbList.size() > 0) {
                fromDbList.removeAll(addList);
                if (fromDbList.size() > 0) {
                    memberBasicService.updateBatch(fromDbList);
                }
            }
        }
        //将数据存入队列
        RabbitMQUtil.getConnection("qkjvip_member_basic", jsonData);
    }

    /**
     * 会员定时任务
     *
     */
    @SysLog("会员循环读取")
    @RequestMapping("/getLoopMembers")
    public void getLoopMembers(String params) throws IOException, NoSuchAlgorithmException {
        String starttime = "";
        String endtime = "";
        if (!StringUtils.isEmpty(params)) {
            Date fromtime = DateUtils.stringToDate(params, DateUtils.DATE_TIME_PATTERN);
            Date totime = DateUtils.addDateMonths(fromtime, 1); //一个月后的日期
            Calendar cal = Calendar.getInstance();
            int yearto = cal.get(Calendar.YEAR);
            cal.setTime(fromtime);
            int yearfrom = cal.get(Calendar.YEAR);
            int num = ((yearto - yearfrom) + 1) * 12;
            for (int i = 0; i < num; i++) {
                if (i > 0) { //第一个月
                    fromtime = totime;
                    totime = DateUtils.addDateMonths(fromtime, 1); //一个月后的日期
                }
                starttime = DateUtils.format(fromtime, "yyyy-MM-dd HH:mm:ss");
                endtime = DateUtils.format(totime, "yyyy-MM-dd HH:mm:ss");
                this.getMembers(starttime + "," + endtime);
            }
        }
    }
}
