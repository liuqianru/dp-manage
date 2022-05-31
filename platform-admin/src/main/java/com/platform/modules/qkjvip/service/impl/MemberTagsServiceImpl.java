/*
 * 项目名称:platform-plus
 * 类名称:QkjvipMemberLabelServiceImpl.java
 * 包名称:com.platform.modules.qkjvip.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-08-26 09:00:34        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjvip.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.StringUtils;
import com.platform.modules.qkjvip.dao.MemberTagsDao;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberTagsEntity;
import com.platform.modules.qkjvip.entity.MemberTagsQueryEntity;
import com.platform.modules.qkjvip.entity.QkjvipTaglibsEntity;
import com.platform.modules.qkjvip.service.MemberTagsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2020-08-26 09:00:34
 */
@Service("memberTagsService")
public class MemberTagsServiceImpl extends ServiceImpl<MemberTagsDao, MemberTagsEntity> implements MemberTagsService {

    @Override
    public List<MemberTagsEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(MemberEntity member) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("memberId", member.getMemberId());
        //先删除会员与标签关系
//        this.removeByMap(map);

        if (member.getMembertags().size() > 0) {
            List<MemberTagsEntity> memberTagsList = baseMapper.queryAll(map);  //根据会员id读取目前会员标签表
//            JSONArray jsonArray = JSONArray.parseArray(member.getMemberLabel());
            List<MemberTagsEntity> memberTagsAddList = new ArrayList<>();
            List<MemberTagsEntity> memberTagsMdyList = new ArrayList<>();
            List<MemberTagsEntity> memberTagsHaveList = new ArrayList<>();
            MemberTagsQueryEntity memberTagsQueryEntity = new MemberTagsQueryEntity();
            for (int i = 0; i < member.getMembertags().size(); i++) {
                memberTagsQueryEntity = member.getMembertags().get(i);
                String tagGroupId = memberTagsQueryEntity.getTagGroupId();
                int tagType = memberTagsQueryEntity.getTagType();

                if (memberTagsList.size() > 0) {  //修改
                    if (tagType == 2) {  //选择型标签
                        List<QkjvipTaglibsEntity> tagList = memberTagsQueryEntity.getTagList();
                        List<String> tagIdList = memberTagsQueryEntity.getTagIdList();
                        for (int j = 0; j < tagIdList.size(); j++) {
                            MemberTagsEntity memberTagsEntity = new MemberTagsEntity();
                            boolean isHave = false;
                            for (MemberTagsEntity memberTag : memberTagsList) {
                                if (StringUtils.isNotBlank(memberTag.getTagId()) && memberTag.getTagId().equals(tagIdList.get(j))) {
                                    isHave = true;
                                    memberTagsEntity = memberTag;
                                    break;
                                }
                            }
                            if (!isHave) {  //传过来的在membertag表不存在的标签，插入
                                memberTagsEntity.setMemberId(member.getMemberId());
                                memberTagsEntity.setTagGroupId(tagGroupId);
                                memberTagsEntity.setTagId(tagIdList.get(j).toString());
                                memberTagsEntity.setTagLocktype(2); //插入的数据全部锁定不可再修改
                                for (QkjvipTaglibsEntity tag : tagList) {
                                    if (tag.getTagId().equals(tagIdList.get(j).toString())) {
                                        memberTagsEntity.setTagValue(tag.getTagName());
                                        break;
                                    }
                                }
                                memberTagsAddList.add(memberTagsEntity);
                            } else {
                                memberTagsHaveList.add(memberTagsEntity);
                            }
                        }
                    } else if (tagType == 1) {
                        MemberTagsEntity memberTagsEntity = new MemberTagsEntity();
                        boolean isHave = false;
                        for (MemberTagsEntity memberTag : memberTagsList) {
                            if (memberTag.getTagGroupId().equals(tagGroupId)) {
                                isHave = true;
                                memberTagsEntity = memberTag;
                                break;
                            }
                        }
                        if (!isHave) {  //传过来的在membertag表不存在的标签，插入
                            memberTagsEntity.setMemberId(member.getMemberId());
                            memberTagsEntity.setTagGroupId(tagGroupId);
                            memberTagsEntity.setTagLocktype(2); //插入的数据全部锁定不可再修改
                            memberTagsEntity.setTagValue(memberTagsQueryEntity.getTagValue());
                            memberTagsAddList.add(memberTagsEntity);
                        } else {
                            memberTagsHaveList.add(memberTagsEntity);
                        }
                    }
                } else {  //插入
                    if (tagType == 2) {  //选择型标签
                        List<QkjvipTaglibsEntity> tagList = memberTagsQueryEntity.getTagList();
                        List<String> tagIdList = memberTagsQueryEntity.getTagIdList();
                        for (int j = 0; j < tagIdList.size(); j++) {
                            MemberTagsEntity memberTagsEntity = new MemberTagsEntity();
                            memberTagsEntity.setMemberId(member.getMemberId());
                            memberTagsEntity.setTagGroupId(tagGroupId);
                            memberTagsEntity.setTagId(tagIdList.get(j).toString());
                            memberTagsEntity.setTagLocktype(2); //插入的数据全部锁定不可再修改
                            for (QkjvipTaglibsEntity tag : tagList) {
                                if (tag.getTagId().equals(tagIdList.get(j))) {
                                    memberTagsEntity.setTagValue(tag.getTagName());
                                    break;
                                }
                            }
                            memberTagsAddList.add(memberTagsEntity);
                        }
                    } else if (tagType == 1) {  //输入型非只读标签
                        MemberTagsEntity memberTagsEntity = new MemberTagsEntity();
                        memberTagsEntity.setMemberId(member.getMemberId());
                        memberTagsEntity.setTagGroupId(tagGroupId);
                        memberTagsEntity.setTagLocktype(2); //插入的数据全部锁定不可再修改
                        memberTagsEntity.setTagValue(memberTagsQueryEntity.getTagValue());
                        memberTagsAddList.add(memberTagsEntity);
                    }
                }
            }
            //保存用户与标签关系
            if (memberTagsAddList.size() > 0) {
                this.saveBatch(memberTagsAddList);
            }
            if (memberTagsHaveList.size() > 0) {
                memberTagsList.removeAll(memberTagsHaveList);  //去除membertag表存在的，只剩下membertag表里要删除的
                memberTagsMdyList = memberTagsList;
                if (memberTagsMdyList.size() > 0) {
                    baseMapper.mdyBatch(memberTagsMdyList);
                }
            }
        } else {
            List<MemberTagsEntity> memberTagsList = baseMapper.queryAll(map);  //根据会员id读取目前会员标签表
            if (memberTagsList.size() > 0) {
                baseMapper.mdyBatch(memberTagsList);
            }
        }
    }

    @Override
    public List<MemberTagsEntity> queryTagsList(Map<String, Object> params) {
        return baseMapper.queryTagsList(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String[] memberIds) {
        return baseMapper.deleteBatch(memberIds);
    }

    @Override
    public int delete(String tagId) {
        return baseMapper.removeByTagId(tagId);
    }

}
