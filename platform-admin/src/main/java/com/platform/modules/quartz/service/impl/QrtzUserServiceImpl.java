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
import com.platform.modules.quartz.dao.QrtzUserDao;
import com.platform.modules.quartz.entity.QrtzUserEntity;
import com.platform.modules.quartz.service.QrtzUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * UserServiceImpl
 *
 * @author liuqianru
 * @date 2020/3/25 15:14
 */
@Service("qrtzUserService")
public class QrtzUserServiceImpl extends ServiceImpl<QrtzUserDao, QrtzUserEntity> implements QrtzUserService {
    @Override
    @DataSource(name="second")
    public List<QrtzUserEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }
}
