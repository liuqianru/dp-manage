/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberScancodeNewDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-30 09:35:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberScancodeNewEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author hanjie
 * @date 2022-03-30 09:35:18
 */
@Mapper
public interface QkjvipMemberScancodeNewDao extends BaseMapper<QkjvipMemberScancodeNewEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberScancodeNewEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberScancodeNewEntity> selectQkjvipMemberScancodeNewPage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 获取个人扫码城市数量
     *
     * @param unionId
     * @return
     */
    int selectScanCityCount(String unionId);

    /**
     * 获取近一年用户的扫码记录 --可分页
     * @param params
     * @return
     */
    List<QkjvipMemberScancodeNewEntity> getScanByYear(@Param("params") Map<String,Object> params);

    /**
     * 获取用户近一年的扫码数量
     * @param unionid
     * @return
     */
    int getScanCount(String unionid);
}
