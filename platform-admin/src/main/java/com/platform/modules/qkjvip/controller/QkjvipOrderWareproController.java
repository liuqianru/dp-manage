/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareproController.java
 * 包名称:com.platform.modules.qkjvip.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.controller;

import cn.emay.util.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.accesstoken.entity.AccesstokenEntity;
import com.platform.modules.qkjInterface.entity.UserMsgEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderProService;
import com.platform.modules.qkjcus.service.QkjcusOrderService;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.entity.QkjvipProductStockEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderOrderdetailService;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
import com.platform.modules.qkjvip.service.QkjvipProductStockService;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareproService;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysSmsLogService;
import com.platform.modules.util.CRCUtils;
import com.platform.modules.util.DingMsg;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@RestController
@RequestMapping("qkjvip/orderwarepro")
public class QkjvipOrderWareproController extends AbstractController {
    @Autowired
    private QkjvipOrderWareproService qkjvipOrderWareproService;
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;
    @Autowired
    private QkjcusOrderProService qkjcusOrderProService;
    @Autowired
    private QkjvipOrderOrderdetailService qkjvipOrderOrderdetailService;
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;
    @Autowired
    private QkjvipProductStockService qkjvipProductStockService;
    @Autowired
    private SysSmsLogService sysSmsLogService;
    @Autowired
    private QkjsmsRInfoService qkjsmsRInfoService;
    @Autowired
    private QkjtakeRWineService qkjtakeRWineService;


    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjvipOrderWareproEntity> list = qkjvipOrderWareproService.queryAll(params);
        Map<String, Object> selmap = new HashMap<>();
        if (list.size()>0) {
            list.stream().map(item->{
                if (item.getState() == 2) {// 已赠送
                    selmap.clear();
                    selmap.put("wareproid", item.getId());
                    item.setCusorders(qkjcusOrderProService.queryAll(selmap));
                    // 查是否有提货申请
//                    if (item.getCusorders()!=null&&item.getCusorders().size()>0) {
//                        // 查是否有提货申请
//                        item.setTakes(qkjtakeRWineService.queryAll(selmap));
//                    }
                } else if (item.getState() == 1) { // 已认购
                    selmap.clear();
                    selmap.put("productid", item.getProid());
                    selmap.put("warehouseid", item.getWareid());
                    selmap.put("ordertype", 5);
                    selmap.put("noorderstatus", 60);
                    item.setOrders(qkjvipOrderOrderdetailService.queryAll(selmap));
//                    if (item.getOrders()!=null&&item.getOrders().size()>0) {
//                        // 查是否有提货申请
//                        selmap.put("orderid",item.getOrders().get(0).getId());
//                        item.setTakes(qkjtakeRWineService.queryAll(selmap));
//                    }

                }

                return item;
            }).collect(Collectors.toList());
        }
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
        Page page = qkjvipOrderWareproService.queryPageListAll(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjvip:orderwarepro:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjvipOrderWareproEntity qkjvipOrderWarepro = qkjvipOrderWareproService.getById(id);

        return RestResponse.success().put("orderwarepro", qkjvipOrderWarepro);
    }

    /**
     * 新增
     *
     * @param qkjvipOrderWarepro qkjvipOrderWarepro
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        // 查询此仓库是否存在
        Map<String, Object> params= new HashMap<>();
        params.put("wareid",qkjvipOrderWarepro.getWareid());
        List<QkjvipOrderWareproEntity> list = qkjvipOrderWareproService.queryAll(params);
        if (list.size()>0) { // 此仓库已有产品
            return RestResponse.error("此仓库已被占用请选择其它仓库");
        } else {
            qkjvipOrderWarepro.setCreator(getUserId());
            qkjvipOrderWarepro.setCreateon(new Date());
            qkjvipOrderWarepro.setUpdatetime(new Date());
            qkjvipOrderWareproService.add(qkjvipOrderWarepro);
            // 生成酒证: id + 日期
            String wareid = qkjvipOrderWarepro.getId() + DateUtil.toString(new Date(), "yyyyMMdd");
            String wareshastr = wareid + CRCUtils.FindCRC(wareid.getBytes());
            String winesha = new Sha256Hash(wareshastr).toHex();
            QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
            wine.setNumber(wareid);
            wine.setWareporid(qkjvipOrderWarepro.getId());
            wine.setWinenum(winesha);
            if(qkjvipOrderWarepro.getFtnumber()!=null)wine.setFtnumber(qkjvipOrderWarepro.getFtnumber());
            if(qkjvipOrderWarepro.getScenetime()!=null)wine.setScenetime(qkjvipOrderWarepro.getScenetime());
            qkjwineRInfoService.save(wine);

            // 生成提货单据
//            QkjtakeRWineEntity qkjtakeRWine = new QkjtakeRWineEntity();
//            qkjtakeRWine.setWareproid(wine.getWareporid());
//            qkjtakeRWine.setWineid(wine.getId());
//            qkjtakeRWine.setState(-20);
//            qkjtakeRWineService.add(qkjtakeRWine);
//
//            // 修改酒证的提货id
//            wine.setTakeid(qkjtakeRWine.getId());
//            qkjwineRInfoService.updateTakeIdById(wine);
            //历史
            QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
            qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjvipOrderWarepro), QkjvipOrderWareprohistoryEntity.class);
            qkjvipOrderWareprohistory.setCreateon(new Date());
            qkjvipOrderWareprohistory.setCreator(getUserId());
            qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
            qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
        }
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderWarepro qkjvipOrderWarepro
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        qkjvipOrderWareproService.update(qkjvipOrderWarepro);
        if (qkjvipOrderWarepro.getState()!=null&&qkjvipOrderWarepro.getState()==3) { // 借出
            //历史
            QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
            qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjvipOrderWarepro), QkjvipOrderWareprohistoryEntity.class);
            qkjvipOrderWareprohistory.setId(null);
            qkjvipOrderWareprohistory.setCreateon(new Date());
            qkjvipOrderWareprohistory.setCreator(getUserId());
            qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
            qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
        }
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderWarepro qkjvipOrderWarepro
     * @return RestResponse
     */
    @SysLog("借出")
    @RequestMapping("/updateState")
    public RestResponse updateState(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        qkjvipOrderWarepro.setState(3);
        qkjvipOrderWareproService.update(qkjvipOrderWarepro);
        if (qkjvipOrderWarepro.getState()!=null&&qkjvipOrderWarepro.getState()==3) { // 借出
            //历史
            QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
            qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjvipOrderWarepro), QkjvipOrderWareprohistoryEntity.class);
            qkjvipOrderWareprohistory.setId(null);
            qkjvipOrderWareprohistory.setCreateon(new Date());
            qkjvipOrderWareprohistory.setCreator(getUserId());
            qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
            qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
        }
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjvipOrderWarepro qkjvipOrderWarepro
     * @return RestResponse
     */
    @SysLog("归还")
    @RequestMapping("/updateStateOther")
    public RestResponse updateStateOther(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        qkjvipOrderWarepro.setState(0);
        qkjvipOrderWareproService.update(qkjvipOrderWarepro);
        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
        qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjvipOrderWarepro), QkjvipOrderWareprohistoryEntity.class);
        qkjvipOrderWareprohistory.setId(null);
        qkjvipOrderWareprohistory.setState(5); // 归还
        qkjvipOrderWareprohistory.setCreateon(new Date());
        qkjvipOrderWareprohistory.setCreator(getUserId());
        qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);
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
    @RequiresPermissions("qkjvip:orderwarehouse:list")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse delete(@RequestBody String[] ids) {
        QkjvipOrderWareproEntity qkjvipOrderWarepro = qkjvipOrderWareproService.getById(ids[0]);
        //历史
        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
        qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(qkjvipOrderWarepro), QkjvipOrderWareprohistoryEntity.class);
        qkjvipOrderWareprohistory.setState(-1); // 删除
        qkjvipOrderWareprohistory.setId(null);
        qkjvipOrderWareprohistory.setCreateon(new Date());
        qkjvipOrderWareprohistory.setCreator(getUserId());
        qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);

        qkjvipOrderWareproService.deleteBatch(ids);

        // 删除酒证（由于外键所有不用特别删除）

        return RestResponse.success();
    }

    /**
     * 转移
     *
     * @param
     * @return RestResponse
     */
    @SysLog("发送短信")
    @RequestMapping("/sendmsg")
    @RequiresPermissions("qkjvip:orderwarehouse:save")
    public RestResponse sendmsg(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        // 查询可以发送的手机号（酒证）
        if (qkjvipOrderWarepro!=null) {
            Set<String> orders = new HashSet<>();
            qkjvipOrderWarepro.getMainorders().stream().forEach(item -> {
                if (item.getOrderid()!=null)orders.add(item.getOrderid());
            });
            qkjvipOrderWarepro.getCusmainorders().stream().forEach(item -> {
                orders.add(item.getId());
            });
            Map<String, Object> params= new HashMap<>();
            params.put("orders",orders);
            List<QkjwineRInfoEntity> list = new ArrayList<>();
            if (orders!=null&&orders.size()>0) list =qkjwineRInfoService.selectSqlSimple(params);
            StringBuffer mobiles = new StringBuffer();
            List<QkjsmsRInfoEntity> ris = new ArrayList<>();
            list.forEach(item -> {
                QkjsmsRInfoEntity qkjsmsRInfo = new QkjsmsRInfoEntity();
                qkjvipOrderWarepro.getMainorders().forEach(oitem -> {
                    if (item.getOrderid().equals(oitem.getOrderid())) {
                        mobiles.append(oitem.getCellphone()+",");
                        qkjsmsRInfo.setWineid(item.getId());
                    }
                });
                qkjvipOrderWarepro.getCusmainorders().forEach(oitem -> {
                    if (item.getOrderid().equals(oitem.getId())) {
                        mobiles.append(oitem.getMobile()+",");
                        qkjsmsRInfo.setWineid(item.getId());
                    }
                });
                ris.add(qkjsmsRInfo);
            });
            UserMsgEntity userMsgEntity = new UserMsgEntity();
            // userMsgEntity.setUrl("https://wxaurl.cn/K0vrkhelCbg");
            userMsgEntity.setTitle("封坛订单通知");
            userMsgEntity.setMsg(qkjvipOrderWarepro.getRemark());
            userMsgEntity.setMobilelist(mobiles.length()>0 ? mobiles.substring(0,mobiles.length()-1): "");
            sendmsgy(userMsgEntity,ris);
        }

        return RestResponse.success();
    }

    public void sendmsgy(UserMsgEntity userMsgEntity,List<QkjsmsRInfoEntity> ris) {
        if (userMsgEntity!=null && userMsgEntity.getMobilelist() != null && !userMsgEntity.getMobilelist().equals("")) {
            // 发短信
            SysSmsLogEntity smsLog = new SysSmsLogEntity();
            smsLog.setContent(userMsgEntity.getMsg() + userMsgEntity.getUrl());
            smsLog.setMobile(userMsgEntity.getMobilelist());
            SysSmsLogEntity sysSmsLogEntity = sysSmsLogService.sendSmsBach(smsLog);
            if (sysSmsLogEntity.getSendStatus() == 0) {
                // 历史记录添加
                qkjsmsRInfoService.saveBatch(ris);
            }
        }
    }
    /**
     * 转移
     *
     * @param
     * @return RestResponse
     */
    @SysLog("转移")
    @RequestMapping("/shiftware")
    @RequiresPermissions("qkjvip:orderwarehouse:save")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse shiftware(@RequestBody QkjvipOrderWareproEntity qkjvipOrderWarepro) {
        // 仓库产品转移warepro直接修改wareid
        qkjvipOrderWareproService.updateShiftWare(qkjvipOrderWarepro);
        // 订单仓库分配转移
        if (qkjvipOrderWarepro!=null&&qkjvipOrderWarepro.getOrdertype()!=null&&!qkjvipOrderWarepro.getOrdertype().equals("")) {
            if (qkjvipOrderWarepro.getOrdertype().equals("0")) { // 销售订单
                QkjvipProductStockEntity stock= new QkjvipProductStockEntity();
                stock.setOrderid(qkjvipOrderWarepro.getOrderid());
                stock.setProductid(qkjvipOrderWarepro.getProid());
                stock.setWarehouseid(qkjvipOrderWarepro.getNewwareid());
                qkjvipProductStockService.updateShiftOrderStock(stock);
            }
        }
        // 历史记录添加
        QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
        qkjvipOrderWareprohistory.setState(10);
        qkjvipOrderWareprohistory.setNum(1);
        qkjvipOrderWareprohistory.setProid(qkjvipOrderWarepro.getProid());
        qkjvipOrderWareprohistory.setProname(qkjvipOrderWarepro.getProname());
        qkjvipOrderWareprohistory.setWareid(qkjvipOrderWarepro.getOldwareid());
        qkjvipOrderWareprohistory.setRemark(qkjvipOrderWarepro.getNewwareid());
        qkjvipOrderWareprohistory.setCreator(getUserId());
        qkjvipOrderWareprohistory.setOrderid(qkjvipOrderWarepro.getId());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory); // 添加移出
        qkjvipOrderWareprohistory.setId(null);
        qkjvipOrderWareprohistory.setState(11);
        qkjvipOrderWareprohistory.setWareid(qkjvipOrderWarepro.getNewwareid());
        qkjvipOrderWareprohistory.setRemark(qkjvipOrderWarepro.getOldwareid());
        qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);// 添加移入
        return RestResponse.success();
    }
}
