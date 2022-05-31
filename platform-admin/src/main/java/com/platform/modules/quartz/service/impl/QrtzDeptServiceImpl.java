/*
 * 项目名称:platform-plus
 * 类名称:OrgUserServiceImpl.java
 * 包名称:com.platform.modules.quartz.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/25 15:14            liuqianru    初版做成
 *
 */
package com.platform.modules.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.datasources.annotation.DataSource;
import com.platform.modules.quartz.dao.QrtzDeptDao;
import com.platform.modules.quartz.entity.QrtzDeptEntity;
import com.platform.modules.quartz.service.QrtzDeptService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * OrgUserServiceImpl
 *
 * @author liuqianru
 * @date 2020/3/25 15:14
 */
@Service("qrtzDeptService")
public class QrtzDeptServiceImpl extends ServiceImpl<QrtzDeptDao, QrtzDeptEntity> implements QrtzDeptService {
    @Override
    @DataSource(name="second")
    public List<QrtzDeptEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }
}
