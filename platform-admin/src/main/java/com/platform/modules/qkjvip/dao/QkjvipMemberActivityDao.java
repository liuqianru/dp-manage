/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActivityDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-03 09:49:29        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityAccountEntity;
import com.platform.modules.qkjvip.entity.QkjvipMemberActivityEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2020-09-03 09:49:29
 */
@Mapper
public interface QkjvipMemberActivityDao extends BaseMapper<QkjvipMemberActivityEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivityEntity> queryAll(@Param("params") Map<String, Object> params);
    List<QkjvipMemberActivityEntity> queryAllReport(@Param("params") Map<String, Object> params);

    List<QkjvipMemberActivityEntity> queryAllSignAddress(IPage page, @Param("params") Map<String, Object> params);
    List<QkjvipMemberActivityEntity> queryAllSignAddress(@Param("params") Map<String, Object> params);

    List<QkjvipMemberActivityEntity> queryAllSignAddressmain(IPage page, @Param("params") Map<String, Object> params);
    List<QkjvipMemberActivityEntity> queryAllSignAddressmain(@Param("params") Map<String, Object> params);

    List<QkjvipMemberActivityEntity> actityisexist();


    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivityEntity> selectQkjvipMemberActivityPage(IPage page, @Param("params") Map<String, Object> params);
    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipMemberActivityEntity> selectQkjvipMemberActivityPageCount(IPage page, @Param("params") Map<String, Object> params);

    List<QkjvipMemberActivityAccountEntity> queryCount(@Param("params") Map<String, Object> params);
}
