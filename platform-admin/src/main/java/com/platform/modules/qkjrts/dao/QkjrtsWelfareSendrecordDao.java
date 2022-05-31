/*
 * 项目名称:platform-plus
 * 类名称:QkjrtsWelfareSendrecordDao.java
 * 包名称:com.platform.modules.qkjrts.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-25 09:09:27        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrts.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjrts.entity.QkjrtsWelfareSendrecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2022-03-25 09:09:27
 */
@Mapper
public interface QkjrtsWelfareSendrecordDao extends BaseMapper<QkjrtsWelfareSendrecordEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareSendrecordEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjrtsWelfareSendrecordEntity> selectQkjrtsWelfareSendrecordPage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjrtsWelfareSendrecordEntity> getWelfareList(@Param("params") Map<String, Object> params);

    boolean setWelfareStatus(List<String> idList);
}
