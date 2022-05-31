/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberFansServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-09 14:02:22        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.modules.quartz.dao.QrtzLastUpdateTimeDao;
import com.platform.modules.quartz.entity.QrtzLastUpdateTimeEntity;
import com.platform.modules.quartz.service.QrtzLastUpdateTimeService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-09-09 14:02:22
 */
@Service("qrtzLastUpdateTimeService")
public class QrtzLastUpdateTimeServiceImpl extends ServiceImpl<QrtzLastUpdateTimeDao, QrtzLastUpdateTimeEntity> implements QrtzLastUpdateTimeService {

    @Override
    public List<QrtzLastUpdateTimeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public void updateLastDatetime(QrtzLastUpdateTimeEntity updateTimeEntity) {
        baseMapper.updateLastDatetime(updateTimeEntity);
    }
}
