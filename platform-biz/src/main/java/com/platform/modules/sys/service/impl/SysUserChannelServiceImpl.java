/*
 * 项目名称:platform-plus
 * 类名称:SysUserChannelServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-12-09 11:32:32        李鹏军     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.Query;
import com.platform.common.utils.ShiroUtils;
import com.platform.modules.sys.dao.SysUserChannelDao;
import com.platform.modules.sys.entity.SysUserChannelEntity;
import com.platform.modules.sys.service.SysUserChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author 李鹏军
 * @date 2020-12-09 11:32:32
 */
@Service("sysUserChannelService")
public class SysUserChannelServiceImpl extends ServiceImpl<SysUserChannelDao, SysUserChannelEntity> implements SysUserChannelService {

    @Override
    public List<SysUserChannelEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SysUserChannelEntity> page = new Query<SysUserChannelEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysUserChannelPage(page, params));
    }

    @Override
    public boolean add(SysUserChannelEntity sysUserChannel) {
        return this.save(sysUserChannel);
    }

    @Override
    public boolean update(SysUserChannelEntity sysUserChannel) {
        return this.updateById(sysUserChannel);
    }

    @Override
    public void saveOrUpdate(String userId, List<Integer> channelIdList) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("user_id", userId);
        //先删除用户与渠道关系
        this.removeByMap(map);

        if (channelIdList == null || channelIdList.size() == 0) {
            return;
        }

        //保存用户与渠道关系
        List<SysUserChannelEntity> list = new ArrayList<>(channelIdList.size());
        for (Integer channelId : channelIdList) {
            SysUserChannelEntity sysUserChannelEntity = new SysUserChannelEntity();
            sysUserChannelEntity.setUserId(userId);
            sysUserChannelEntity.setChannelId(channelId);

            list.add(sysUserChannelEntity);
        }
        this.saveBatch(list);
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
    public List<Integer> queryChannelIdList(String userId) {
        return baseMapper.queryChannelIdList(userId);
    }

    @Override
    public String queryChannelIdByUserId(String userId) {
        List<Integer> channelIdlist = baseMapper.queryChannelIdList(userId);
        StringBuilder channelIdStr = new StringBuilder();
        String alias = "";
        if (channelIdlist != null && !channelIdlist.isEmpty()) {
            for (Integer channelId : channelIdlist) {
                channelIdStr.append(",");
                channelIdStr.append(channelId);
            }
            alias = channelIdStr.toString().substring(1, channelIdStr.length());
        }
        return alias;
    }

    @Override
    public List<SysUserChannelEntity> queryPermissionChannels(Map<String, Object> params) {
        return baseMapper.queryPermissionChannels(params);
    }
}
