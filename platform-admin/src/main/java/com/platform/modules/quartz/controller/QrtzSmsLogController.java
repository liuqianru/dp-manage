/*
 * 项目名称:platform-plus
 * 类名称:QrtzSmsLogController.java
 * 包名称:com.platform.modules.quartz.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/6/1 15:48            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.controller;

import cn.emay.Example;
import cn.emay.ResultModel;
import cn.emay.eucp.inter.http.v1.dto.response.ReportResponse;
import cn.emay.util.DateUtil;
import cn.emay.util.JsonHelper;
import com.platform.common.annotation.SysLog;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.Constant;
import com.platform.common.utils.DateUtils;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.entity.SmsConfig;
import com.platform.modules.sys.entity.SysSmsLogEntity;
import com.platform.modules.sys.service.SysConfigService;
import com.platform.modules.sys.service.SysSmsLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * QrtzSmsLogController
 *
 * @author liuqianru
 * @date 2021/6/1 15:48
 */
@RestController
@Component("qrtzSmsLog")
@Slf4j
public class QrtzSmsLogController {
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private SysSmsLogService sysSmsLogService;

    /**
     * 定时获取短信发送状态报告
     *
     */
    @SysLog("获取短信发送状态报告")
    @RequestMapping("/getSmsReport")
    public void getSmsReport() {
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
        //青稞酒
        // appId
        String appId = config.getAppid();// 请联系销售，或者在页面中 获取
        // 密钥
        String secretKey = config.getSecretkey();// 请联系销售，或者在页面中 获取
        // 接口地址
        String host = config.getHost();// 请联系销售获取
        // 编码
        String encode = "UTF-8";
        // 加密算法
        String algorithm = config.getAlgorithm();
        // 是否压缩
        boolean isGizp = true;

        long startTime, endTime;
        Date nowDate = new Date();
        startTime = System.currentTimeMillis();
        ResultModel resultModel = Example.getReport(appId, secretKey, host, algorithm, isGizp, encode);  // 取3天前的数据，每次最多取500条
        if ("SUCCESS".equals(resultModel.getCode())) {
            System.out.println("短信状态报告：" + resultModel.getResult());
            ReportResponse[] response = JsonHelper.fromJson(ReportResponse[].class, resultModel.getResult());
            if (response != null && response.length > 0) {  // 有报告返回
                Map params = new HashMap();
                Date startDate = DateUtils.addDateDays(nowDate, -4);  // 4天前的数据
                params.put("startTime", DateUtil.toString(startDate, "yyyy-MM-dd HH:mm:ss"));
                params.put("endTime", DateUtil.toString(nowDate, "yyyy-MM-dd HH:mm:ss"));
                List<SysSmsLogEntity> smsList = sysSmsLogService.queryAll(params);
                SysSmsLogEntity smsLoget;
                for (ReportResponse d : response) {
                    for (SysSmsLogEntity smsLogEntity : smsList) {
                        if (smsLogEntity.getId()!=null && !smsLogEntity.getId().equals("") &&smsLogEntity.getSendId() != null && d.getSmsId().equals(smsLogEntity.getSendId()) && d.getMobile().equals(smsLogEntity.getMobile())) {
                            if (!"DELIVRD".equals(d.getState())) {  // 发送不成功
                                smsLoget = new SysSmsLogEntity();
                                smsLoget.setId(smsLogEntity.getId());
                                smsLoget.setSendStatus(1);
                                smsLoget.setReturnMsg(d.getState());
                                sysSmsLogService.update(smsLoget);  // 更新表发送状态
                            }
                        }
                    }
                }
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("获取短信状态报告完成，用时" + (endTime - startTime) + "ms");
    }
}
