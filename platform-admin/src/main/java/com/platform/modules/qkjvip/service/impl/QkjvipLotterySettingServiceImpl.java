/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotterySettingServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-05 10:59:58        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipLotterySettingDao;
import com.platform.modules.qkjvip.entity.QkjvipLotterySettingEntity;
import com.platform.modules.qkjvip.service.QkjvipLotterySettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-07-05 10:59:58
 */
@Service("qkjvipLotterySettingService")
public class QkjvipLotterySettingServiceImpl extends ServiceImpl<QkjvipLotterySettingDao, QkjvipLotterySettingEntity> implements QkjvipLotterySettingService {

    @Override
    public List<QkjvipLotterySettingEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<QkjvipLotterySettingEntity> page = new Query<QkjvipLotterySettingEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipLotterySettingPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipLotterySettingEntity qkjvipLotterySetting) {
        return this.save(qkjvipLotterySetting);
    }

    @Override
    public boolean update(QkjvipLotterySettingEntity qkjvipLotterySetting) {
        return this.updateById(qkjvipLotterySetting);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
