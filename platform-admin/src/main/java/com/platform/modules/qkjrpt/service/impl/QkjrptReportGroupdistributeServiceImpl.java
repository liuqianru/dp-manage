/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportGroupdistributeServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-11-11 15:11:18        hanjie     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptReportGroupdistributeDao;
import com.platform.modules.qkjrpt.domain.QkjrptGroupDistributeStatic;

import com.platform.modules.qkjrpt.entity.QkjrptReportGroupdistributeEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportGroupdistributeService;
import com.platform.modules.sys.entity.SysOrgEntity;
import com.platform.modules.sys.service.SysOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author hanjie
 * @date 2021-11-11 15:11:18
 */
@Service("qkjrptReportGroupdistributeService")
public class QkjrptReportGroupdistributeServiceImpl extends ServiceImpl<QkjrptReportGroupdistributeDao, QkjrptReportGroupdistributeEntity> implements QkjrptReportGroupdistributeService {
    @Autowired
    private SysOrgService sysOrgService;

    @Override
    public List<QkjrptReportGroupdistributeEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.CreateTime");
        params.put("asc", false);
        Page<QkjrptReportGroupdistributeEntity> page = new Query<QkjrptReportGroupdistributeEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptReportGroupdistributePage(page, params));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute) {
        this.save(qkjrptReportGroupdistribute);
        // 同时添加部门
        SysOrgEntity sysOrg = new SysOrgEntity();
        sysOrg.setOrgNo(qkjrptReportGroupdistribute.getId());
        sysOrg.setOrgName(qkjrptReportGroupdistribute.getDistributername());
        sysOrg.setParentNo("01");
        sysOrg.setStatus(1);
        sysOrgService.add2(sysOrg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(QkjrptReportGroupdistributeEntity qkjrptReportGroupdistribute) {
        this.updateById(qkjrptReportGroupdistribute);
        // 同时修改部门
        SysOrgEntity sysOrg = sysOrgService.getById(qkjrptReportGroupdistribute.getId());
        sysOrg.setOrgName(qkjrptReportGroupdistribute.getDistributername());
        sysOrgService.update(sysOrg);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(String[] ids) {
        this.removeByIds(Arrays.asList(ids));
        // 同时删除部门
        if (ids.length > 0) {
            for (int i = 0; i < ids.length; i++) {
                sysOrgService.delete(ids[i]);
            }
        }
    }

    /**
     * 查询经销商区域统计数据
     *
     * @param params
     * @return
     */
    @Override
    public List<QkjrptGroupDistributeStatic> getPrimaryAreaDst(Map<String, Object> params) {
        if ((params.containsKey("secondary") && !params.get("secondary").toString().equals("")) || params.containsKey("tertiary")) {
            return baseMapper.getTertiaryAreaDst(params);
        } else if (params.containsKey("primary")) {
            return baseMapper.getSecondaryAreaDst(params);
        } else {
            return baseMapper.getPrimaryAreaDst(params);
        }
    }
}
