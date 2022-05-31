/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportActivitypeoplanServiceImpl.java
 * 包名称:com.platform.modules.qkjrpt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-28 11:31:01        孙珊珊     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.qkjrpt.dao.QkjrptReportActivitypeoplanDao;
import com.platform.modules.qkjrpt.entity.QkjrptReportActivitypeoplanEntity;
import com.platform.modules.qkjrpt.service.QkjrptReportActivitypeoplanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author 孙珊珊
 * @date 2021-09-28 11:31:01
 */
@Service("qkjrptReportActivitypeoplanService")
public class QkjrptReportActivitypeoplanServiceImpl extends ServiceImpl<QkjrptReportActivitypeoplanDao, QkjrptReportActivitypeoplanEntity> implements QkjrptReportActivitypeoplanService {

    @Override
    public List<QkjrptReportActivitypeoplanEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<QkjrptReportActivitypeoplanEntity> page = new Query<QkjrptReportActivitypeoplanEntity>(params).getPage();
        return page.setRecords(baseMapper.selectQkjrptReportActivitypeoplanPage(page, params));
    }

    @Override
    public boolean add(QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan) {
        return this.save(qkjrptReportActivitypeoplan);
    }

    @Override
    public boolean update(QkjrptReportActivitypeoplanEntity qkjrptReportActivitypeoplan) {
        return this.updateById(qkjrptReportActivitypeoplan);
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
