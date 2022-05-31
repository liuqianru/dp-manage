/*
 * 项目名称:platform-plus
 * 类名称:QkjcusOrderController.java
 * 包名称:com.platform.modules.qkjcus.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-23 16:50:14             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjcus.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.datascope.ContextHelper;
import com.platform.modules.app.entity.UserEntity;
import com.platform.modules.qkjcus.entity.QkjcusOrderProEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderOutService;
import com.platform.modules.qkjcus.service.QkjcusOrderProService;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import com.platform.modules.qkjsms.service.QkjsmsRInfoService;
import com.platform.modules.qkjtake.entity.QkjtakeRWineEntity;
import com.platform.modules.qkjtake.service.QkjtakeRWineService;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareprohistoryEntity;
import com.platform.modules.qkjvip.service.QkjvipOrderWareproService;
import com.platform.modules.qkjvip.service.QkjvipOrderWareprohistoryService;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import com.platform.modules.qkjwine.service.QkjwineRInfoService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.qkjcus.entity.QkjcusOrderEntity;
import com.platform.modules.qkjcus.service.QkjcusOrderService;
import com.platform.modules.sys.entity.SysUserEntity;
import org.apache.catalina.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller
 *
 * @author 
 * @date 2021-12-23 16:50:14
 */
@RestController
@RequestMapping("qkjcus/order")
public class QkjcusOrderController extends AbstractController {
    @Autowired
    private QkjcusOrderService qkjcusOrderService;
    @Autowired
    private QkjcusOrderProService qkjcusOrderProService;
    @Autowired
    private QkjvipOrderWareproService qkjvipOrderWareproService;
    @Autowired
    private QkjvipOrderWareprohistoryService qkjvipOrderWareprohistoryService;
    @Autowired
    private QkjcusOrderOutService qkjcusOrderOutService;
    @Autowired
    private QkjwineRInfoService qkjwineRInfoService;
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
    @RequiresPermissions("qkjcus:order:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<QkjcusOrderEntity> list = qkjcusOrderService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    @RequiresPermissions("qkjcus:order:list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        String listorgno = ContextHelper.getPermitDepts("qkjcus:order:list");
        if(listorgno!=null&&!listorgno.equals("-1")){
            params.put("listorgno",listorgno);
            params.put("addUser",getUserId());
        }
        Page page = qkjcusOrderService.queryPage(params);

        List<QkjcusOrderEntity> orderList = page.getRecords();
        params.clear();
        params.put("state",0);
        List<QkjsmsRInfoEntity> sms = qkjsmsRInfoService.queryAll(params);
        Map<String, Object> promap = new HashMap<>();
        orderList.stream().forEach(item -> {
            List<QkjsmsRInfoEntity> smsnew = sms.stream().filter(hitem -> hitem.getOrderid()!=null&&hitem.getOrderid().equals(item.getId())).collect(Collectors.toList());
            item.setIssms(smsnew.size()>0 ? 1 : 0);
            // 产品列表
            promap.clear();
            promap.put("mainid",item.getId());
            item.setPros(qkjcusOrderProService.queryAll(promap));
        });
        page.setRecords(orderList);
        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("qkjcus:order:info")
    public RestResponse info(@PathVariable("id") String id) {
        QkjcusOrderEntity qkjcusOrder = qkjcusOrderService.getById(id);
        // 产品列表
        Map<String, Object> params = new HashMap<>();
        params.put("mainid",qkjcusOrder.getId());
        qkjcusOrder.setPros(qkjcusOrderProService.queryAll(params));

        // 查询酒证
        params.put("orderid",qkjcusOrder.getId());
        List<QkjwineRInfoEntity> wines = new ArrayList<>();
        wines=qkjwineRInfoService.queryAll(params);
        qkjcusOrder.setWines(wines);
        // 出库列表
        params.clear();
        params.put("instockid",qkjcusOrder.getId());
        qkjcusOrder.setOuts(qkjcusOrderOutService.queryAll(params));
        return RestResponse.success().put("order", qkjcusOrder);
    }

    /**
     * 新增
     *
     * @param qkjcusOrder qkjcusOrder
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse save(@RequestBody QkjcusOrderEntity qkjcusOrder) {
        qkjcusOrder.setAddUser(getUserId());
        qkjcusOrder.setAddTime(new Date());
        qkjcusOrder.setOrdercode(getCid());
        qkjcusOrderService.add(qkjcusOrder);

        if (qkjcusOrder.getPros().size()>0) {
            // 排除已存在的
            List<QkjcusOrderProEntity> pros = qkjcusOrder.getPros().stream().filter(a -> (a.getId()==null || a.getId().equals(""))).collect(Collectors.toList());
            pros.forEach(item -> {
                item.setMainid(qkjcusOrder.getId());
                if (item.getWareproid()==null || item.getWareproid().equals("")) { // 没有封坛
                    QkjvipOrderWareproEntity qkjvipOrderWarepro = new QkjvipOrderWareproEntity();
                    qkjvipOrderWarepro.setWareid(item.getWareid());
                    qkjvipOrderWarepro.setState(0);
                    qkjvipOrderWarepro.setProid(item.getProid());
                    qkjvipOrderWarepro.setProname(item.getProname());
                    qkjvipOrderWarepro.setCreator(getUserId());
                    qkjvipOrderWarepro.setCreateon(new Date());
                    qkjvipOrderWarepro.setUpdatetime(new Date());
                    qkjvipOrderWareproService.add(qkjvipOrderWarepro);
                    item.setWareproid(qkjvipOrderWarepro.getId());
                }
                QkjvipOrderWareproEntity wpentity = new QkjvipOrderWareproEntity();
                wpentity.setId(item.getWareproid());
                wpentity.setState(2); // 已赠送
                qkjvipOrderWareproService.update(wpentity);
                // 更新酒证信息 根据wareproid
                if (item.getWareproid()!=null){
                    QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
                    wine.setOrderid(qkjcusOrder.getId());
                    wine.setOrdertype(1);
                    wine.setMemberid(qkjcusOrder.getMemberId());
                    wine.setWareporid(item.getWareproid());
                    qkjwineRInfoService.updateWineInfo(wine);

                    //  更新提货单
//                    QkjtakeRWineEntity qkjtakeRWine = new QkjtakeRWineEntity();
//                    qkjtakeRWine.setOrderid(qkjcusOrder.getId());
//                    qkjtakeRWine.setOrdertype(0);
//                    qkjtakeRWine.setMemberid(qkjcusOrder.getMemberId());
//                    qkjtakeRWine.setWareproid(item.getWareproid());
//                    qkjtakeRWine.setMembername(qkjcusOrder.getMemberName());
//                    qkjtakeRWineService.updateTakeByWareproid(qkjtakeRWine);
                }
                qkjcusOrderProService.add(item);

                //历史
                QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
                qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(wpentity), QkjvipOrderWareprohistoryEntity.class);
                qkjvipOrderWareprohistory.setId(null);
                qkjvipOrderWareprohistory.setState(2);
                qkjvipOrderWareprohistory.setNum(1);
                qkjvipOrderWareprohistory.setProid(item.getProid());
                qkjvipOrderWareprohistory.setProname(item.getProname());
                qkjvipOrderWareprohistory.setWareid(item.getWareid());
                qkjvipOrderWareprohistory.setCreateon(new Date());
                qkjvipOrderWareprohistory.setCreator(getUserId());
                qkjvipOrderWareprohistory.setOrderid(qkjcusOrder.getId());
                qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);

            });
        }
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param qkjcusOrder qkjcusOrder
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    @RequiresPermissions("qkjcus:order:update")
    @Transactional(rollbackFor = Exception.class)
    public RestResponse update(@RequestBody QkjcusOrderEntity qkjcusOrder) {
        qkjcusOrderService.update(qkjcusOrder);
        if (qkjcusOrder.getPros().size()>0) {
            // 排除已存在的
            List<QkjcusOrderProEntity> pros = qkjcusOrder.getPros().stream().filter(a -> (a.getId()==null || a.getId().equals(""))).collect(Collectors.toList());
            pros.forEach(item -> {
                item.setMainid(qkjcusOrder.getId());
                if (item.getWareproid()==null || item.getWareproid().equals("")) { // 没有封坛 修改后不需要，订单只保定未封坛仓库
                    QkjvipOrderWareproEntity qkjvipOrderWarepro = new QkjvipOrderWareproEntity();
                    qkjvipOrderWarepro.setWareid(item.getWareid());
                    qkjvipOrderWarepro.setState(0);
                    qkjvipOrderWarepro.setProid(item.getProid());
                    qkjvipOrderWarepro.setProname(item.getProname());
                    qkjvipOrderWarepro.setCreator(getUserId());
                    qkjvipOrderWarepro.setCreateon(new Date());
                    qkjvipOrderWarepro.setUpdatetime(new Date());
                    qkjvipOrderWareproService.add(qkjvipOrderWarepro);
                    item.setWareproid(qkjvipOrderWarepro.getId());
                }
                QkjvipOrderWareproEntity wpentity = new QkjvipOrderWareproEntity();
                wpentity.setId(item.getWareproid());
                wpentity.setState(2); // 已赠送
                qkjvipOrderWareproService.update(wpentity);

                qkjcusOrderProService.add(item);

                // 更新酒证信息 根据wareproid
                if (item.getWareproid()!=null){
                    QkjwineRInfoEntity wine = new QkjwineRInfoEntity();
                    wine.setOrderid(qkjcusOrder.getId());
                    wine.setOrdertype(1);
                    wine.setMemberid(qkjcusOrder.getMemberId());
                    wine.setWareporid(item.getWareproid());
                    qkjwineRInfoService.updateWineInfo(wine);

                    //  更新提货单
//                    QkjtakeRWineEntity qkjtakeRWine = new QkjtakeRWineEntity();
//                    qkjtakeRWine.setOrderid(qkjcusOrder.getId());
//                    qkjtakeRWine.setOrdertype(0);
//                    qkjtakeRWine.setMemberid(qkjcusOrder.getMemberId());
//                    qkjtakeRWine.setWareproid(item.getWareproid());
//                    qkjtakeRWine.setMembername(qkjcusOrder.getMemberName());
//                    qkjtakeRWineService.updateTakeByWareproid(qkjtakeRWine);
                }

                //历史
                QkjvipOrderWareprohistoryEntity qkjvipOrderWareprohistory = new QkjvipOrderWareprohistoryEntity();
                qkjvipOrderWareprohistory = JSON.parseObject(JSON.toJSONString(wpentity), QkjvipOrderWareprohistoryEntity.class);
                qkjvipOrderWareprohistory.setId(null);
                qkjvipOrderWareprohistory.setState(2);
                qkjvipOrderWareprohistory.setNum(1);
                qkjvipOrderWareprohistory.setProid(item.getProid());
                qkjvipOrderWareprohistory.setProname(item.getProname());
                qkjvipOrderWareprohistory.setWareid(item.getWareid());
                qkjvipOrderWareprohistory.setCreateon(new Date());
                qkjvipOrderWareprohistory.setCreator(getUserId());
                qkjvipOrderWareprohistory.setOrderid(qkjcusOrder.getId());
                qkjvipOrderWareprohistoryService.add(qkjvipOrderWareprohistory);

            });
        }
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
    @RequiresPermissions("qkjcus:order:delete")
    public RestResponse delete(@RequestBody String[] ids) {
        qkjcusOrderService.deleteBatch(ids);

        return RestResponse.success();
    }

    public String getCid() {
        //获取当前时间
        String dateString = getDate(new Date(), "yyyyMMddHHmmss");
        int b = (int)(Math.random()*9000)+1000;
        String a = String.format("%04d", b);
        SysUserEntity userid= new SysUserEntity();
        userid = getUser();
        String cid = getUser().getOaId() + dateString + a;
        //注意点,你可以将b这个值设置到缓存当中或者数据库当中,然后每次那这个值就可以
        return cid;
    }

    public static String getDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdFormat = new SimpleDateFormat(format);

        return sdFormat.format(date);
    }


}
