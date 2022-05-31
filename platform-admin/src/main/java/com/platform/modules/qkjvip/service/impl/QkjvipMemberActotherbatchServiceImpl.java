/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberActotherbatchServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-04-26 15:07:44        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberActotherbatchDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberActotherbatchEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberActotherbatchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-04-26 15:07:44
 */
@Service("qkjvipMemberActotherbatchService")
public class QkjvipMemberActotherbatchServiceImpl extends ServiceImpl<QkjvipMemberActotherbatchDao, QkjvipMemberActotherbatchEntity> implements QkjvipMemberActotherbatchService {

    @Override
    public List<QkjvipMemberActotherbatchEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.batch_num");
        params.put("asc", false);
        Page<QkjvipMemberActotherbatchEntity> page = new Query<QkjvipMemberActotherbatchEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjvipMemberActotherbatchPage(page, params));
    }

    @Override
    public boolean add(QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch) {
        return this.save(qkjvipMemberActotherbatch);
    }

    @Override
    public boolean update(QkjvipMemberActotherbatchEntity qkjvipMemberActotherbatch) {
        return this.updateById(qkjvipMemberActotherbatch);
    }

    @Override
    public boolean delete(String mainId) {
        return this.removeById(mainId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] mainIds) {
        return this.removeByIds(Arrays.asList(mainIds));
    }
}
