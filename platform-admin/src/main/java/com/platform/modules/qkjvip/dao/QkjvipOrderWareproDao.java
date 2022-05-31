/*
 * 项目名称:platform-plus
 * 类名称:QkjvipOrderWareproDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-12-22 13:59:47        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipOrderWareproEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 孙珊珊
 * @date 2021-12-22 13:59:47
 */
@Mapper
public interface QkjvipOrderWareproDao extends BaseMapper<QkjvipOrderWareproEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipOrderWareproEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipOrderWareproEntity> selectQkjvipOrderWareproPage(IPage page, @Param("params") Map<String, Object> params);

    List<QkjvipOrderWareproEntity> selectQkjvipOrderWareproListPage(IPage page, @Param("params") Map<String, Object> params);

    boolean updateStateByProAndWare(QkjvipOrderWareproEntity qkjvipMemberRightsEntity);

    boolean updateDisabledByProAndWare(QkjvipOrderWareproEntity qkjvipMemberRightsEntity);
    boolean updateShiftWare(QkjvipOrderWareproEntity qkjvipMemberRightsEntity);
}
