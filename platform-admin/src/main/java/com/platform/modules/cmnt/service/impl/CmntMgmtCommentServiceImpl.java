/*
 * 项目名称:platform-plus
 * 类名称:CmntMgmtCommentServiceImpl.java
 * 包名称:com.platform.modules.cmnt.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-05-25 17:16:11        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.cmnt.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.Query;
import com.platform.modules.cmnt.dao.CmntMgmtCommentDao;
import com.platform.modules.cmnt.entity.CmntMgmtCommentChildrenEntity;
import com.platform.modules.cmnt.entity.CmntMgmtCommentEntity;
import com.platform.modules.cmnt.entity.CmntMgmtCommentResultEntity;
import com.platform.modules.cmnt.entity.CmntMgmtCommentUserEntity;
import com.platform.modules.cmnt.service.CmntMgmtCommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Service实现类
 *
 * @author liuqianru
 * @date 2021-05-25 17:16:11
 */
@Service("cmntMgmtCommentService")
public class CmntMgmtCommentServiceImpl extends ServiceImpl<CmntMgmtCommentDao, CmntMgmtCommentEntity> implements CmntMgmtCommentService {

    @Override
    public List<CmntMgmtCommentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.createdate");
        params.put("asc", false);
        Page<CmntMgmtCommentEntity> page = new Query<CmntMgmtCommentEntity>(params).getPage();
        page.setRecords(baseMapper.selectCmntMgmtCommentPage(page, params));
        page.setTotal(baseMapper.queryAll(params).size());
        return page;
    }

    @Override
    public List<CmntMgmtCommentResultEntity> viewList(Map<String, Object> params) {
        List<CmntMgmtCommentEntity> list = baseMapper.queryList(params);
        List<CmntMgmtCommentResultEntity> commentList = new ArrayList<>();
        for (CmntMgmtCommentEntity cmnt1 : list) {
            if (cmnt1.getCmnttype() != null && cmnt1.getCmnttype() == 1) {  // 评论
                CmntMgmtCommentResultEntity comment = new CmntMgmtCommentResultEntity();
                comment.setId(cmnt1.getId());
                comment.setContent(cmnt1.getComment());
                comment.setCreatedate(cmnt1.getCreatedate());
                comment.setThumbsupcnt(cmnt1.getThumbsupcnt());
                comment.setIsthumbsup(cmnt1.getIsthumbsup());
                // 评论人对象
                CmntMgmtCommentUserEntity commentuser = new CmntMgmtCommentUserEntity();
                commentuser.setMemberid(cmnt1.getMemberid());
                commentuser.setNickname(cmnt1.getMembername());
                commentuser.setAvatar(cmnt1.getAvatar());
                comment.setCommentuser(commentuser);

                List<CmntMgmtCommentChildrenEntity> childrenList = new ArrayList<>();
                for (CmntMgmtCommentEntity cmnt2 : list) {
                    if (cmnt1.getId().equals(cmnt2.getCmntid())) {
                        CmntMgmtCommentChildrenEntity commentChildren = new CmntMgmtCommentChildrenEntity();
                        commentChildren.setId(cmnt2.getId());
                        commentChildren.setContent(cmnt2.getComment());
                        commentChildren.setCreatedate(cmnt2.getCreatedate());
                        commentChildren.setThumbsupcnt(cmnt2.getThumbsupcnt());
                        commentChildren.setIsthumbsup(cmnt2.getIsthumbsup());
                        // 回复人对象
                        CmntMgmtCommentUserEntity subcommentuser = new CmntMgmtCommentUserEntity();
                        subcommentuser.setMemberid(cmnt2.getMemberid());
                        subcommentuser.setNickname(cmnt2.getMembername());
                        subcommentuser.setAvatar(cmnt2.getAvatar());
                        commentChildren.setCommentuser(subcommentuser);
                        // 被回复人对象
                        CmntMgmtCommentUserEntity targetuser = new CmntMgmtCommentUserEntity();
                        targetuser.setMemberid(cmnt2.getTargetmemberid());
                        targetuser.setNickname(cmnt2.getTargetmembername());
                        targetuser.setAvatar(cmnt2.getTargetavatar());
                        commentChildren.setTargetuser(targetuser);

                        childrenList.add(commentChildren);
                    }
                }
                comment.setChildrenlist(childrenList);
                commentList.add(comment);
            } else {
                continue;
            }
        }
        return commentList;
    }

    @Override
    public boolean add(CmntMgmtCommentEntity cmntMgmtComment) {
        return this.save(cmntMgmtComment);
    }

    @Override
    public boolean update(CmntMgmtCommentEntity cmntMgmtComment) {
        return this.updateById(cmntMgmtComment);
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
