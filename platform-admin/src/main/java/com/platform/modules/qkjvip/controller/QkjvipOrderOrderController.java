/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderOrderController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-10 11:37:08        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.accesstoken.service.AccesstokenService;
import com.platform.modules.qkjInterface.entity.UserMsgEntity;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import com.platform.modules.qkjvip.entity.*;
import com.platform.modules.qkjvip.service.*;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.sys.service.SysUserChannelService;
import com.platform.modules.util.DingMsg;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.Vars;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-03-10 11:37:08
 */
@RestController
@RequestMapping("qkjvip/orderorder")
public class QkjvipOrderOrderController extends AbstractController {
    @Autowired
    private QkjvipOrderOrderService qkjvipOrderOrderService;
    @Autowired
    private QkjvipOrderOrderdetailService qkjvipOrderOrderdetailService;
    @Autowired
    private QkjvipProductStockService qkjvipProductStockService;
    @Autowired
    private QkjvipOrderDeliverlogService qkjvipOrderDeliverlogService;
    @Autowired
    private SysUserChannelService sysUserChannelService;
    @Autowired
    private QkjvipOrderErporderService qkjvipOrderErporderService;
    @Autowired
    private QkjvipOrderWareproService qkjvipOrderWareproService;
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;
    @Autowired
    private QkjtakeRWineService qkjtakeRWineService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private AccesstokenService accesstokenService;
    @Autowired
    private QkjsmsRInfoService qkjsmsRInfoService;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderOrderEntity> list = qkjvipOrderOrderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 查看最近收货地址
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryorderbyMember")
    public RestResponse queryorderbyMember(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderOrderEntity> list = qkjvipOrderOrderService.queryorderbyMember(params);
        QkjvipOrderOrderEntity qkjvipo=new QkjvipOrderOrderEntity();
        if(list!=null&&list.size()>0){
            qkjvipo=list.get(0);
        }
        return RestResponse.success().put("qkjvipOrderAddress", qkjvipo);
    }

    /**
     * 分页查询
     *
     * @param
     * @return RestResponse
     */
    @PostMapping("/list")
    public RestResponse list(@RequestBody QkjvipOrderOrderQuaryEntity order) throws IOException {
        Set<String> list = new HashSet<>();
        if (order!=null&&order.getDatetype()!=null) {
            Date date=new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date2=sdf.format(date);
            Calendar c = Calendar.getInstance();
            if (order.getDatetype()==1) {//一个月
                c.setTime(new Date());
                c.add(Calendar.MONTH, -1);
                Date m = c.getTime();
                String mon = sdf.format(m);
                order.setStartorderdate(mon + " 00:00:00");
            } else if (order.getDatetype()==2) {//三个月
                //过去三个月
                c.setTime(new Date());
                c.add(Calendar.MONTH, -3);
                Date m3 = c.getTime();
                String mon3 = sdf.format(m3);
                order.setStartorderdate(mon3 + " 00:00:00");
            }else if (order.getDatetype()==3) { //半年
                c.setTime(new Date());
                c.add(Calendar.MONTH, -6);
                Date m3 = c.getTime();
                String mon3 = sdf.format(m3);
                order.setStartorderdate(mon3 + " 00:00:00");
            }else if (order.getDatetype()==4) {//一年
                c.setTime(new Date());
                c.add(Calendar.YEAR, -1);
                Date y = c.getTime();
                String year = sdf.format(y);
                order.setStartorderdate(year + " 00:00:00");
            }
            order.setEndorderdate(date2 + " 23:59:59");
        }
        list=ContextHelper.setOrdertypesm("qkjvip:memberactivity:list",getUserId());
        StringBuilder typs = new StringBuilder();
        if(order!=null&&order.getListordertype()!=null){
            String[] liststr=order.getListordertype().split(",");
            for(String l:liststr){
                for (String str:list) {
                    if(l.equals(str)){ //有权限
                        typs.append(str+",");
                    }
                }
            }

        }else{
            for (String str:list) {
                typs.append(str+",");
            }
        }
        if (list.size()>0) {
            order.setListordertype(typs.toString().substring(0,typs.length()-1));
        } else { //无任何类别查询权限
            //order.setListordertype("0");
        }
        order.setListorgno(ContextHelper.getPermitDepts("qkjvip:memberactivity:list"));
        if (!getUser().getUserName().contains("admin")) {  // 空默认是全部所有权限
            order.setCurrentmemberid(getUserId());
//            memberQuery.setListorgno(sysRoleOrgService.queryOrgNoListByUserIdAndPerm(getUserId(), "qkjvip:member:list"));
            order.setListmemberchannel("0".equals(sysUserChannelService.queryChannelIdByUserId(getUserId())) ? "-1" : sysUserChannelService.queryChannelIdByUserId(getUserId())); // 0代表选择的是全部渠道传-1
        } else {
            order.setListmemberchannel("-1");
        }
        Object obj = JSONArray.toJSON(order);
        String queryJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_LIST, queryJsonStr);
        System.out.println("订单检索条件：" + queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //调用成功
            List<QkjvipOrderOrderEntity> orderList = new ArrayList<>();
            orderList = JSON.parseArray(resultObject.getString("listorder"),QkjvipOrderOrderEntity.class);
            Map<String, Object> params = new HashMap<>();
            params.put("state",0);
            List<QkjsmsRInfoEntity> sms = qkjsmsRInfoService.queryAll(params);
            if (orderList!=null) {
                orderList.stream().forEach(item -> {
                    List<QkjsmsRInfoEntity> smsnew = sms.stream().filter(hitem -> hitem.getOrderid()!=null&&hitem.getOrderid().equals(item.getOrderid())).collect(Collectors.toList());
                    item.setIssms(smsnew.size()>0 ? 1 : 0);
                });
            }

            Page page = new Page();
            page.setRecords(orderList);
            page.setTotal(Long.parseLong(resultObject.get("totalcount").toString()));
            page.setSize(order.getPagesize() == null? 0 : order.getPagesize());
            page.setCurrent(order.getPageindex() == null? 0 : order.getPageindex());
            return RestResponse.success().put("page", page);
        } else {
            return RestResponse.error(resultObject.get("descr").toString());
        }
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/listinfo")
    public RestResponse listinfo(@RequestParam Map<String, Object> params) {
        String wareproid = params.get("wareproid") + "";
        if (wareproid!=null&&!wareproid.equals("")) {
            Map<String, Object> map = new HashMap<>();
            map.put("wareproid",wareproid);
            List<QkjwineRInfoEntity> wines = qkjwineRInfoService.selectSqlSimple(map);
            if(wines.size()>0 && wines.get(0)!=null && wines.get(0).getScenephone()!=null && !wines.get(0).getScenephone().equals("")){
                params.put("scenephone",wines.get(0).getScenephone());
            }
        }
        Page page = qkjvipOrderOrderService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) throws IOException {
        //QkjvipOrderOrderEntity qkjvipOrderOrder = qkjvipOrderOrderService.getById(id);
        QkjvipOrderOrderQuaryEntity order=new QkjvipOrderOrderQuaryEntity();
        order.setMorderid(id);
        Object obj = JSONArray.toJSON(order);
        String queryJsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_LISTDETILE, queryJsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        QkjvipOrderOrderEntity qkjvipOrderOrder = JSON.toJavaObject(resultObject,QkjvipOrderOrderEntity.class);
        qkjvipOrderOrder.setId(qkjvipOrderOrder.getMorderid());
        qkjvipOrderOrder.setOrderid(qkjvipOrderOrder.getMorderid());
        //查询库存分配
        if(qkjvipOrderOrder.getOrdertype()==null){
            qkjvipOrderOrder.setOrdertype(5);
        }
        if(qkjvipOrderOrder.getOrdertype()==5){
            List<QkjvipProductStockEntity> liststocks=new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            params.put("orderid",qkjvipOrderOrder.getMorderid());
            liststocks=qkjvipProductStockService.queryAll(params);
            List<QkjvipOrderDeliverlogEntity> listout=new ArrayList<>();
            listout=qkjvipOrderDeliverlogService.queryAll(params);
            // 查询酒证
            List<QkjwineRInfoEntity> wines = new ArrayList<>();
            wines=qkjwineRInfoService.queryAll(params);
            List<QkjvipProductProductEntity> newps=new ArrayList<>();
            if(qkjvipOrderOrder!=null&&qkjvipOrderOrder.getListproduct()!=null){
                for(QkjvipProductProductEntity es:qkjvipOrderOrder.getListproduct()){
                    Double outsum=0.00;
                    for(QkjvipProductStockEntity e:liststocks){
                        if(es.getProductid().equals(e.getProductid())){
                            if(e.getOuttotalcount()!=null){
                                outsum+=e.getOuttotalcount().doubleValue();
                            }
                        }
                    }
                    es.setOutcount(outsum);
                    newps.add(es);
                }
            }
            qkjvipOrderOrder.setListproduct(newps);
            qkjvipOrderOrder.setListstock(liststocks);
            qkjvipOrderOrder.setListout(listout);
            qkjvipOrderOrder.setWines(wines);

        }
        //查询封坛
        List<QkjvipOrderErporderEntity> list = new ArrayList<>();
        if (qkjvipOrderOrder!=null&&qkjvipOrderOrder.getErpordercode()!=null&&!qkjvipOrderOrder.getErpordercode().equals("")){
            Map<String, Object> params= new HashMap<>();
            params.put("ordercode",qkjvipOrderOrder.getErpordercode());
            list = qkjvipOrderErporderService.queryAllDetail(params);
        }
        return RestResponse.success().put("qkjvipOrderOrder", qkjvipOrderOrder).put("erplist",list);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrder qkjvipOrderOrder
     * @return RestResponse
     */
    @SysLog("新增&修改")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) throws IOException {
        qkjvipOrderOrder.setListuserfile(null);
        qkjvipOrderOrder.setListfinalfile(null);
        qkjvipOrderOrder.setCreatoradminid(getUserId());
        qkjvipOrderOrder.setCreatoradmin(getUser().getUserName());
        if(qkjvipOrderOrder.getCrmMemberid()!=null){
            qkjvipOrderOrder.setMemberid(qkjvipOrderOrder.getCrmMemberid());
        }
        Object obj = JSONArray.toJSON(qkjvipOrderOrder);
        String JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_ADD, JsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"200".equals(resultObject.get("resultcode").toString())) {  //清洗失败
            return RestResponse.error(resultObject.get("descr").toString());
        }
        //qkjvipOrderOrderService.add(qkjvipOrderOrder);
        return RestResponse.success();
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrder qkjvipOrderOrder
     * @return RestResponse
     */
    @SysLog("推送商城")
    @RequestMapping("/savetocom")
    public RestResponse savetocom(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) throws IOException {
        // 修改信息
        qkjvipOrderOrder.setListuserfile(null);
        qkjvipOrderOrder.setListfinalfile(null);
        qkjvipOrderOrder.setCreatoradminid(getUserId());
        qkjvipOrderOrder.setCreatoradmin(getUser().getUserName());
        if(qkjvipOrderOrder.getCrmMemberid()!=null){
            qkjvipOrderOrder.setMemberid(qkjvipOrderOrder.getCrmMemberid());
        }
        Object obj = JSONArray.toJSON(qkjvipOrderOrder);
        String JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_ADD, JsonStr);

        // 推送
        QkjvipOrderOrderEntity newo =new QkjvipOrderOrderEntity();
        newo.setOrderstatus(20);
        newo.setMorderid(qkjvipOrderOrder.getMorderid());
        newo.setToqkh(1);
        obj = JSONArray.toJSON(newo);
        JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_MDYSTATUS, JsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"200".equals(resultObject.get("resultcode").toString())) {  //清洗失败
            return RestResponse.error(resultObject.get("descr").toString());
        }
        //qkjvipOrderOrderService.add(qkjvipOrderOrder);
        return RestResponse.success();
    }

    /**
     * 新增
     *
     * @param
     * @return RestResponse
     */
    @SysLog("转换正式订单")
    @RequestMapping("/savestatus")
    public RestResponse savestatus(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) throws IOException {
        // 推送
        QkjvipOrderOrderEntity newo =new QkjvipOrderOrderEntity();
        newo.setOrderstatus(70);
        newo.setMorderid(qkjvipOrderOrder.getId());
        newo.setToqkh(0);
        Object obj = JSONArray.toJSON(newo);
        String JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_MDYSTATUS, JsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if (!"200".equals(resultObject.get("resultcode").toString())) {  //清洗失败
            return RestResponse.error(resultObject.get("descr").toString());
        }
        //qkjvipOrderOrderService.add(qkjvipOrderOrder);
        return RestResponse.success();
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrder qkjvipOrderOrder
     * @return RestResponse
     */
    @SysLog("新增&修改封坛")
    @RequestMapping("/saveft")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse saveft(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) throws IOException {
        int infoflag = 0;
        StringBuffer products = new StringBuffer();
        Integer stocklistnew=qkjvipOrderOrder.getListstock().size();// 当前入库分配数量
        Integer outlistnew=qkjvipOrderOrder.getListout().size();// 当前入库分配数量
        Map<String, Object> params = new HashMap<>();
        params.put("orderid",qkjvipOrderOrder.getMorderid());
        List<QkjvipProductStockEntity> liststocksold=qkjvipProductStockService.queryAll(params); // 旧出库数量
        List<QkjvipOrderDeliverlogEntity> listoutold=qkjvipOrderDeliverlogService.queryAll(params);// 旧出库数量

        if (qkjvipOrderOrder!=null&&qkjvipOrderOrder.getId()!=null&&!qkjvipOrderOrder.getId().equals("")) infoflag=1;
        if (qkjvipOrderOrder!=null && qkjvipOrderOrder.getWineid()!=null&&!qkjvipOrderOrder.getWineid().equals("") && qkjvipOrderOrder.getWdata()!=null && !qkjvipOrderOrder.getWdata().equals("")) {
            //修改酒证资料
            QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
            wine.setWdata(qkjvipOrderOrder.getWdata());
            wine.setId(qkjvipOrderOrder.getWineid());
            qkjwineRInfoService.update(wine);
        }
        Object flag = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
        qkjvipOrderOrder.setCreatoradminid(getUserId());
        qkjvipOrderOrder.setCreatoradmin(getUser().getUserName());
        if(qkjvipOrderOrder.getCrmMemberid()!=null){
            qkjvipOrderOrder.setMemberid(qkjvipOrderOrder.getCrmMemberid());
        }
        Object obj = JSONArray.toJSON(qkjvipOrderOrder);
        String JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
        String resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_ADD, JsonStr);
        JSONObject resultObject = JSON.parseObject(resultPost);
        if ("200".equals(resultObject.get("resultcode").toString())) {  //修改成功
            String orderid=resultObject.get("morderid").toString();
            // 转正式订单
            QkjvipOrderOrderEntity newo =new QkjvipOrderOrderEntity();
            newo.setOrderstatus(70);
            newo.setMorderid(orderid);
            newo.setToqkh(0);
            obj = JSONArray.toJSON(newo);
            JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
            resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_MDYSTATUS, JsonStr);
            resultObject = JSON.parseObject(resultPost);
            if (!"200".equals(resultObject.get("resultcode").toString())) {  //转正式失败
                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(flag);
                return RestResponse.error(resultObject.get("descr").toString());
            } else {
                //添加入库分配
                qkjvipProductStockService.deleteBatchByOrder(orderid);
                if(qkjvipOrderOrder.getListstock()!=null&&qkjvipOrderOrder.getListstock().size()>0){
                    List<QkjvipProductStockEntity> liststock = qkjvipOrderOrder.getListstock().stream().map(item -> {
                        products.append(item.getProductname()+",");
                        item.setProductcount(item.getOuttotalcount()==null ? item.getIntotalcount(): item.getIntotalcount().subtract(item.getOuttotalcount()));
                        item.setCreator(getUserId());
                        item.setCreateon(new Date());
                        item.setOrderid(orderid);
                        QkjvipOrderWareproEntity wpentity = new QkjvipOrderWareproEntity();
                        if (item.getWarehouseid() != null && item.getProductid() != null) {
                            // 查询此仓库是否有此商品
                            if ((item == null || item.getWareproid()==null || item.getWareproid().equals("")) && item.getIsorderadd()!=null&&item.getIsorderadd().equals("1")) { // 仓库没有产品则入库
                                QkjvipOrderWareproEntity qkjvipOrderWarepro = new QkjvipOrderWareproEntity();
                                qkjvipOrderWarepro.setWareid(item.getWarehouseid());
                                qkjvipOrderWarepro.setState(0);
                                qkjvipOrderWarepro.setProid(item.getProductid());
                                qkjvipOrderWarepro.setProname(item.getProductname());
                                qkjvipOrderWarepro.setCreator(getUserId());
                                qkjvipOrderWarepro.setCreateon(new Date());
                                qkjvipOrderWarepro.setUpdatetime(new Date());
                                qkjvipOrderWareproService.add(qkjvipOrderWarepro);
                            }
                            wpentity.setWareid(item.getWarehouseid());
                            wpentity.setProid(item.getProductid());
                            wpentity.setState(1); // 已认购
                            qkjvipOrderWareproService.updateStateByProAndWare(wpentity);
                            // 更新酒证信息 根据wareproid
                            QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
                            wine.setOrderid(orderid);
                            wine.setOrdertype(0);
                            wine.setMemberid(qkjvipOrderOrder.getMemberid());
                            wine.setWareporid(item.getWareproid());
                            qkjwineRInfoService.updateWineInfo(wine);

                            //  更新提货单
//                            QkjtakeRWineEntity qkjtakeRWine = new QkjtakeRWineEntity();
//                            qkjtakeRWine.setOrderid(orderid);
//                            qkjtakeRWine.setOrdertype(0);
//                            qkjtakeRWine.setMemberid(qkjvipOrderOrder.getMemberid());
//                            qkjtakeRWine.setWareproid(item.getWareproid());
//                            qkjtakeRWine.setMembername(qkjvipOrderOrder.getMembername());
//                            qkjtakeRWineService.updateTakeByWareproid(qkjtakeRWine);

                            //历史 (是否正式订单的时候再加入历史以免历史多余)
                            QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
                            qkjvipOrderWareprohistory.setState(1);
                            qkjvipOrderWareprohistory.setNum(1);
                            qkjvipOrderWareprohistory.setProid(item.getProductid());
                            qkjvipOrderWareprohistory.setProname(item.getProductname());
                            qkjvipOrderWareprohistory.setWareid(item.getWarehouseid());
                            qkjvipOrderWareprohistory.setCreateon(new Date());
                            qkjvipOrderWareprohistory.setCreator(getUserId());
                            qkjvipOrderWareprohistory.setOrderid(orderid);
                            if (qkjvipOrderOrder.getId() == null || qkjvipOrderOrder.getId().equals(""))qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
                        }
                        return item;
                    }).collect(Collectors.toList());
                    qkjvipProductStockService.batchAdd(liststock);
                }

                //添加出库记录
                if(qkjvipOrderOrder.getListout()!=null&&qkjvipOrderOrder.getListout().size()>0){
                    qkjvipOrderDeliverlogService.deleteBatchByOrder(orderid);
                    Date date = new Date();//获取当前的日期
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String str = df.format(date);//获取String类型的时间
                    StringBuffer sbs=new StringBuffer();
                    List<QkjvipOrderDeliverlogEntity> liststock = qkjvipOrderOrder.getListout().stream().map(item -> {
                        item.setSaleunit(5);
                        item.setCreator(getUserId());
                        item.setCreateon(str);
                        item.setOrderid(orderid);
                        sbs.append(item.getProductname()+",");
                        QkjvipOrderWareproEntity wpentity = new QkjvipOrderWareproEntity();
                        if (item.getWarehouseid() != null && item.getProductid() != null) {
                            // 更新提货表状态
                            QkjtakeRWineEntity qwe = new QkjtakeRWineEntity();
                            qwe.setState(2);
                            qwe.setProid(item.getProductid());
                            qwe.setWareid(item.getWarehouseid());
                            qwe.setOperationtime(new Date());
                            qwe.setOperator(getUserId());
                            qkjtakeRWineService.updateTakeStateById(qwe);

                            //删除入库记录
                            wpentity.setWareid(item.getWarehouseid());
                            wpentity.setProid(item.getProductid());
                            wpentity.setDisabled(1);
                            qkjvipOrderWareproService.updateDisabledByProAndWare(wpentity);

                            //历史 (是否正式订单的时候再加入历史以免历史多余)
                            QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
                            qkjvipOrderWareprohistory.setState(4);
                            qkjvipOrderWareprohistory.setNum(1);
                            qkjvipOrderWareprohistory.setProid(item.getProductid());
                            qkjvipOrderWareprohistory.setProname(item.getProductname());
                            qkjvipOrderWareprohistory.setWareid(item.getWarehouseid());
                            qkjvipOrderWareprohistory.setCreateon(new Date());
                            qkjvipOrderWareprohistory.setCreator(getUserId());
                            qkjvipOrderWareprohistory.setOrderid(orderid);
                            if (qkjvipOrderOrder.getId() != null && !qkjvipOrderOrder.getId().equals(""))qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
                        }
                        return item;
                    }).collect(Collectors.toList());
                    qkjvipOrderDeliverlogService.batchAdd(liststock);
                    // 发送出库消息
                    UserMsgEntity userMsgEntity = new UserMsgEntity();
                    //userMsgEntity.setUrl("https://wxaurl.cn/K0vrkhelCbg");
                    userMsgEntity.setTitle("封坛订单通知");
                    String str2 = sbs.length()>0 ? sbs.substring(0,sbs.length()-1): "";
                    userMsgEntity.setMsg("尊贵的" + qkjvipOrderOrder.getMembername()+"您好：您的"+str2+"已经提货完成，请登录青稞荟小程序查看详情");
                    userMsgEntity.setMobilelist(qkjvipOrderOrder.getCellphone());
                    if(outlistnew>listoutold.size())sendmsg(userMsgEntity);
                }
            }
        }else {
            TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(flag);
            return RestResponse.error(resultObject.get("descr").toString());
        }
        // 给会员发短信添加时且分配存储变更时
        if (infoflag == 0 && qkjvipOrderOrder!=null && qkjvipOrderOrder.getCellphone()!=null&&!qkjvipOrderOrder.getCellphone().equals("") && stocklistnew>liststocksold.size()) {
// 短信钉钉通知
//            UserMsgEntity userMsgEntity = new UserMsgEntity();
//            userMsgEntity.setUrl("https://wxaurl.cn/K0vrkhelCbg");
//            userMsgEntity.setTitle("封坛订单通知");
//            String str = products.length()>0 ? products.substring(0,products.length()-1): "";
//            userMsgEntity.setMsg("尊贵的" + qkjvipOrderOrder.getMembername()+"您好：专属于您的"+str+"的酒证已经生成，请点击链接登录青稞荟小程序，查看酒证；");
//            userMsgEntity.setMobilelist(qkjvipOrderOrder.getCellphone());
//            sendmsg(userMsgEntity);
        }
        //qkjvipOrderOrderService.add(qkjvipOrderOrder);
        return RestResponse.success();
    }

    public void sendmsg(UserMsgEntity userMsgEntity) {
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
        }
    }

    /**
     * 新增
     *
     * @param qkjvipOrderOrder qkjvipOrderOrder
     * @return RestResponse
     */
    @SysLog("视图界面绑定封坛订单")
    @RequestMapping("/updateft")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse updateft(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) throws IOException {
        //添加入库分配
        // 查询此订单此商品是否已分配存储
        StringBuffer products = new StringBuffer();
        if(qkjvipOrderOrder.getListstock()!=null&&qkjvipOrderOrder.getListstock().size()>0){
            List<QkjvipProductStockEntity> liststock = qkjvipOrderOrder.getListstock().stream().map(item -> {
                // 查询此仓库是否有此商品
                Map<String, Object> params= new HashMap<>();
                params.put("productid",item.getProductid());
                params.put("orderid",qkjvipOrderOrder.getId());
                products.append(item.getProductname()+",");
                List<QkjvipProductStockEntity> sts = qkjvipProductStockService.queryAll(params);
                if (sts.size()<=0 || (sts.size()>0 &&sts.size()<sts.get(0).getDetailnum())) {
                    item.setProductcount(BigDecimal.valueOf(1));
                    item.setCreator(getUserId());
                    item.setCreateon(new Date());
                    item.setOrderid(qkjvipOrderOrder.getId());
                    QkjvipOrderWareproEntity wpentity = new QkjvipOrderWareproEntity();
                    if (item.getWarehouseid() != null && item.getProductid() != null) {
                        wpentity.setWareid(item.getWarehouseid());
                        wpentity.setProid(item.getProductid());
                        wpentity.setState(1); // 已认购
                        qkjvipOrderWareproService.updateStateByProAndWare(wpentity);
                        // 更新酒证信息 根据wareproid
                        QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
                        wine.setOrderid(qkjvipOrderOrder.getId());
                        wine.setOrdertype(0);
                        wine.setMemberid(qkjvipOrderOrder.getMemberid());
                        wine.setWareporid(item.getWareproid());
                        qkjwineRInfoService.updateWineInfo(wine);

                        //  更新提货单
//                        QkjtakeRWineEntity qkjtakeRWine = new QkjtakeRWineEntity();
//                        qkjtakeRWine.setOrderid(qkjvipOrderOrder.getId());
//                        qkjtakeRWine.setOrdertype(0);
//                        qkjtakeRWine.setMemberid(qkjvipOrderOrder.getMemberid());
//                        qkjtakeRWine.setWareproid(item.getWareproid());
//                        qkjtakeRWine.setMembername(qkjvipOrderOrder.getMembername());
//                        qkjtakeRWineService.updateTakeByWareproid(qkjtakeRWine);


                        //历史 (是否正式订单的时候再加入历史以免历史多余)
                        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
                        qkjvipOrderWareprohistory.setState(1);
                        qkjvipOrderWareprohistory.setNum(1);
                        qkjvipOrderWareprohistory.setProid(item.getProductid());
                        qkjvipOrderWareprohistory.setProname(item.getProductname());
                        qkjvipOrderWareprohistory.setWareid(item.getWarehouseid());
                        qkjvipOrderWareprohistory.setCreateon(new Date());
                        qkjvipOrderWareprohistory.setCreator(getUserId());
                        qkjvipOrderWareprohistory.setOrderid(qkjvipOrderOrder.getId());
                        if (qkjvipOrderOrder.getId() == null || qkjvipOrderOrder.getId().equals(""))
                            qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
                    }
                    return item;
                } else {
                    return null;
                }
            }).collect(Collectors.toList());
            qkjvipProductStockService.batchAdd(liststock);
        }
// 给会员发短信添加时
        if (qkjvipOrderOrder!=null && qkjvipOrderOrder.getCellphone()!=null&&!qkjvipOrderOrder.getCellphone().equals("")) {
// 短信钉钉通知
//            UserMsgEntity userMsgEntity = new UserMsgEntity();
//            userMsgEntity.setUrl("https://wxaurl.cn/K0vrkhelCbg");
//            userMsgEntity.setTitle("封坛订单通知");
//            String str = products.length()>0 ? products.substring(0,products.length()-1): "";
//            userMsgEntity.setMsg("尊贵的" + qkjvipOrderOrder.getMembername()+"您好：专属于您的"+str+"的酒证已经生成，请点击链接登录青稞荟小程序，查看酒证；");
//            userMsgEntity.setMobilelist(qkjvipOrderOrder.getCellphone());
//            sendmsg(userMsgEntity);
        }
    return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderOrder qkjvipOrderOrder
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjvip:orderorder:update")
    public RestResponse update(@RequestBody QkjvipOrderOrderEntity qkjvipOrderOrder) {

        qkjvipOrderOrderService.update(qkjvipOrderOrder);

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
        // qkjvipOrderOrderService.deleteBatch(ids);
        QkjvipOrderOrderEntity qkjvipOrderOrder;
        for (int i=0;i<ids.length;i++) {
            if(ids[i]!=null&&!ids[i].equals("")){
                qkjvipOrderOrder = new QkjvipOrderOrderEntity();
                qkjvipOrderOrder.setMorderid(ids[i]);
                qkjvipOrderOrder.setMemberid(getUserId());
                qkjvipOrderOrder.setMembername(getUser().getUserName());
                Object obj = JSONArray.toJSON(qkjvipOrderOrder);
                String JsonStr = JsonHelper.toJsonString(obj, "yyyy-MM-dd HH:mm:ss");
                String resultPost = null;
                try {
                    resultPost = HttpClient.sendPost(Vars.MEMBER_ORDER_ORDER_DELTET, JsonStr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                JSONObject resultObject = JSON.parseObject(resultPost);
                if ("200".equals(resultObject.get("resultcode").toString())) {
                    return RestResponse.success();
                }else {
                    return RestResponse.error(resultObject.get("descr").toString());
                }
                }
        }
        //
        return RestResponse.success();
    }
}
