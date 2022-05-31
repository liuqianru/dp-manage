/*
 * 项目名称:platform-plus
 * 类名称:SysUserServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysUserRegDao;
import com.platform.modules.sys.entity.SysUserRegEntity;
import com.platform.modules.sys.service.SysUserRedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Map;

/**
 * @author 孙珊
 */
@Service("sysUserRegService")
public class SysUserRegServiceImpl extends ServiceImpl<SysUserRegDao, SysUserRegEntity> implements SysUserRedService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(SysUserRegEntity userreg,Map<String, Object> params) {
        this.save(userreg);

    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.ADD_TIME");
        params.put("asc", false);
        Page<SysUserRegEntity> page=new Query<SysUserRegEntity>(params).getPage();
        return page.setRecords(baseMapper.selectRegListPage(page,params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserRegEntity user, Map<String, Object> params) {
        this.updateById(user);

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateState(SysUserRegEntity reg) {
        baseMapper.updateStateById(reg);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] userIds) {
        this.removeByIds(Arrays.asList(userIds));
    }


}
