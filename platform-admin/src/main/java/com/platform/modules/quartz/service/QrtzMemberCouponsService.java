/*
 * 项目名称:platform-plus
 * 类名称:MemberBasicService.java
 * 包名称:com.platform.modules.qkjvip.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/7 15:48            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.quartz.entity.QrtzMemberBasicEntity;
import com.platform.modules.quartz.entity.QrtzMemberCouponsEntity;

import java.util.List;
import java.util.Map;

/**
 * MemberBasicService
 *
 * @author liuqianru
 * @date 2020/8/7 15:48
 */
public interface QrtzMemberCouponsService extends IService<QrtzMemberCouponsEntity> {

    /**
     * 查询所有
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzMemberCouponsEntity> queryAll(Map<String, Object> params);

    /**
     * 查询所有
     *
     * @return List
     */
    List<QrtzMemberCouponsEntity> queryList();

    /**
     * 插入优惠券
     *
     * @param mbList 用户
     */
    void addBatch(List<QrtzMemberCouponsEntity> mbList);

    /**
     * 批量修改优惠券
     *
     * @param mbList mbList
     */
    void updateBatch(List<QrtzMemberCouponsEntity> mbList);
}
