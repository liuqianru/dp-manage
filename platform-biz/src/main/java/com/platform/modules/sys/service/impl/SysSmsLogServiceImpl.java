/*
 * 项目名称:platform-plus
 * 类名称:SysSmsLogServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2019-02-12 09:51:15        李鹏军     初版做成
 *
 * Copyright (c) 2018-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import cn.emay.Example;
import cn.emay.ResultModel;
import cn.emay.eucp.inter.http.v1.dto.response.SmsResponse;
import cn.emay.util.JsonHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.*;
import com.platform.modules.sys.dao.SysSmsLogDao;
import com.platform.modules.sys.entity.SmsConfig;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysSmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 短信发送日志Service实现类
 *
 * @author 李鹏军
 * @date 2019-02-12 09:51:15
 */
@Service("sysSmsLogService")
public class SysSmsLogServiceImpl extends ServiceImpl<SysSmsLogDao, SysSmsLogEntity> implements SysSmsLogService {

    @Autowired
    private SysConfigService sysConfigService;

    @Override
    public List<SysSmsLogEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.STIME");
        params.put("asc", false);
        Page<SysSmsLogEntity> page = new Query<SysSmsLogEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysSmsLogPage(page, params));
    }

    @Override
    public void add(SysSmsLogEntity sysSmsLog) {
        this.save(sysSmsLog);
    }

    @Override
    public void update(SysSmsLogEntity sysSmsLog) {
        this.update(sysSmsLog, new QueryWrapper<>());
    }

    @Override
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        this.removeByIds(Arrays.asList(ids));
    }

    @Override
    public SysSmsLogEntity sendSms(SysSmsLogEntity smsLog) {
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            throw new BusinessException("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getHost())) {
            throw new BusinessException("请先配置短信平台网址");
        }
        if (StringUtils.isNullOrEmpty(config.getSecretkey())) {
            throw new BusinessException("请先配置短信平台密钥");
        }
        if (StringUtils.isNullOrEmpty(config.getAppid())) {
            throw new BusinessException("请先配置短信平台appid");
        }
        /**
         * 创锐
         */
        if (config.getType() == 0) {
//            try {
//                *//**
//                 * 状态,发送编号,无效号码数,成功提交数,黑名单数和消息，无论发送的号码是多少，一个发送请求只返回一个sendid，如果响应的状态不是“0”，则只有状态和消息
//                 *//*
//                result = SmsUtil.crSendSms(config.getName(), config.getPwd(), smsLog.getMobile(), smsLog.getContent(), config.getSign(),
//                        DateUtils.format(smsLog.getStime(), "yyyy-MM-dd HH:mm:ss"), smsLog.getExtno());
//            } catch (Exception e) {
//
//            }
//            String[] arr = result.split(",");
//            //发送成功
//            if (Constant.STR_ZERO.equals(arr[0])) {
//                smsLog.setSendId(arr[1]);
//                smsLog.setInvalidNum(Integer.parseInt(arr[2]));
//                smsLog.setSuccessNum(Integer.parseInt(arr[3]));
//                smsLog.setBlackNum(Integer.parseInt(arr[4]));
//                smsLog.setReturnMsg(arr[5]);
//            } else {
//                //发送失败
//                smsLog.setReturnMsg(arr[1]);
//            }
//            smsLog.setSendStatus(Integer.parseInt(arr[0]));
//            try {
//                smsLog.setUserId(ShiroUtils.getUserId());
//            } catch (Exception e) {
//                //外部发送短信
//                smsLog.setUserId("1");
//            }
//            smsLog.setSign(config.getSign());
//            if (null == smsLog.getStime()) {
//                smsLog.setStime(new Date());
//            }
        } else {
            //青稞酒
            // appId
            String appId = config.getAppid();// 请联系销售，或者在页面中 获取
            // 密钥
            String secretKey = config.getSecretkey();// 请联系销售，或者在页面中 获取
            // 接口地址
            String host = config.getHost();// 请联系销售获取
            // 加密算法
            String algorithm = config.getAlgorithm();
            // 编码
            String encode = "UTF-8";
            // 是否压缩
            boolean isGizp = true;

            String content=smsLog.getContent();
            String mobile=smsLog.getMobile();
            ResultModel resultModel = Example.setSingleSms(appId, secretKey, host,algorithm,content, null, null, mobile, isGizp, encode);

            if("SUCCESS".equals(resultModel.getCode())){  // 发送成功
                SmsResponse response = JsonHelper.fromJson(SmsResponse.class, resultModel.getResult());
                smsLog.setSendId(response.getSmsId());
                smsLog.setSendStatus(0);
                smsLog.setReturnMsg("成功");
            } else {  //发送失败
                smsLog.setSendStatus(1);
                smsLog.setReturnMsg(resultModel.getCode());
            }
            smsLog.setType(SmsUtil.TYPE);
            smsLog.setStime(new Date());
            if (ShiroUtils.isLogin()) {
                smsLog.setUserId(ShiroUtils.getUserId());
            } else {
                smsLog.setUserId("6"); //通过接口调用短信失败
            }
        }
        //保存发送记录
        save(smsLog);
        return smsLog;
    }

    @Override
    public SysSmsLogEntity sendSmsBach(SysSmsLogEntity smsLog) {
        //获取云存储配置信息
        SmsConfig config = sysConfigService.getConfigObject(Constant.SMS_CONFIG_KEY, SmsConfig.class);
        if (StringUtils.isNullOrEmpty(config)) {
            throw new BusinessException("请先配置短信平台信息");
        }
        if (StringUtils.isNullOrEmpty(config.getHost())) {
            throw new BusinessException("请先配置短信平台网址");
        }
        if (StringUtils.isNullOrEmpty(config.getSecretkey())) {
            throw new BusinessException("请先配置短信平台密钥");
        }
        if (StringUtils.isNullOrEmpty(config.getAppid())) {
            throw new BusinessException("请先配置短信平台appid");
        }
        /**
         * 创锐
         */
        if (config.getType() == 0) {
        } else {
            //青稞酒
            // appId
            String appId = config.getAppid();// 请联系销售，或者在页面中 获取
            // 密钥
            String secretKey = config.getSecretkey();// 请联系销售，或者在页面中 获取
            // 接口地址
            String host = config.getHost();// 请联系销售获取
            // 加密算法
            String algorithm = config.getAlgorithm();
            // 编码
            String encode = "UTF-8";
            // 是否压缩
            boolean isGizp = true;
            String content = smsLog.getContent();
            String[] mobiles = smsLog.getMobile().split(",");
            ResultModel resultModel = Example.setBatchOnlySms(appId, secretKey, host, algorithm, content, null, mobiles, isGizp, encode);
            List<SysSmsLogEntity> smsLogList = new ArrayList<>();
            if("SUCCESS".equals(resultModel.getCode())){  // 发送成功
                smsLog.setSendStatus(0);
                SmsResponse[] response = JsonHelper.fromJson(SmsResponse[].class, resultModel.getResult());
                System.out.println("=============begin sendSmsBach==================");
                for (SmsResponse d : response) {
                    System.out.println("data:" + d.getMobile() + "," + d.getSmsId() + "," + d.getCustomSmsId());
                    SysSmsLogEntity smsLogEntity = new SysSmsLogEntity();
                    smsLogEntity.setSendStatus(0);
                    smsLogEntity.setSendId(d.getSmsId());
                    smsLogEntity.setMobile(d.getMobile());
                    smsLogEntity.setReturnMsg("成功");
                    smsLogEntity.setType(SmsUtil.TYPE);
                    smsLogEntity.setStime(new Date());
                    smsLogEntity.setContent(content);
                    if (ShiroUtils.isLogin()) {
                        smsLogEntity.setUserId(ShiroUtils.getUserId());
                    } else {
                        smsLogEntity.setUserId("6"); //通过接口调用短信失败
                    }
                    smsLogList.add(smsLogEntity);
                }
                System.out.println("=============end sendSmsBach==================");
                saveBatch(smsLogList, 1000);
            } else {
                smsLog.setType(SmsUtil.TYPE);
                smsLog.setStime(new Date());
                if (ShiroUtils.isLogin()) {
                    smsLog.setUserId(ShiroUtils.getUserId());
                } else {
                    smsLog.setUserId("6"); //通过接口调用短信失败
                }

                smsLog.setReturnMsg(resultModel.getCode());
                smsLog.setSendStatus(1);
                //保存发送记录
                save(smsLog);
            }
        }
        return smsLog;
    }
}
