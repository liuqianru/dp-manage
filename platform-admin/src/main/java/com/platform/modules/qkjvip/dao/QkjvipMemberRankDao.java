package com.platform.modules.qkjvip.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.platform.modules.qkjvip.entity.QkjvipMemberRankEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * dao 接口类
 *
 * @author hanjie
 * @date 2021年11月3日11:14:26
 */
@Mapper
public interface QkjvipMemberRankDao extends BaseMapper<QkjvipMemberRankEntity> {

    /**
     * 获取用户积分排行榜
     *
     * @return list
     */
    List<QkjvipMemberRankEntity> getIntegralRank(int size);

    /**
     * 获取扫码排行榜
     *
     * @param params
     * @return 排行榜列表
     */
    List<QkjvipMemberRankEntity> getRankList(@Param("params") Map<String, Object> params);

    /**
     * 获取单个用户的扫码情况
     *
     * @param params
     * @return
     */
    QkjvipMemberRankEntity getUserScanCode(@Param("params") Map<String, Object> params);

    /**
     * 获取单个用户的签到情况
     *
     * @param params
     * @return
     */
    QkjvipMemberRankEntity getUserSignin(@Param("params") Map<String, Object> params);

    /**
     * 获取用户排名前的人数
     *
     * @param params
     * @return
     */
    int getUserTopRank(@Param("params") Map<String, Object> params);

    /**
     * 更新积分排行榜
     */
    void updateIntegralRank();

    /**
     * 更新扫码排行榜
     */
    void updateScanRank();

    /**
     * 更新签到排行榜
     */
    void updateSigninRank();

    /**
     * 更新扫码次数分析
     */
    void updateScanStatic();

    /**
     * 更新签到数量分析
     */
    void updateSigninStatic();
}
