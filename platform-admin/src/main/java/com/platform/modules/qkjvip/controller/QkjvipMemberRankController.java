package com.platform.modules.qkjvip.controller;

import cn.emay.util.JsonHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.JedisUtil;
import com.platform.common.utils.RestResponse;
import com.platform.modules.qkjvip.entity.MemberEntity;
import com.platform.modules.qkjvip.entity.MemberRankParam;
import com.platform.modules.qkjvip.entity.QkjvipMemberRankEntity;
import com.platform.modules.qkjvip.service.MemberService;
import com.platform.modules.qkjvip.service.QkjvipMemberRankService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysLogEntity;
import com.platform.modules.sys.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.alibaba.fastjson.JSON.toJSONString;

/**
 * 获取排行榜信息
 *
 * @author hanjie
 * @date 2021-11-03
 */
@Component("qrtzMemberRank")
@RestController
@RequestMapping("/qkjvip/memberrank")
public class QkjvipMemberRankController extends AbstractController {
    @Autowired
    private QkjvipMemberRankService rankSevice;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysLogService sysLogService;

    /**
     * 获取积分排行榜
     *
     * @param rankParam
     * @return
     */
    @RequestMapping(value = "/getintegralrank", method = RequestMethod.POST)
    public RestResponse getIntegralRank(@RequestBody MemberRankParam rankParam) {
        //获取排行榜
        int size = rankParam == null || rankParam.getPagesize() == 0 ? 10 : rankParam.getPagesize();
        List<QkjvipMemberRankEntity> rankList = rankSevice.getIntegralRank(size);
        //获取当前人排行
        if (rankParam != null && rankParam.getMemberid() != null && !rankParam.getMemberid().equals("")) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("memberId", rankParam.getMemberid());
            //获取当前人员的信息
            List<MemberEntity> memberList = memberService.queryAll(params);
            if (memberList != null && memberList.size() > 0) {
                MemberEntity memberEntity = memberList.get(0);
                QkjvipMemberRankEntity userRank = new QkjvipMemberRankEntity();
                userRank.setMembername(memberEntity.getMemberName());
                userRank.setMemberlevel(memberEntity.getMemberLevel());
                userRank.setRank(memberEntity.getIntegral());
                userRank.setRanktype(0);
                RestResponse.success().put("userLevel", userRank);
            }
        }
        return RestResponse.success().put("rankList", rankList);
    }

    /**
     * 获取非等级排行榜
     *
     * @param rankParam
     * @return
     */
    @RequestMapping(value = "/getranklist", method = RequestMethod.POST)
    public RestResponse getRankList(@RequestBody MemberRankParam rankParam) {
        if (rankParam == null) {
            return RestResponse.error("参数不允许为空");
        }

        SysLogEntity logEntity=new SysLogEntity();
        logEntity.setParams(toJSONString(rankParam));
        logEntity.setMethod("getranklist");
        logEntity.setCreateTime(new Date());
        logEntity.setTime((long)3000);
        sysLogService.save(logEntity);

        //获取等级排名列表
        int size = rankParam == null || rankParam.getPagesize() == 0 ? 10 : rankParam.getPagesize();
        List<QkjvipMemberRankEntity> rankList = null;
        if (rankParam.getRanktype() == 0) {
            rankList = rankSevice.getIntegralRank(size);

        }
        //获取非等级排名列表
        else {
            HashMap<String, Object> searchParams = new HashMap<>();
            searchParams.put("pagesize", size);
            searchParams.put("ranktype", rankParam.getRanktype());
            rankList = rankSevice.getRankList(searchParams);
        }
        QkjvipMemberRankEntity userRank = null;
        //获取当前用户排名
        if (rankParam != null && rankParam.getMemberid() != null && !rankParam.getMemberid().equals("")) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("memberId", rankParam.getMemberid());
            //获取当前人员的信息
            List<MemberEntity> memberList = memberService.selectMemberByJuruMemberid(params);
            if (memberList != null && memberList.size() > 0) {
                MemberEntity memberEntity = memberList.get(0);
                if (rankParam.getRanktype() == 0) {
                    userRank = new QkjvipMemberRankEntity();
                    userRank.setMembername(memberEntity.getMemberName());
                    userRank.setMemberlevel(memberEntity.getMemberLevel());
                    userRank.setRank(memberEntity.getIntegral()==null?0:memberEntity.getIntegral());
                    userRank.setRanktype(0);
                    userRank.setUnionid(memberEntity.getUnionid());
                    userRank.setMemberid(memberEntity.getMemberId());
                    userRank.setHeadimg(memberEntity.getHeadImgUrl());
                } else {
                    Map<String, Object> userParam = new HashMap<>();
                    userParam.put("ranktype", rankParam.getRanktype());
                    userParam.put("memberid", memberEntity.getMemberId());
                    if (memberEntity.getUnionid() != null && !memberEntity.getUnionid().equals("") && rankParam.getRanktype() < 3) {
                        userParam.put("unionid", memberEntity.getUnionid());
                        userRank = rankSevice.getUserScanCode(userParam);
                    } else if (rankParam.getRanktype() >= 3) {
                        userRank = rankSevice.getUserSignin(userParam);
                    }

                    if (userRank != null) {
                        userParam.put("ranknum", userRank.getRank());
                        int toprankcount = rankSevice.getUserTopRank(userParam);
                        userRank.setRownum(toprankcount + 1);

                        if (rankList.stream().anyMatch(m -> (m.getMemberid() != null && m.getMemberid().equals(memberEntity.getMemberId()))
                                || (m.getUnionid() != null && m.getUnionid().equals(memberEntity.getUnionid())))) {
                            QkjvipMemberRankEntity userbyList =
                                    rankList.stream()
                                            .filter(m -> (m.getMemberid() != null && m.getMemberid().equals(memberEntity.getMemberId()))
                                                    || (m.getUnionid() != null && m.getUnionid().equals(memberEntity.getUnionid())))
                                            .findFirst().get();
                            userRank.setRownum(userbyList == null || userbyList.getRank() < userRank.getRank() ? toprankcount + 1 : userbyList.getRownum());
                        }
                    }
                }
            }
        }

        return RestResponse.success().put("userRank", userRank).put("rankList", rankList);
    }

    /**
     * 更新用户排行榜
     *
     * @return
     */
    @RequestMapping(value = "/updatememberrank", method = RequestMethod.GET)
    public RestResponse updateMemberRank() {
        rankSevice.updateIntegralRank();
        rankSevice.updateScanRank();
        rankSevice.updateSigninRank();
        rankSevice.updateScanStatic();
        rankSevice.updateSigninStatic();
        return RestResponse.success();
    }


}
