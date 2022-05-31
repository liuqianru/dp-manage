/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberBasicDao.java
 * 包名称:com.platform.modules.tmp.dao
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-20 14:33:13        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.quartz.entity.TmpQkjvipMemberBasicEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * Dao
 *
 * @author liuqianru
 * @date 2020-08-20 14:33:13
 */
@Mapper
public interface TmpQkjvipMemberBasicDao extends BaseMapper<TmpQkjvipMemberBasicEntity> {

    /**
     * 清空会员临时表
     */
    int deleteAll();
}
