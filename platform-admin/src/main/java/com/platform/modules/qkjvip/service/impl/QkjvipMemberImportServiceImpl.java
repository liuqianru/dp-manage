/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberImportServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-09-25 08:41:43        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjvip.dao.QkjvipMemberImportDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberImportEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberImportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @date 2020-09-25 08:41:43
 */
@Service("qkjvipMemberImportService")
public class QkjvipMemberImportServiceImpl extends ServiceImpl<QkjvipMemberImportDao, QkjvipMemberImportEntity> implements QkjvipMemberImportService {

    @Override
    public List<QkjvipMemberImportEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.memberId");
        params.put("asc", false);
        Page<QkjvipMemberImportEntity> page = new Query<QkjvipMemberImportEntity>(params).getPage();
        page.setRecords(baseMapper.selectQkjvipMemberImportPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }


    @Override
    public List<QkjvipMemberImportEntity> selectMemberByMobile(Map<String, Object> params) {
        return baseMapper.selectMemberByMobile(params);
    }

    @Override
    public boolean add(QkjvipMemberImportEntity qkjvipMemberImport) {
        return this.save(qkjvipMemberImport);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBatch(List<QkjvipMemberImportEntity> mList) {
        this.saveBatch(mList);
    }

    @Override
    public boolean update(QkjvipMemberImportEntity qkjvipMemberImport) {
        return this.updateById(qkjvipMemberImport);
    }

    @Override
    public boolean delete(String memberId) {
        return this.removeById(memberId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] memberIds) {
        return this.removeByIds(Arrays.asList(memberIds));
    }
}
