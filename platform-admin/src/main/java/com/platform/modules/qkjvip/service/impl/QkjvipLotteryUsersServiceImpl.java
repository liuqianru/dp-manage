/*
 * 项目名称:platform-plus
 * 类名称:QkjvipLotteryUsersServiceImpl.java
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
import com.platform.modules.qkjvip.dao.QkjvipLotteryUsersDao;
import com.platform.modules.qkjvip.entity.QkjvipLotteryUsersEntity;
import com.platform.modules.qkjvip.service.QkjvipLotteryUsersService;
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
@Service("qkjvipLotteryUsersService")
public class QkjvipLotteryUsersServiceImpl extends ServiceImpl<QkjvipLotteryUsersDao, QkjvipLotteryUsersEntity> implements QkjvipLotteryUsersService {

    @Override
    public List<QkjvipLotteryUsersEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public List<QkjvipLotteryUsersEntity> queryLotteryUsers(Map<String, Object> params) {
        return baseMapper.queryLotteryUsers(params);
    }

    @Override
    public List<QkjvipLotteryUsersEntity> queryWinners(Map<String, Object> params) {
        return baseMapper.queryWinners(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.mainId");
        params.put("asc", false);
        Page<QkjvipLotteryUsersEntity> page = new Query<QkjvipLotteryUsersEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipLotteryUsersPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(QkjvipLotteryUsersEntity qkjvipLotteryUsers) {
        return this.save(qkjvipLotteryUsers);
    }

    @Override
    public boolean addBatch(List<QkjvipLotteryUsersEntity> userList) {
        return this.saveBatch(userList);
    }

    @Override
    public boolean update(QkjvipLotteryUsersEntity qkjvipLotteryUsers) {
        return this.updateById(qkjvipLotteryUsers);
    }

    @Override
    public boolean updateBatch(List<QkjvipLotteryUsersEntity> userList) {
        return this.updateBatchById(userList, 200);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    public boolean deleteByMainId(String mainid) {
        return baseMapper.deleteByMainId(mainid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }
}
