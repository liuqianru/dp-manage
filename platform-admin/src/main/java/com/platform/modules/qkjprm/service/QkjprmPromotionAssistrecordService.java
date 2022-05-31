/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionAssistrecordService.java
 * 包名称:com.platform.modules.qkjprm.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjprm.domain.ReceiveParam;
import com.platform.modules.qkjprm.entity.QkjprmPromotionAssistrecordEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
public interface QkjprmPromotionAssistrecordService extends IService<QkjprmPromotionAssistrecordEntity> {

    /**
     * 助力优惠券翻倍
     *
     * @param assist   助力人信息
     * @param couponsn 助力优惠券编号
     * @return
     */
    boolean assistCoupon(ReceiveParam assist, String couponsn) throws Exception;

    /**
     * 助力积分榜得积分
     *
     * @param assist  助力人
     * @param unionid 被助力人unionid
     * @return
     */
    boolean assistIntegralRank(ReceiveParam assist, String unionid) throws Exception;

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionAssistrecordEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjprmPromotionAssistrecord
     * @return 新增结果
     */
    boolean add(QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord);

    /**
     * 根据主键更新
     *
     * @param qkjprmPromotionAssistrecord
     * @return 更新结果
     */
    boolean update(QkjprmPromotionAssistrecordEntity qkjprmPromotionAssistrecord);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);
}
