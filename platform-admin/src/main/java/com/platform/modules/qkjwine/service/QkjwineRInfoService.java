/*
 * 项目名称:platform-plus
 * 类名称:QkjwineRInfoService.java
 * 包名称:com.platform.modules.qkjwine.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2022-02-16 09:16:11             初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjwine.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.qkjwine.entity.QkjwineRInfoEntity;
import org.apache.poi.hpsf.Decimal;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author 
 * @date 2022-02-16 09:16:11
 */
public interface QkjwineRInfoService extends IService<QkjwineRInfoEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjwineRInfoEntity> queryAll(Map<String, Object> params);
    List<QkjwineRInfoEntity> selectSqlSimple(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 获取藏酒价值
     */
    Double queryValue(Map<String, Object> params);

    /**
     * 新增
     *
     * @param qkjwineRInfo 
     * @return 新增结果
     */
    boolean add(QkjwineRInfoEntity qkjwineRInfo);

    /**
     * 根据主键更新
     *
     * @param qkjwineRInfo 
     * @return 更新结果
     */
    boolean update(QkjwineRInfoEntity qkjwineRInfo);
    boolean updateWineInfo(QkjwineRInfoEntity qkjwineRInfo);
    boolean updateTakeIdById(QkjwineRInfoEntity qkjwineRInfo);
    boolean updateScenetimeByWareporid(QkjwineRInfoEntity qkjwineRInfo);
    boolean updateFtnumByWareporid(QkjwineRInfoEntity qkjwineRInfo);
    boolean mdyWineDisableById(QkjwineRInfoEntity qkjwineRInfo);


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
