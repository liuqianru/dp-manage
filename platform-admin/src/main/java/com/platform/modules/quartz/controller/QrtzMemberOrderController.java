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

import com.alibaba.fastjson.JSON;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberOrderthpEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderService;
import com.platform.modules.qkjvip.service.QkjvipMemberOrderthpService;
import com.platform.modules.quartz.entity.QrtzLastUpdateTimeEntity;
import com.platform.modules.quartz.service.QrtzLastUpdateTimeService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.MD5Utils;
import com.platform.modules.util.Vars;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * QrtzMemberController
 *
 * @author liuqianru
 * @date 2020/8/6 9:41
 */

@RestController
@Component("qrtzMemberOrder")
@Slf4j
public class QrtzMemberOrderController extends AbstractController {
    @Autowired
    private QkjvipMemberOrderService qkjvipMemberOrderService;
    @Autowired
    private QrtzLastUpdateTimeService qrtzLastUpdateTimeService;
    @Autowired
    private QkjvipMemberOrderthpService qkjvipMemberOrderthpService;

    /**
     * 会员定时任务
     *
     */
    @SysLog("会员订单读取定时任务")
    @RequestMapping("/getMembersOrder")
    public void getMembersOrder(String params) throws IOException, NoSuchAlgorithmException {
        QrtzLastUpdateTimeEntity updateTimeEntity = new QrtzLastUpdateTimeEntity();
        long start, end2;
        start = System.currentTimeMillis();
        String url = "http://open.api.zhongjiu.cn/DashboardOpenAPI/ShopOrderDataSync";
        String thurl = "http://open.api.zhongjiu.cn/DashboardOpenAPI/ShopOrderRefundSync";
        String timeStamp = DateUtils.getTimeStamp();  //时间戳
        SortedMap<String, String> map = new TreeMap<>();
        String urlParam = "";
        String sign = "";
        String resultPost = "";  //返回结果
        map.clear();
        String startime="";
        String endtime="";
        if (!StringUtils.isEmpty(params) && params.split(",").length == 2) {//同步指定时间
            String[] paramArr = new String[params.split(",").length];
            paramArr = params.split(",");
            startime=paramArr[0].trim().substring(0,10);
            endtime=paramArr[1].trim().substring(0,10);
            List<String> days = new ArrayList<String>();
            days = getDays(startime, endtime);
            for(int i=0;i<days.size();i++){
                String myd=days.get(i);
                startime = myd + " 00:00:00";
                endtime = myd +" 23:59:59";
                Integer listsize=getMemberBasicEntities(url,timeStamp,startime,endtime);//订单生成修改
                Integer listsize2=getMemberThEntities(thurl,timeStamp,startime,endtime);
            }
            end2 = System.currentTimeMillis();
            System.out.println("会员订单批量处理手动数据完成条数据耗费了" + (end2 - start) + "ms");
        }else{//半小时同步一次
            Date nowDate = new Date();
            String nowday = DateUtils.format(nowDate, "yyyy-MM-dd");  //当天日期
            endtime = nowday+" 23:59:59";  //现在时间
            startime = nowday + " 00:00:00"; //前半小时时间
//            endtime = DateUtils.format(nowDate, "yyyy-MM-dd HH:mm:ss");  //现在时间
//            startime = DateUtils.format(DateUtils.addDateMinutes(nowDate, -30), "yyyy-MM-dd HH:mm:ss"); //前半小时时间

            Map paramMap = new HashMap();
            paramMap.put("type", 2);
            List<QrtzLastUpdateTimeEntity> updateTimeList = qrtzLastUpdateTimeService.queryAll(paramMap);
            updateTimeEntity = updateTimeList.get(0);
            if (updateTimeList.get(0).getLastDatetime() != null) {
                //startime = DateUtils.format(updateTimeList.get(0).getOrderLastDatetime(), "yyyy-MM-dd HH:mm:ss");
            }
            updateTimeEntity.setLastDatetime(nowDate);
            Integer listsize=getMemberBasicEntities(url,timeStamp,startime,endtime);//订单生成修改
            Integer listsize2=getMemberThEntities(thurl,timeStamp,startime,endtime);
            end2 = System.currentTimeMillis();
            //将最后更新数据存入数据库
            if (!StringUtils.isEmpty(updateTimeEntity.getId())) {
                qrtzLastUpdateTimeService.updateLastDatetime(updateTimeEntity);
            }
            System.out.println("会员订单批量处理"+(listsize+listsize2)+"条数据，耗费了" + (end2 - start) + "ms");
        }
    }

    private Integer getMemberBasicEntities(String url, String timeStamp, String starttime, String endtime) throws IOException {
        SortedMap<String, String> map = new TreeMap<>();
        String resultPost = "";  //返回结果
        String sign;
        String urlParam;
        List<QkjvipMemberOrderEntity> addList= new ArrayList<QkjvipMemberOrderEntity>();
        List<QkjvipMemberOrderEntity> mdyList= new ArrayList<QkjvipMemberOrderEntity>();
        //数据库已存在的所有数据
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("timestamp", timeStamp);
        map.put("app_key", Vars.APP_KEY);
        for(int i=1;i<=1;i++){
            map.put("timetype", i+"");
            sign = MD5Utils.getMD5Sign(map);
            urlParam = "?starttime=" + starttime
                    + "&endtime=" + endtime
                    + "&timetype=" + i+""
                    + "&timestamp=" + timeStamp
                    + "&app_key=" + Vars.APP_KEY
                    + "&sign=" + sign;
            String myurl = url + urlParam;
            resultPost = HttpClient.doPost(myurl);
            if(i==1){
                addList = JSON.parseArray(resultPost, QkjvipMemberOrderEntity.class);
                if(addList.size()>1000){
                    int listSize=addList.size();
                    int toIndex=1000;
                    for(int j = 0;j<addList.size();j+=1000){
                        if(j+1000>listSize){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                            toIndex=listSize-j;
                        }
                        List newList = addList.subList(j,j+toIndex);
                        qkjvipMemberOrderService.deleteBatchByOrder(newList);
                    }
                }else{
                    if(addList!=null&&addList.size()>0){
                        qkjvipMemberOrderService.deleteBatchByOrder(addList);
                    }
                }

                qkjvipMemberOrderService.saveBatch(addList);
            }else {
                //mdyList = JSON.parseArray(resultPost, QkjvipMemberOrderEntity.class);
                //qkjvipMemberOrderService.updateBatch(mdyList);
            }

        }
        return addList.size()+mdyList.size();
    }

    /**
     * 获取退货
     * @param url
     * @param timeStamp
     * @param starttime
     * @param endtime
     * @return
     * @throws IOException
     */
    private Integer getMemberThEntities(String url, String timeStamp, String starttime, String endtime) throws IOException {
        SortedMap<String, String> map = new TreeMap<>();
        String resultPost = "";  //返回结果
        String sign;
        String urlParam;
        List<QkjvipMemberOrderthpEntity> addList= new ArrayList<QkjvipMemberOrderthpEntity>();
        List<QkjvipMemberOrderthpEntity> mdyList= new ArrayList<QkjvipMemberOrderthpEntity>();
        //数据库已存在的所有数据
        map.put("starttime", starttime);
        map.put("endtime", endtime);
        map.put("timestamp", timeStamp);
        map.put("app_key", Vars.APP_KEY);
        for(int i=1;i<=1;i++){
            map.put("timetype", i+"");
            sign = MD5Utils.getMD5Sign(map);
            urlParam = "?starttime=" + starttime
                    + "&endtime=" + endtime
                    + "&timetype=" + i+""
                    + "&timestamp=" + timeStamp
                    + "&app_key=" + Vars.APP_KEY
                    + "&sign=" + sign;
            resultPost = HttpClient.doPost(url + urlParam);
            if(i==1){
                addList = JSON.parseArray(resultPost, QkjvipMemberOrderthpEntity.class);
                if(addList.size()>1000){
                    int listSize=addList.size();
                    int toIndex=1000;
                    for(int j = 0;j<addList.size();j+=1000){
                        if(j+1000>listSize){        //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                            toIndex=listSize-j;
                        }
                        List newList = addList.subList(j,j+toIndex);
                        qkjvipMemberOrderthpService.deleteBatchByOrder(newList);
                    }
                }else{
                    if(addList!=null&&addList.size()>0){
                        qkjvipMemberOrderthpService.deleteBatchByOrder(addList);
                    }
                }

                qkjvipMemberOrderthpService.saveBatch(addList);
            }else {
                //mdyList = JSON.parseArray(resultPost, QkjvipMemberOrderEntity.class);
                //qkjvipMemberOrderService.updateBatch(mdyList);
            }

        }
        return addList.size()+mdyList.size();
    }

    public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        ;
        return days;
    }

}
