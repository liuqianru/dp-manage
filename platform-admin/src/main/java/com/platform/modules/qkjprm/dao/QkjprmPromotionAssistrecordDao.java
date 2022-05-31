/*
 * 项目名称:platform-plus
 * 类名称:QkjprmPromotionAssistrecordDao.java
 * 包名称:com.platform.modules.qkjprm.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-09 10:46:48        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjprm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjprm.entity.QkjprmPromotionAssistrecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author hanjie
 * @date 2021-12-09 10:46:48
 */
@Mapper
public interface QkjprmPromotionAssistrecordDao extends BaseMapper<QkjprmPromotionAssistrecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionAssistrecordEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjprmPromotionAssistrecordEntity> selectQkjprmPromotionAssistrecordPage(IPage page, @Param("params") Map<String, Object> params);
}
