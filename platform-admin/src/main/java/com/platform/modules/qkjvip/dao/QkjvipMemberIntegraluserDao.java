/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberIntegraluserDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-21 15:18:24        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberIntegraluserEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberMessageUserQueryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2020-12-21 15:18:24
 */
@Mapper
public interface QkjvipMemberIntegraluserDao extends BaseMapper<QkjvipMemberIntegraluserEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberIntegraluserEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberIntegraluserEntity> selectQkjvipMemberIntegraluserPage(IPage page, @Param("params") Map<String, Object> params);

    void deleteByIntegralId(String integralId);

    void deleteBatchByIntegralId(String[] integralIds);

    List<QkjvipMemberMessageUserQueryEntity> queryByIntegralId(String integralId);

    List<String> queryByIntegralId2(String integralId);
}
