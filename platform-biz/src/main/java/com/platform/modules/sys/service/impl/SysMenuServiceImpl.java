/*
 * 项目名称:platform-plus
 * 类名称:SysMenuServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *      日期                修正者      主要内容
 *      2018/11/21 16:04    李鹏军      初版完成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Constant;
import com.platform.common.utils.StringUtils;
import com.platform.modules.sys.dao.SysMenuDao;
import com.platform.modules.sys.entity.SysMenuEntity;
import com.platform.modules.sys.service.SysMenuService;
import com.platform.modules.sys.service.SysRoleMenuService;
import com.platform.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 李鹏军
 */
@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Override
    public List<SysMenuEntity> queryListParentId(String parentId, List<String> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for (SysMenuEntity menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenuEntity> queryListParentId(String parentId) {
        return baseMapper.selectList(new QueryWrapper<SysMenuEntity>().eq("PARENT_ID", parentId).orderByAsc("ORDER_NUM"));
    }


    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenuEntity> getUserMenuList(String userId) {
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.equals(userId) || Constant.SUPER_ADMIN2.equals(userId) || Constant.SUPER_ADMIN3.equals(userId)) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<String> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public boolean delete(String menuId) {
        //删除菜单
        this.removeById(menuId);
        //删除菜单与角色关联
        Map<String, Object> map = new HashMap<>(2);
        map.put("menu_id", menuId);
        return sysRoleMenuService.removeByMap(map);
    }

    @Override
    public List<SysMenuEntity> queryList() {
        return baseMapper.queryList();
    }

    @Override
    public List<SysMenuEntity> queryListRedis() {
        return baseMapper.queryAllPermsToRedis();
    }

    @Override
    public boolean add(SysMenuEntity menu) {
        String parentId = menu.getParentId();
        String maxId = baseMapper.queryMaxIdByParentId(parentId);

        menu.setMenuId(StringUtils.addOne(parentId, maxId));
        return this.save(menu);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenuEntity> getAllMenuList(List<String> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId("0", menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<String> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            //目录
            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

    /**
     * 根据菜单id获取菜单列表
     *
     * @param menuIds
     * @return
     */
    public List<SysMenuEntity> queryListByMenuId(List<String> menuIds) {
        return baseMapper.queryListByMenuId(menuIds);
    }

    /**
     * 获取用户所有菜单
     *
     * @param menuIds
     * @return
     */
    public List<SysMenuEntity> getUserMenuV2(List<String> menuIds) {
        //获取跟菜单
        List<SysMenuEntity> menuList = queryListParentId("0");
        //用户菜单列表
        List<SysMenuEntity> userMenuList = null;
        //判断是否为管理员
        if (menuIds != null) {
            userMenuList = queryListByMenuId(menuIds);
        } else {
            userMenuList = queryList();
        }
        userMenuList=userMenuList.stream().filter(m->!m.getType().equals(2)).collect(Collectors.toList());
        List<SysMenuEntity> subMenuList = new ArrayList<>();

        for (SysMenuEntity entity : menuList) {
            if (menuIds == null || menuIds.contains(entity.getMenuId())) {
                entity.setList(getMenuTree(entity.getMenuId(), userMenuList));
                subMenuList.add(entity);
            }
        }
        return subMenuList;
    }


    /**
     * 递归
     *
     * @param menuList
     * @return
     */
    public List<SysMenuEntity> getMenuTree(String parentId, List<SysMenuEntity> allMenuList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();
        List<SysMenuEntity> menuList = allMenuList.stream().filter(m -> m.getParentId().equals(parentId)).collect(Collectors.toList());
        menuList.sort(Comparator.comparing(a->a.getOrderNum()));
        for (SysMenuEntity entity : menuList) {
            List<SysMenuEntity> subList = allMenuList.stream()
                    .filter(m -> m.getParentId().equals(entity.getMenuId()))
                    .collect(Collectors.toList());
            if (subList != null && subList.size() > 0) {
                entity.setList(getMenuTree(entity.getMenuId(), allMenuList));
            }
            subMenuList.add(entity);
        }
        return subMenuList;
    }


    public List<SysMenuEntity> getUserMenuListV2(String userId) {
        //系统管理员，拥有最高权限
        if (Constant.SUPER_ADMIN.equals(userId) || Constant.SUPER_ADMIN2.equals(userId) || Constant.SUPER_ADMIN3.equals(userId)) {
            return getUserMenuV2(null);
        }

        //用户菜单列表
        List<String> menuIdList = sysUserService.queryAllMenuId(userId);
        return getUserMenuV2(menuIdList);
    }

}
