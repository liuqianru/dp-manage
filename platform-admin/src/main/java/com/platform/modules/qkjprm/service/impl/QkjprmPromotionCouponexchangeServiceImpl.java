/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionCouponexchangeServiceImpl.java
 * 包名称:com.platform.modules.qkjprm.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service.impl;

import cn.emay.util.AES;
import cn.emay.util.JsonHelper;
import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONArray;
import com.aliyun.api.internal.parser.json.JsonConverter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjprm.dao.QkjprmPromotionCouponexchangeDao;
import com.platform.modules.qkjprm.domain.ScanCodeInfo;
import com.platform.modules.qkjprm.domain.ScanSendPackResult;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponexchangeEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionCouponreceiveEntity;
import com.platform.modules.qkjprm.entity.QkjprmPromotionMemberEntity;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponexchangeService;
import com.platform.modules.qkjprm.service.QkjprmPromotionCouponreceiveService;
import com.platform.modules.qkjprm.service.QkjprmPromotionMemberService;
import com.platform.modules.util.HttpClient;
import com.platform.modules.util.JSONUtil;
import com.platform.modules.util.RandomGUID;
import com.platform.modules.util.Vars;
import org.apache.cxf.message.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.emay.util.Md5.md5;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Service("qkjprmPromotionCouponexchangeService")
public class QkjprmPromotionCouponexchangeServiceImpl extends ServiceImpl<QkjprmPromotionCouponexchangeDao, QkjprmPromotionCouponexchangeEntity> implements QkjprmPromotionCouponexchangeService {
    @Autowired
    private QkjprmPromotionCouponService couponService;
    @Autowired
    private QkjprmPromotionCouponreceiveService receiveService;
    @Autowired
    private QkjprmPromotionMemberService memberService;

    /**
     * 获取扫码的用户
     *
     * @param bottlecode
     * @return
     */
    public List<String> getScanUnion(String bottlecode) {
        return baseMapper.getScanUnion(bottlecode);
    }

    /**
     * 获取云码扫码记录
     *
     * @param code
     * @return
     */
    public ScanCodeInfo getYMScanInfo(String code) {
        RandomGUID myGUID = new RandomGUID();
        String url = "http://api.zhongjiu.cn/datapool/scannerinforecord";
        HashMap<String, Object> param = new HashMap<>();
        HashMap<String, Object> subMap = new HashMap<>();
        param.put("Scancode", code);
        subMap.put("user", "");
        subMap.put("imei", "12312312312");
        subMap.put("version", 1);
        subMap.put("channel", "00000000");
        subMap.put("phonemodel", "hua wei");
        subMap.put("platform", 0);
        subMap.put("IsApp", 0);
        param.put("meta", subMap);
        String requestJson = JsonHelper.toJsonString(param);

        byte[] bytes = new byte[0];
        try {
            bytes = requestJson.getBytes(Vars.ENCODE);
            if (bytes.length % 8 != 0) {
                byte[] hellotemp = new byte[bytes.length + (16 - bytes.length % 16)];
                for (int i = 0; i < bytes.length; i++) {
                    hellotemp[i] = bytes[i];
                }
                bytes = hellotemp;
            }
            byte[] parambytes = AES.encrypt(bytes, Vars.AESKEY.getBytes(Vars.ENCODE), Vars.VECTOR.getBytes(Vars.ENCODE), Vars.ALGORITHM);
            //转base64位字符串
            String res = new BASE64Encoder().encode(parambytes);
            System.out.println("key:data" + " vlaue:" + res);
            String guid = myGUID.toString();
            String sign = md5((res + guid + Vars.SECRETKEY).getBytes(Vars.ENCODE));   //md5加密
            String urlParam = "?v=1" + "&sign=" + sign + "&key=" + guid;
            System.out.println("key:sign" + " vlaue:" + sign);
            System.out.println("key:key" + " vlaue:" + guid);
            String resultPost = HttpClient.sendPost(url + urlParam, res);
            ScanCodeInfo result = JsonHelper.fromJson(ScanCodeInfo.class, resultPost);
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 扫码兑换优惠券
     *
     * @param bottlecode
     * @param unionid
     * @param couponsn
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean exchangeScanCode(String bottlecode, String unionid, String couponsn) throws Exception {
        QkjprmPromotionCouponreceiveEntity receiveEntity = receiveService.getById(couponsn);
        if (receiveEntity == null) {
            throw new Exception("优惠券码不正确");
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        if (!fmt.format(receiveEntity.getCreatetime()).equals(fmt.format(new Date())))
            throw new Exception("优惠券已过期");
        if (!receiveEntity.getUnionid().equals(unionid))
            throw new Exception("扫码人员不一致");

        QkjprmPromotionMemberEntity memberEntity = memberService.getMemberByUnionid(unionid);
        if (memberEntity == null)
            throw new Exception("用户不存在");
        BigDecimal exchangeAmount = receiveEntity.getIsdouble() == 0 ? receiveEntity.getCouponamount() : receiveEntity.getCouponamount().multiply(new BigDecimal(2));
        //更新累计返现金额
        memberEntity.setExchangeamount(memberEntity.getExchangeamount().add(exchangeAmount));
        if (!memberService.update(memberEntity))
            throw new Exception("更新累计返现金额失败");
        QkjprmPromotionCouponexchangeEntity exchangeEntity = new QkjprmPromotionCouponexchangeEntity();
        exchangeEntity.setCouponreceiveid(couponsn);
        exchangeEntity.setCreatetime(new Date());
        exchangeEntity.setExchangeamount(exchangeAmount);
        exchangeEntity.setIsdouble(receiveEntity.getIsdouble());
        exchangeEntity.setMarketcode(bottlecode);
        exchangeEntity.setUserid(memberEntity.getId());
        exchangeEntity.setUserunionid(unionid);
        exchangeEntity.setExchangeresult(1);
        if (!this.save(exchangeEntity))
            throw new Exception("添加记录失败");

        //返现接口
        String url = "https://sy-webapi.ym.qkj.com.cn/Market/MarketCode/ScanToSendRedPack?tokenKey=AsYgZ94GFzdc73fm";
        Map<String, Object> postParams = new HashMap<>();
        postParams.put("code", bottlecode);
        postParams.put("unionId", unionid);
        postParams.put("openId", memberEntity.getOpenid());
        postParams.put("money", Convert.toInt(exchangeAmount.multiply(new BigDecimal(100))));

        String result = HttpClient.sendPost(url, JsonHelper.toJsonString(postParams));
        ScanSendPackResult model = (ScanSendPackResult) JSONUtil.toObject(result, ScanSendPackResult.class);
        if (!model.getRespCode().equals(0)) {
            throw new Exception("返现失败");
        }
        //更新
        exchangeEntity.setExchangeresult(1);
        exchangeEntity.setExchangesn("123");
        this.update(exchangeEntity);

        return true;
    }


    @Override
    public List<QkjprmPromotionCouponexchangeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjprmPromotionCouponexchangeEntity> page = new Query<QkjprmPromotionCouponexchangeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjprmPromotionCouponexchangePage(page, params));
    }

    @Override
    public boolean add(QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange) {
        return this.save(qkjprmPromotionCouponexchange);
    }

    @Override
    public boolean update(QkjprmPromotionCouponexchangeEntity qkjprmPromotionCouponexchange) {
        return this.updateById(qkjprmPromotionCouponexchange);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
