/*
 * 项目名称:platform-plus
 * 类名称:QkjvipQuestionnaireDao.java
 * 包名称:com.platform.modules.qkjvip.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-16 10:27:08        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireEntity;
import com.platform.modules.qkjvip.entity.QkjvipQuestionnaireExcelEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2021-07-16 10:27:08
 */
@Mapper
public interface QkjvipQuestionnaireDao extends BaseMapper<QkjvipQuestionnaireEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<QkjvipQuestionnaireEntity> queryAll(@Param("params") Map<String, Object> params);

    /**
     * 自定义分页查询
     *
     * @param page   分页参数
     * @param params 查询参数
     * @return List
     */
    List<QkjvipQuestionnaireEntity> selectQkjvipQuestionnairePage(IPage page, @Param("params") Map<String, Object> params);

    /**
     * 查询导出列表
     * @param params 查询参数
     * @return
     */
    List<QkjvipQuestionnaireExcelEntity> ExcelList(@Param("params") Map<String, Object> params);
}
