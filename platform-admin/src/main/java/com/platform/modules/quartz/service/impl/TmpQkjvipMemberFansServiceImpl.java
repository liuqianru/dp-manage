/*
 * 项目名称:platform-plus
 * 类名称:TmpQkjvipMemberFansServiceImpl.java
 * 包名称:com.platform.modules.tmp.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-14 13:51:21        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.quartz.dao.TmpQkjvipMemberFansDao;
import com.platform.modules.quartz.entity.TmpQkjvipMemberBasicEntity;
import com.platform.modules.quartz.entity.TmpQkjvipMemberFansEntity;
import com.platform.modules.quartz.service.TmpQkjvipMemberFansService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-09-14 13:51:21
 */
@Service("tmpQkjvipMemberFansService")
public class TmpQkjvipMemberFansServiceImpl extends ServiceImpl<TmpQkjvipMemberFansDao, TmpQkjvipMemberFansEntity> implements TmpQkjvipMemberFansService {

    @Override
    public void addBatch(List<TmpQkjvipMemberFansEntity> fanList) {
        //批量插入前先清空临时表
        baseMapper.deleteAll();
        this.saveBatch(fanList);
    }
}
