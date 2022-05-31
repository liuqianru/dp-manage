/*
 * 项目名称:platform-plus
 * 类名称:QkjluckDrawAcitvityController.java
 * 包名称:com.platform.modules.qkjluck.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 17:26:24        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjluck.controller;

import cn.emay.util.DateUtil;
import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.HttpContextUtils;
import com.platform.common.utils.IpUtils;
import com.platform.common.utils.JedisUtil;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjluck.entity.*;
import com.platform.modules.qkjluck.service.QkjluckDrawAcitiityitemService;
import com.platform.modules.qkjluck.service.QkjluckDrawResultService;
import com.platform.modules.qkjluck.service.QkjluckDrawResultcachService;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjluck.service.QkjluckDrawAcitvityService;
import com.platform.modules.sys.entity.SysCacheEntity;
import com.platform.modules.sys.entity.SysLogEntity;
import com.platform.modules.sys.entity.SysUserEntity;
import com.platform.modules.sys.service.SysCacheService;
import com.platform.modules.sys.service.SysLogService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-07-05 17:26:24
 */
@RestController
@RequestMapping("qkjluck/drawacitvity")
public class QkjluckDrawAcitvityController extends AbstractController {
    @Autowired
    private QkjluckDrawAcitvityService qkjluckDrawAcitvityService;
    @Autowired
    private QkjluckDrawAcitiityitemService qkjluckDrawAcitiityitemService;
    @Autowired
    private QkjluckDrawResultService qkjluckDrawResultService;
    @Autowired
    JedisUtil jedisUtil;
    @Autowired
    SysCacheService sysCacheService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysLogService sysLogService;
    @Autowired
    private QkjluckDrawResultcachService qkjluckDrawResultcachService;


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjluckDrawAcitvityEntity> list = qkjluckDrawAcitvityService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = qkjluckDrawAcitvityService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        QkjluckDrawAcitvityEntity qkjluckDrawAcitvity = qkjluckDrawAcitvityService.getById(id);
        // 查询奖品
        Map<String, Object> params = new HashMap<>();
        params.put("activityId",id);
        Page page = qkjluckDrawAcitiityitemService.queryPage(params);
        List<QkjluckDrawAcitiityitemEntity> items = page.getRecords();
        qkjluckDrawAcitvity.setItemlist(items);
        return RestResponse.success().put("drawacitvity", qkjluckDrawAcitvity);
    }

    /**
     * 根据主键查询详情抽奖
     *
     * @param paramsa 主键
     * @return RestResponse
     */
    @GetMapping("/luckinfo")
    public RestResponse luckinfo(@RequestParam Map<String, Object> paramsa) {
        String yltype = paramsa.get("yltype")+"";//是否是预览
        String openid = paramsa.get("openid")+"";//参与人
        String unionid = paramsa.get("unionid")+"";//参与人
        String id=paramsa.get("id")+"";//活动id
        // 查询会员信息
       QkjluckMallInteEntity memberinfo =new QkjluckMallInteEntity();
        Map map = new HashMap();
        map.put("unionid", unionid);
        String queryJsonStr = JsonHelper.toJsonString(map);
        try {
            String  resultPost = HttpClient.sendPost(Vars.MALL_INTEGRAL_SELECTBYUNIONID, queryJsonStr);
            JSONObject resultObject = JSON.parseObject(resultPost);
            if ("0".equals(resultObject.get("code").toString())) {  //调用成功
                memberinfo = JSON.parseObject(resultPost, QkjluckMallInteEntity.class);
            } else {
                memberinfo.setIntegral(0);
            }
        } catch (IOException e) {
            memberinfo.setIntegral(0);
        }

        QkjluckDrawAcitvityEntity qkjluckDrawAcitvity = qdklucbBean(id,paramsa);//查询活动
        List itemlist = new ArrayList<>();
        itemlist=getacitemlist(id);
        qkjluckDrawAcitvity.setItemlist(itemlist);//查询奖项

        //是否在抽奖时间段内
        Date nowDate = new Date();
        Date star_date= DateUtil.parseDate(qkjluckDrawAcitvity.getStrDate(),"yyyy-MM-dd");
        Date end_date=DateUtil.parseDate(qkjluckDrawAcitvity.getEndDate(),"yyyy-MM-dd");
        String nowday = DateUtil.toString(nowDate,"yyyy-MM-dd");
        Boolean endflag = nowDate.before(end_date);
        Boolean starflag = nowDate.after(star_date);


        // 抽奖
        int luckresultindex = -1;
        String luckresultid = "";//抽中奖品id
        int lucknum = 0;//剩余抽奖次数
        Boolean issureluck = true;
        if ((endflag == true || qkjluckDrawAcitvity.getEndDate().equals(nowday)) && (starflag == true || qkjluckDrawAcitvity.getStrDate().equals(nowday))) { //在有效时间内
            if (qkjluckDrawAcitvity!=null&&(yltype==null||yltype.equals(""))) {// 非预览情况
                map.clear();
                map.put("unionid",unionid);
                map.put("activityId",id);
                if (qkjluckDrawAcitvity.getIseveryday()!=null&&qkjluckDrawAcitvity.getIseveryday()==1) { //每天一更新
                    map.put("everyday",nowday);
                }
                List<QkjluckDrawResultEntity> itemlistresult = qkjluckDrawResultService.queryAll(map);
                Boolean flag = true;
                if (qkjluckDrawAcitvity.getPoints()!=null && qkjluckDrawAcitvity.getPoints()>0) { //消耗积分大于0
                    if ((memberinfo.getIntegral() - qkjluckDrawAcitvity.getPoints()) < 0){
                        flag = false;
                    }
                }
                if (flag == true) {
                    String lucnum = luckDraw(qkjluckDrawAcitvity,itemlistresult,id,openid,unionid);
                    String[] res = lucnum.split(",");
                    if(!lucnum.equals("-1")&&!lucnum.equals("-2")&&res.length>1){ //有抽奖机会
                        luckresultindex = Integer.parseInt(res[0]);
                        luckresultid = res[1];
                        lucknum = Integer.parseInt(res[2]);
                    }else if (lucnum.equals("-2")){
                        luckresultindex = -2;
                    }
                } else {
                    luckresultindex = -3;
                }

            }
        } else {
            issureluck = false;
        }
        return RestResponse.success().put("drawacitvity", qkjluckDrawAcitvity).put("luckresultindex",luckresultindex).put("luckresultid",luckresultid).put("lucknum",lucknum).put("issureluck",issureluck).put("memberinfo",memberinfo);
    }

    /**
     * 修改抽奖状态已抽奖
     *
     * @param qkjluckDrawResult qkjluckDrawAcitvity
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/updateluckstatus")
    public RestResponse updateluckstatus(@RequestBody QkjluckDrawResultEntity qkjluckDrawResult) {
        String itemid = qkjluckDrawResult.getItemId();
        // 小程序用户及手机号
        String userid = qkjluckDrawResult.getUserid();
        String mobile= qkjluckDrawResult.getMobile();
        int luckresultindex = -1;
        String luckresultid = "";//抽中奖品id
        int lucknum =0;
        Map<String, Object> paramsa = new HashMap<>();
        paramsa.put("id",qkjluckDrawResult.getActivityId());
        QkjluckDrawAcitvityEntity qkjluckDrawAcitvity = qdklucbBean(qkjluckDrawResult.getActivityId(),paramsa);//查询活动

        String nowday = DateUtil.toString(new Date(),"yyyy-MM-dd");
        paramsa.clear();
        paramsa.put("unionid",qkjluckDrawResult.getUnionid());
        paramsa.put("activityId",qkjluckDrawResult.getActivityId());
        if (qkjluckDrawAcitvity.getIseveryday()!=null&&qkjluckDrawAcitvity.getIseveryday()==1) { //每天一更新
            paramsa.put("everyday",nowday);
        }
        List<QkjluckDrawResultEntity> itemlistresult = qkjluckDrawResultService.queryAll(paramsa);

//        String itemlsresult = "";
//
//        if (qkjluckDrawAcitvity.getIseveryday()!=null&&qkjluckDrawAcitvity.getIseveryday()==1) { //每天一更新
//            itemlsresult = jedisUtil.get("MTM_CACHE:LUCKACTIVITY:RESULT_" + qkjluckDrawResult.getActivityId() +":"+  qkjluckDrawResult.getUnionid()+"_" +nowday);
//        } else {
//            itemlsresult = jedisUtil.get("MTM_CACHE:LUCKACTIVITY:RESULT_" + qkjluckDrawResult.getActivityId() +":"+  qkjluckDrawResult.getUnionid());
//        }
//        itemlistresult = JSON.parseArray(itemlsresult, QkjluckDrawResultEntity.class);
        saveluckresult(qkjluckDrawResult);
        QkjluckMallInteEntity memberinfo= new QkjluckMallInteEntity();
        if(itemlistresult!=null){
            List<QkjluckDrawResultEntity> newitemlist = new ArrayList<>();
            for(QkjluckDrawResultEntity qd:itemlistresult){
                if (qd!=null&&qd.getActivityId().equals(qkjluckDrawResult.getActivityId())&&qd.getUnionid().equals(qkjluckDrawResult.getUnionid())&&qd.getItemId().equals(qkjluckDrawResult.getItemId())) {
                    qd.setNum(1);
                }
                newitemlist.add(qd);
            }
            String queryJsonStr = JsonHelper.toJsonString(newitemlist, "yyyy-MM-dd HH:mm:ss");
            if (qkjluckDrawAcitvity.getIseveryday()!=null&&qkjluckDrawAcitvity.getIseveryday()==1) { //每天一更新
                jedisUtil.set("MTM_CACHE:LUCKACTIVITY:RESULT_" + qkjluckDrawResult.getActivityId() +":"+  qkjluckDrawResult.getUnionid()+"_" +nowday,queryJsonStr,0);

            } else {
                jedisUtil.set("MTM_CACHE:LUCKACTIVITY:RESULT_" + qkjluckDrawResult.getActivityId() +":"+  qkjluckDrawResult.getUnionid(),queryJsonStr,0);

            }
            // 查询是否还有抽奖机会，有返回下一次抽奖结果
            List itemlist = new ArrayList<>();
            itemlist=getacitemlist(qkjluckDrawResult.getActivityId());
            qkjluckDrawAcitvity.setItemlist(itemlist);//查询奖项orderorder

            Boolean flag = true;

                // 获得积分  crmmemberid
                Integer luckmoney = 0;
                if (itemlist.size()>0) {
                    for (int i=0;i<itemlist.size();i++) {
                        QkjluckDrawAcitiityitemEntity item = new QkjluckDrawAcitiityitemEntity();
                        item=(QkjluckDrawAcitiityitemEntity)itemlist.get(i);
                        if (item.getId().equals(itemid)&&item.getPrizetakentype().equals("4")) //是积分情况
                            luckmoney = Integer.parseInt(item.getPrizetakenpath());
                    }
                }
                List<QkjluckMallInteEntity> memintes = new ArrayList<>();
                QkjluckMallInteEntity meminte = new QkjluckMallInteEntity();
                if (qkjluckDrawAcitvity.getPoints()!=null && qkjluckDrawAcitvity.getPoints() > 0) {
                    meminte = new QkjluckMallInteEntity();
                    meminte.setDescr("大转盘消耗积分");
                    meminte.setAction(18);
                    meminte.setUserid(userid);
                    meminte.setMobile(mobile);
                    meminte.setIntegral((~(qkjluckDrawAcitvity.getPoints() - 1)));
                    memintes.add(meminte);
                }
                if (luckmoney > 0) {
                    meminte = new QkjluckMallInteEntity();
                    meminte.setDescr("大转盘赢得积分");
                    meminte.setAction(13);
                    meminte.setUserid(userid);
                    meminte.setMobile(mobile);
                    meminte.setIntegral(luckmoney);
                    memintes.add(meminte);
                }
                String resultPost = null;
                if (memintes!=null&& memintes.size()>0) {
                    String JsonStr = JsonHelper.toJsonString(memintes);
                    try {
                        resultPost = HttpClient.sendPost(Vars.MALL_INTEGRAL_BATCHSEAL, JsonStr);
                    } catch (IOException e) {
                        System.out.println("大转盘得积分获得失败！");
                        try {
                            resultPost = HttpClient.sendPost(Vars.MALL_INTEGRAL_BATCHSEAL, JsonStr);
                        } catch (IOException ex) {
                            System.out.println("大转盘得积分二次获得失败！");
                            saveSysLog(JsonStr);
                            // ex.printStackTrace();
                        }
                        // e.printStackTrace();
                    }
                    JSONObject resultObject = JSON.parseObject(resultPost);
                    if ("0".equals(resultObject.get("code").toString())) {  //调用成功
                        System.out.println("大转盘得积分获得成功！");
                    } else {
                        try {
                            resultPost = HttpClient.sendPost(Vars.MALL_INTEGRAL_BATCHSEAL, JsonStr);
                        } catch (IOException ex) {
                            System.out.println("大转盘得积分重试获得失败！");
                            saveSysLog(JsonStr);
                            // ex.printStackTrace();
                        }
                    }
                }


                // 查询会员信息
                Map map = new HashMap();
                map.put("unionid", qkjluckDrawResult.getUnionid());
                queryJsonStr = JsonHelper.toJsonString(map);
                try {
                    resultPost = HttpClient.sendPost(Vars.MALL_INTEGRAL_SELECTBYUNIONID, queryJsonStr);
                    JSONObject resultObject  = JSON.parseObject(resultPost);
                    if ("0".equals(resultObject.get("code").toString())) {  //调用成功
                        memberinfo = JSON.parseObject(resultPost, QkjluckMallInteEntity.class);
                    } else {
                        memberinfo.setIntegral(0);
                    }
                } catch (IOException e) {
                    memberinfo.setIntegral(0);
                }

                if ((memberinfo.getIntegral() - qkjluckDrawAcitvity.getPoints()) < 0){
                    flag = false;
                }
            if (flag == true) {
                String lucnum = luckDraw(qkjluckDrawAcitvity,itemlistresult,qkjluckDrawResult.getActivityId(),qkjluckDrawResult.getOpenid(),qkjluckDrawResult.getUnionid());
                String[] res = lucnum.split(",");
                if(!lucnum.equals("-1")&&!lucnum.equals("-2")&&res.length>1) { //有抽奖机会
                    luckresultindex = Integer.parseInt(res[0]);
                    luckresultid = res[1];
                    lucknum = Integer.parseInt(res[2]);
                }else if (lucnum.equals("-2")){
                    luckresultindex = -2;
                }
            } else {
                luckresultindex = -3;
            }

        }

        return RestResponse.success().put("drawacitvity", qkjluckDrawAcitvity).put("luckresultindex",luckresultindex).put("luckresultid",luckresultid).put("lucknum",lucknum).put("memberinfo",memberinfo);
    }

    //查询活动
    public QkjluckDrawAcitvityEntity qdklucbBean(String id,Map<String, Object> paramsa){
        QkjluckDrawAcitvityEntity qkjluckDrawAcitvity = new QkjluckDrawAcitvityEntity();
        QkjluckDrawAcitvityEntity redisactivity = (QkjluckDrawAcitvityEntity) jedisUtil.getObject("MTM_CACHE:LUCKACTIVITY:INFO_" + id);
        if (redisactivity!=null&&redisactivity.getId()!=null) {
            qkjluckDrawAcitvity = redisactivity;
        } else {
            List<QkjluckDrawAcitvityEntity> list = qkjluckDrawAcitvityService.queryAll(paramsa);
            if(list!=null&&list.size()>0){
                qkjluckDrawAcitvity = list.get(0);
                jedisUtil.setObject("MTM_CACHE:LUCKACTIVITY:INFO_" + qkjluckDrawAcitvity.getId(),qkjluckDrawAcitvity,0);
            }
        }
        return qkjluckDrawAcitvity;
    }
    //查询奖品
    public List getacitemlist(String id){
        List itemlist = new ArrayList<>();
//        String itemls = jedisUtil.get("MTM_CACHE:LUCKACTIVITY:ITEM_" + id);
//        itemlist = JSON.parseArray(itemls,QkjluckDrawAcitiityitemEntity.class);
//        if (itemlist==null||itemlist.size()<=0) {
//            // 查询奖品
//            Map<String, Object> params = new HashMap<>();
//            params.put("activityId",id);
//            Page page = qkjluckDrawAcitiityitemService.queryPage(params);
//            List items = page.getRecords();
//            itemlist=new ArrayList<>();
//            itemlist=items;
//            String queryJsonStr = JsonHelper.toJsonString(items, "yyyy-MM-dd HH:mm:ss");
//            jedisUtil.set("MTM_CACHE:LUCKACTIVITY:ITEM_" + id,queryJsonStr,0);
//        }
        // 查询奖品
        Map<String, Object> params = new HashMap<>();
        params.put("activityId",id);
        Page page = qkjluckDrawAcitiityitemService.queryPage(params);
        itemlist=new ArrayList<>();
        itemlist = page.getRecords();
        return itemlist;
    }
    //抽奖
    public String luckDraw (QkjluckDrawAcitvityEntity qkjluckDrawAcitvity,List<QkjluckDrawResultEntity> itemlist,String id,String openid,String unionid) {
        int num = qkjluckDrawAcitvity.getPeonum()!=null ? qkjluckDrawAcitvity.getPeonum() : 1; //本活动每人的抽奖次数 0代表无穷大
        String index= "-1";//默认没有抽奖机会
        int lucknum = itemlist!=null ? itemlist.size():0;//已抽奖次数
        String checkid = "";//有未实际抽的奖品
        if (itemlist!=null&&itemlist.size()>0) {
            lucknum = 0;
            for (QkjluckDrawResultEntity qdre:itemlist) {
                if(qdre!=null&&(qdre.getNum()==null||(qdre.getNum()!= null&&qdre.getNum()==0))){//有未抽的奖品
                    checkid = qdre.getItemId();
                } else {
                    lucknum += 1;
                }
            }
            if (!checkid.equals("")) {
                int len =qkjluckDrawAcitvity.getItemlist().size();
                String [] array = new String[len];
                for (int i = 0; i < len; i++) {
                    array[i]=qkjluckDrawAcitvity.getItemlist().get(i).getId();
                    if (array[i].equals(checkid)) {
                        index= i+","+array[i]+"" + "," + (num - lucknum);
                        break;
                    }
                }
            }

        }
        if (checkid.equals("")){
            if ((num - lucknum)>0 || num == 0) {//开始 num=0无穷大
                int n;
                Random random = new Random();
                //String [] array = new String[100];
                List<String> arraylist = new ArrayList<String>();
                // int cindex = 0;
                List<QkjluckDrawAcitiityitemEntity> newarraylist= new ArrayList<>();//还有库存的奖品列表
                Map<String, Integer> zjindex= new HashMap<>(); //每个奖项的索引
                for (int i=0;i<qkjluckDrawAcitvity.getItemlist().size();i++) {
                    Integer number = qkjluckDrawAcitvity.getItemlist().get(i).getNumber();// 库存总数
                    String itemid = qkjluckDrawAcitvity.getItemlist().get(i).getId();
                    zjindex.put(itemid,i);
                    int totelnum=0;
                    if (number!=null) {
                        totelnum = number;
                    }
                    Boolean ischeck = false;
                    if (totelnum == 0) { //数量不限制
                        ischeck = true;
                    } else {
                        //其它人这个奖项的抽奖个数
                        Map<String, Object> params= new HashMap<>();
                        params.put("activityId",id);
                        List<QkjluckDrawResultEntity> oterresult = qkjluckDrawResultService.queryAll(params);
                        int othernum = 0;
                        for (QkjluckDrawResultEntity other:oterresult) {
                            if (other.getItemId().equals(itemid)) {//是本奖项
                                othernum +=1;
                            }
                        }
                        if ((totelnum-othernum)<=0) { //其它人抽奖结果与总数相等则删除此奖品
                            ischeck = false;
                        } else {
                            ischeck = true;
                        }
                    }
                    if (ischeck == true) {
                        newarraylist.add(qkjluckDrawAcitvity.getItemlist().get(i));
                    }
                }

                // arraylist 从小到大排序
                if (newarraylist.size()>0) { //有奖品
                    List<QkjluckDrawAcitiityitemEntity> sortlist= new ArrayList<>();
                    sortlist=sortListData(newarraylist);
                    int selected = getPrizeIndex(sortlist);
                    String zjid = sortlist.get(selected).getId();
                    int cindex = zjindex.get(zjid);
                    index= cindex+","+zjid+"," + (num - lucknum);

                    // 保存抽奖结果
                    List<QkjluckDrawResultEntity> qdes = new ArrayList<>();
                    QkjluckDrawResultEntity qkjluckDrawResult = new QkjluckDrawResultEntity();
                    qkjluckDrawResult.setActivityId(id);
                    qkjluckDrawResult.setOpenid(openid);
                    qkjluckDrawResult.setUnionid(unionid);
                    qkjluckDrawResult.setItemId(zjid);
                    qkjluckDrawResult.setNum(0);
                    qkjluckDrawResult.setAddtime(new Date());
                    qdes.add(qkjluckDrawResult);
                    String queryJsonStr = JsonHelper.toJsonString(qdes, "yyyy-MM-dd HH:mm:ss");
                    if (itemlist != null && itemlist.size() > 0) {
                        itemlist.add(qkjluckDrawResult);
                        queryJsonStr = JsonHelper.toJsonString(itemlist, "yyyy-MM-dd HH:mm:ss");
                    }
                    //更新redis
                    if (qkjluckDrawAcitvity.getIseveryday()!=null&&qkjluckDrawAcitvity.getIseveryday()==1) { //每天一更新
                        String nowday = DateUtil.toString(new Date(),"yyyy-MM-dd");
                        jedisUtil.set("MTM_CACHE:LUCKACTIVITY:RESULT_" + id +":"+ unionid+"_" +nowday, queryJsonStr,0);
                    } else {
                        jedisUtil.set("MTM_CACHE:LUCKACTIVITY:RESULT_" + id +":"+ unionid, queryJsonStr,0);
                    }

                    //添加数据库（异步）
                    addluckresult(qkjluckDrawResult);
                } else { //所有奖品都无库存
                    index = "-2";
                }

            }

        }
        return index;
    }

    /**
     * 根据Math.random()产生一个double型的随机数，判断每个奖品出现的概率
     * @param prizes
     * @return random：奖品列表prizes中的序列（prizes中的第random个就是抽中的奖品）
     */
    public int getPrizeIndex(List<QkjluckDrawAcitiityitemEntity> prizes) {
        DecimalFormat df = new DecimalFormat("######0.00");
        int random = -1;
        try{
            //计算总权重
            double sumWeight = 0;
            for(QkjluckDrawAcitiityitemEntity p : prizes){
                sumWeight += p.getWeight();
            }

            //产生随机数
            double randomNumber;
            randomNumber = Math.random();

            //根据随机数在所有奖品分布的区域并确定所抽奖品
            double d1 = 0;
            double d2 = 0;
            for(int i=0;i<prizes.size();i++){
                d2 += Double.parseDouble(String.valueOf(prizes.get(i).getWeight()))/sumWeight;
                if(i==0){
                    d1 = 0;
                }else{
                    d1 +=Double.parseDouble(String.valueOf(prizes.get(i-1).getWeight()))/sumWeight;
                }
                if(randomNumber >= d1 && randomNumber <= d2){
                    random = i;
                    break;
                }
            }
        }catch(Exception e){
            System.out.println("生成抽奖随机数出错，出错原因：" +e.getMessage());
        }
        return random;
    }


    @Async
    public void addluckresult (QkjluckDrawResultEntity qkjluckDrawResult) {
        qkjluckDrawResultService.add(qkjluckDrawResult);
    }
    public void saveluckresult (QkjluckDrawResultEntity qkjluckDrawResult) {
        qkjluckDrawResultService.updateByPama(qkjluckDrawResult);
    }

    /**
     * 新增
     *
     * @param qkjluckDrawAcitvity qkjluckDrawAcitvity
     * @return RestResponse
     */
    @SysLog("新增&修改")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjluckDrawAcitvityEntity qkjluckDrawAcitvity) {
        if (qkjluckDrawAcitvity!=null&&qkjluckDrawAcitvity.getId()!=null&&!qkjluckDrawAcitvity.getId().equals("")) {
            qkjluckDrawAcitvityService.update(qkjluckDrawAcitvity);

        } else {
            // qkjluckDrawAcitvity.setAdduser("1");

            qkjluckDrawAcitvity.setAddtime(new Date());
            qkjluckDrawAcitvityService.add(qkjluckDrawAcitvity);
        }
        // 删除所有项目
        qkjluckDrawAcitiityitemService.deleteBatchByMain(qkjluckDrawAcitvity.getId());
        if (qkjluckDrawAcitvity!=null&&qkjluckDrawAcitvity.getItemlist()!=null&&qkjluckDrawAcitvity.getItemlist().size()>0) {
            for (QkjluckDrawAcitiityitemEntity item:qkjluckDrawAcitvity.getItemlist()) {
                if(item.getPrizetakenpath()!=null){
                    if(item.getPrizetakentype().equals("1")){ //小程序拼接参数
                        String str ="&activity=";
                        if(!item.getPrizetakenpath().contains("?")){
                            str = "?activity=";
                        }
                        str = item.getPrizetakenpath() + str + "turntable-" + qkjluckDrawAcitvity.getId();
                        item.setPrizetakenpathresult(str);
                    }else if (item.getPrizetakentype().equals("3")){ //产品
                        //String str = "/pages/product/detail" + "?from=turntable&gift=1&pid=" + item.getPrizetakenpath();
                        String str = "/components/address/address";
                        item.setPrizetakenpathresult(str);
                    }else if (item.getPrizetakentype().equals("4")){ //积分记录查询
                        String str = "/pages/creditList/creditList?showType=3";
                        item.setPrizetakenpathresult(str);
                    }else {
                        item.setPrizetakenpathresult(item.getPrizetakenpath());
                    }
                }
                item.setActivityId(qkjluckDrawAcitvity.getId());
                qkjluckDrawAcitiityitemService.add(item);
            }
        }

        //修改产品的连接
        // qkjluckDrawAcitiityitemService.update(qkjluckDrawAcitvity.getId());

        //更新redis
        // 查询奖品
        Map<String, Object> params = new HashMap<>();
        params.put("activityId",qkjluckDrawAcitvity.getId());
        Page page = qkjluckDrawAcitiityitemService.queryPage(params);
        List<QkjluckDrawAcitiityitemEntity> items = page.getRecords();
        qkjluckDrawAcitvity.setItemlist(items);
        jedisUtil.setObject("MTM_CACHE:LUCKACTIVITY:INFO_" + qkjluckDrawAcitvity.getId(),qkjluckDrawAcitvity,0);
        String queryJsonStr = JsonHelper.toJsonString(items, "yyyy-MM-dd HH:mm:ss");
        jedisUtil.set("MTM_CACHE:LUCKACTIVITY:ITEM_" + qkjluckDrawAcitvity.getId(),queryJsonStr,0);
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjluckDrawAcitvity qkjluckDrawAcitvity
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjluckDrawAcitvityEntity qkjluckDrawAcitvity) {

        qkjluckDrawAcitvityService.update(qkjluckDrawAcitvity);

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
    public RestResponse delete(@RequestBody String[] ids) {
        qkjluckDrawAcitvityService.deleteBatch(ids);

        return RestResponse.success();
    }

    private void saveSysLog(String params) {

        SysLogEntity sysLog = new SysLogEntity();

        try {
            sysLog.setOperation("访问批量操作积分接口出错");
            sysLog.setParams(params);
            sysLog.setTime((long) 10000);
            sysLog.setCreateTime(new Date());
            //保存系统日志
            sysLogService.save(sysLog);
        } catch (Exception ignored) {

        }
    }

    public static List<QkjluckDrawAcitiityitemEntity> sortListData(List<QkjluckDrawAcitiityitemEntity> list) {
        Collections.sort(list, (o1, o2) -> {
            if (o2.getWeight() < o1.getWeight()) {
                return 1;
            }
            return -1;
        });
        return list;
    }

}
