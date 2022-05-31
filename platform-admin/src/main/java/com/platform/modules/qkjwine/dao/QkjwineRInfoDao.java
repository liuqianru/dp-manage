/*
 * 项目名称:platform-plus
 * 类名称:QkjwineRInfoDao.java
 * 包名称:com.platform.modules.qkjwine.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-16 09:16:11             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjwine.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hpsf.Decimal;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author 
 * @date 2022-02-16 09:16:11
 */
@Mapper
public interface QkjwineRInfoDao extends BaseMapper<QkjwineRInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjwineRInfoEntity> queryAll(@Param("params") Map<String, Object> params);
    List<QkjwineRInfoEntity> selectSqlSimple(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjwineRInfoEntity> selectQkjwineRInfoPage(IPage page, @Param("params") Map<String, Object> params);

    boolean updateWineInfo(QkjwineRInfoEntity qkjwineRInfoEntity);
    boolean updateTakeIdById(QkjwineRInfoEntity qkjwineRInfoEntity);
    boolean updateScenetimeByWareporid(QkjwineRInfoEntity qkjwineRInfoEntity);
    boolean updateFtnumByWareporid(QkjwineRInfoEntity qkjwineRInfoEntity);

    // 删除
    boolean mdyWineDisableById(QkjwineRInfoEntity qkjwineRInfoEntity);

    Double queryValue(@Param("params") Map<String, Object> params);

}
