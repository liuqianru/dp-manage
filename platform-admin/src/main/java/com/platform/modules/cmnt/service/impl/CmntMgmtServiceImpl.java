/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtServiceImpl.java
 * 包名称:com.platform.modules.cmnt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:11        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.cmnt.dao.CmntMgmtDao;
import com.platform.modules.cmnt.entity.CmntMgmtEntity;
import com.platform.modules.cmnt.service.CmntMgmtService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-05-25 17:16:11
 */
@Service("cmntMgmtService")
public class CmntMgmtServiceImpl extends ServiceImpl<CmntMgmtDao, CmntMgmtEntity> implements CmntMgmtService {

    @Override
    public List<CmntMgmtEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addtime");
        params.put("asc", false);
        Page<CmntMgmtEntity> page = new Query<CmntMgmtEntity>(params).getPage();
        page.setRecords(baseMapper.selectCmntMgmtPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public boolean add(CmntMgmtEntity cmntMgmt) {
        return this.save(cmntMgmt);
    }

    @Override
    public boolean update(CmntMgmtEntity cmntMgmt) {
        return this.updateById(cmntMgmt);
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
