/*
 * 项目名称:platform-plus
 * 类名称:SysOrgUpdatelogServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 13:52:13        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysOrgUpdatelogDao;
import com.platform.modules.sys.entity.SysOrgUpdatelogEntity;
import com.platform.modules.sys.service.SysOrgUpdatelogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-08-04 13:52:13
 */
@Service("sysOrgUpdatelogService")
public class SysOrgUpdatelogServiceImpl extends ServiceImpl<SysOrgUpdatelogDao, SysOrgUpdatelogEntity> implements SysOrgUpdatelogService {

    @Override
    public List<SysOrgUpdatelogEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.deptcode");
        params.put("asc", false);
        Page<SysOrgUpdatelogEntity> page = new Query<SysOrgUpdatelogEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysOrgUpdatelogPage(page, params));
    }

    @Override
    public boolean add(SysOrgUpdatelogEntity sysOrgUpdatelog) {
        return this.save(sysOrgUpdatelog);
    }

    @Override
    public boolean update(SysOrgUpdatelogEntity sysOrgUpdatelog) {
        return this.updateById(sysOrgUpdatelog);
    }

    @Override
    public boolean delete(String deptcode) {
        return this.removeById(deptcode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] deptcodes) {
        return this.removeByIds(Arrays.asList(deptcodes));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<SysOrgUpdatelogEntity> sysOrgUpdatelog) {
        this.saveBatch(sysOrgUpdatelog, 100);
    }
}
