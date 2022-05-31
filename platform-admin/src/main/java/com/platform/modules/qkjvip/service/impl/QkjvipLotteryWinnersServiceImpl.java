/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryWinnersServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-07-07 16:06:26        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipLotteryWinnersDao;
import com.platform.modules.qkjvip.entity.QkjvipLotteryWinnersEntity;
import com.platform.modules.qkjvip.service.QkjvipLotteryWinnersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-07-07 16:06:26
 */
@Service("qkjvipLotteryWinnersService")
public class QkjvipLotteryWinnersServiceImpl extends ServiceImpl<QkjvipLotteryWinnersDao, QkjvipLotteryWinnersEntity> implements QkjvipLotteryWinnersService {

    @Override
    public List<QkjvipLotteryWinnersEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.prizeLevel");
        params.put("asc", true);
        Page<QkjvipLotteryWinnersEntity> page = new Query<QkjvipLotteryWinnersEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipLotteryWinnersPage(page, params));
    }

    @Override
    public boolean add(QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {
        return this.save(qkjvipLotteryWinners);
    }

    @Override
    public boolean addBatch(List<QkjvipLotteryWinnersEntity> winnerList) {
//        this.deleteByMainId(winnerList.get(0).getMainid());
        return this.saveBatch(winnerList);
    }

    @Override
    public boolean update(QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {
        return this.updateById(qkjvipLotteryWinners);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    public boolean deleteByMainId(String mainId) {
        return baseMapper.deleteByMainId(mainId);
    }

    @Override
    public boolean delete2(QkjvipLotteryWinnersEntity qkjvipLotteryWinners) {
        return baseMapper.delete2(qkjvipLotteryWinners);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
