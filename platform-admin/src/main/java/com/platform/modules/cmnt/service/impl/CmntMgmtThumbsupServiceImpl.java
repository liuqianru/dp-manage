/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtThumbsupServiceImpl.java
 * 包名称:com.platform.modules.cmnt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:10        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.cmnt.dao.CmntMgmtThumbsupDao;
import com.platform.modules.cmnt.entity.CmntMgmtThumbsupEntity;
import com.platform.modules.cmnt.service.CmntMgmtThumbsupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-05-25 17:16:10
 */
@Service("cmntMgmtThumbsupService")
public class CmntMgmtThumbsupServiceImpl extends ServiceImpl<CmntMgmtThumbsupDao, CmntMgmtThumbsupEntity> implements CmntMgmtThumbsupService {

    @Override
    public List<CmntMgmtThumbsupEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createdate");
        params.put("asc", false);
        Page<CmntMgmtThumbsupEntity> page = new Query<CmntMgmtThumbsupEntity>(params).getPage();
        page.setRecords(baseMapper.selectCmntMgmtThumbsupPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(CmntMgmtThumbsupEntity cmntMgmtThumbsup) {
        return this.save(cmntMgmtThumbsup);
    }

    @Override
    public boolean update(CmntMgmtThumbsupEntity cmntMgmtThumbsup) {
        return this.updateById(cmntMgmtThumbsup);
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

    @Override
    public boolean doDelete(CmntMgmtThumbsupEntity cmntMgmtThumbsup) {
        return baseMapper.doDelete(cmntMgmtThumbsup);
    }
}
