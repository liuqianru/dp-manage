/*
 * 项目名称:platform-plus
 * 类名称:QkjsmsRInfoDao.java
 * 包名称:com.platform.modules.qkjsms.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-03-15 14:31:17        sun     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjsms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjsms.entity.QkjsmsRInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author sun
 * @date 2022-03-15 14:31:17
 */
@Mapper
public interface QkjsmsRInfoDao extends BaseMapper<QkjsmsRInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjsmsRInfoEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjsmsRInfoEntity> selectQkjsmsRInfoPage(IPage page, @Param("params") Map<String, Object> params);
}
