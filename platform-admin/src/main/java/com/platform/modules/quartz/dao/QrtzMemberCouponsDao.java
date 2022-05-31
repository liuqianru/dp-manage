/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberCouponsDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-03-17 11:37:32        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.quartz.entity.QrtzMemberCouponsEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-03-17 11:37:32
 */
@Mapper
public interface QrtzMemberCouponsDao extends BaseMapper<QrtzMemberCouponsEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QrtzMemberCouponsEntity> queryAll(@Param("params") Map<String, Object> params);

    List<QrtzMemberCouponsEntity> queryList();
}
