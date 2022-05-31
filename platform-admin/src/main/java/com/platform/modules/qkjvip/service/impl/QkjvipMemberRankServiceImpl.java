package com.platform.modules.qkjvip.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.modules.qkjvip.dao.QkjvipMemberRankDao;
import com.platform.modules.qkjvip.entity.QkjvipMemberRankEntity;
import com.platform.modules.qkjvip.service.QkjvipMemberRankService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * service实现类
 *
 * @author hanjie
 * @date 2021年11月3日10:49:57
 */
@Service("QkjvipMemberRankSevice")
public class QkjvipMemberRankServiceImpl extends ServiceImpl<QkjvipMemberRankDao, QkjvipMemberRankEntity> implements QkjvipMemberRankService {

    /**
     * 获取用户积分排行榜
     *
     * @return
     */
    @Override
    public List<QkjvipMemberRankEntity> getIntegralRank(int size) {
        return baseMapper.getIntegralRank(size);
    }

    /**
     * 获取扫码排行榜
     *
     * @param params
     * @return
     */
    @Override
    public List<QkjvipMemberRankEntity> getRankList(Map<String, Object> params) {
        return baseMapper.getRankList(params);
    }

    /**
     * 获取单个用户的签到情况
     *
     * @param params
     * @return
     */
    @Override
    public QkjvipMemberRankEntity getUserScanCode(Map<String, Object> params) {
        return baseMapper.getUserScanCode(params);
    }

    /**
     * 获取单个用户的签到情况
     *
     * @param params
     * @return
     */
    @Override
    public QkjvipMemberRankEntity getUserSignin(Map<String, Object> params) {
        return baseMapper.getUserSignin(params);
    }

    /**
     * 获取用户排名前的人数
     *
     * @param params
     * @return
     */
    @Override
    public int getUserTopRank(Map<String, Object> params) {
        return baseMapper.getUserTopRank(params);
    }

    /**
     * 更新积分排行榜
     */
    @Override
    public void updateIntegralRank() {
        baseMapper.updateIntegralRank();
    }

    /**
     * 更新扫码排行榜
     */
    @Override
    public void updateScanRank() {
        baseMapper.updateScanRank();
    }

    /**
     * 更新签到排行榜
     */
    @Override
    public void updateSigninRank() {
        baseMapper.updateSigninRank();
    }

    /**
     * 更新扫码次数分析
     */
    @Override
    public void updateScanStatic() {
        baseMapper.updateScanStatic();
    }

    /**
     * 更新签到数量分析
     */
    @Override
    public void updateSigninStatic() {
        baseMapper.updateSigninStatic();
    }
}