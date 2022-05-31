/*
 * 项目名称:platform-plus
 * 类名称:SysUserSuperviseServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-08-04 09:26:18        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysUserSuperviseDao;
import com.platform.modules.sys.entity.SysUserSuperviseEntity;
import com.platform.modules.sys.service.SysUserSuperviseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-08-04 09:26:18
 */
@Service("sysUserSuperviseService")
public class SysUserSuperviseServiceImpl extends ServiceImpl<SysUserSuperviseDao, SysUserSuperviseEntity> implements SysUserSuperviseService {

    @Override
    public List<SysUserSuperviseEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.addTime");
        params.put("asc", false);
        Page<SysUserSuperviseEntity> page = new Query<SysUserSuperviseEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysUserSupervisePage(page, params));
    }

    @Override
    public boolean add(SysUserSuperviseEntity sysUserSupervise) {
        return this.save(sysUserSupervise);
    }

    @Override
    public boolean update(SysUserSuperviseEntity sysUserSupervise) {
        return this.updateById(sysUserSupervise);
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
