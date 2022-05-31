/*
 * 项目名称:platform-plus
 * 类名称:MemberEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/3/9 14:21            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 会员检索条件实体类
 * @author liuqianru
 * @date 2020/10/29 14:21
 */

@Data
public class MemberQueryEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String memberid;
    private String memberids;
    private String membername;
    private String realname;
    private String mobile;
    private Integer startage;
    private Integer endage;
    private Integer sex;
    private String companyname;
    private String jobtitle;
    private String startregtime;  // 注册开始时间
    private String endregtime;   // 注册结束时间
    private String startaddtime;  // 新增时间开始
    private String endaddtime;  // 新增时间结束
    private String orgno;  // 搜索条件里所属部门or业务员部门
    private String orguserid; // 搜索条件里业务代表or业务员
    private Double startamount1;
    private Double endamount1;
    private Double startamount3;
    private Double endamount3;
    private Double startamount6;
    private Double endamount6;
    private Double startamount12;
    private Double endamount12;
    private Integer startbuynum1;
    private Integer endbuynum1;
    private Integer startbuynum3;
    private Integer endbuynum3;
    private Integer startbuynum6;
    private Integer endbuynum6;
    private Integer startbuynum12;
    private Integer endbuynum12;
    private Double scanamountfrom1;
    private Double scanamountto1;
    private Double scanamountfrom3;
    private Double scanamountto3;
    private Double scanamountfrom6;
    private Double scanamountto6;
    private Double scanamountfrom12;
    private Double scanamountto12;
    private Double scanamountfromall;
    private Double scanamounttoall;
    private Integer scannumfrom1;
    private Integer scannumto1;
    private Integer scannumfrom3;
    private Integer scannumto3;
    private Integer scannumfrom6;
    private Integer scannumto6;
    private Integer scannumfrom12;
    private Integer scannumto12;
    private Integer scannumfromall;
    private Integer scannumtoall;
    private Double startunitprice; // 客单价
    private Double endunitprice;  // 客单价
    private String startbirthday;  // 生日开始
    private String endbirthday;  // 生日结束
    private String areatype;  // 地区类型
    private String membertype;  // 会员类型（暂时废弃）
    private String membernature;  // 会员性质
    private String activitytype;  // 活动类型
    private String startlastactivitydate;  // 最后参加活动开始时间
    private String endlastactivitydate;  // 最后参加活动结束时间
    private String city; // 用户所在城市
    private String listorgno;  // 用户的权限部门
    private String searchlistorgno;   // 搜索部门列表（暂时废弃）
    private String currentmemberid;  // 权限里的当前登录用户id or 业务员id
    private String listmemberchannel;  // 用户的渠道权限
    private Boolean issearchmain;  // 是否主渠道（0否 1是）
    private String searchmemberchannel; // 检索条件的渠道
    private String listmemberlevel;  // 会员等级
    private List<MemberTagsQueryEntity> membertags;  // 会员标签
    private Integer pageindex;
    private Integer pagesize;
    private Integer queryall;  // 0:分页 1：不分页
    private Integer isconsultant;  // 0:全部 1：普通 2：政商务
    private String membergroup;  // 消费者群体
    private String listmemberlayer;  // 圈层
    private Integer identitygroup;  // 身份类型
    private Integer identitylevel;  // 身份等级
    private String areacode;  // 大区
    private String grouporg;  // 客户所属
    private String areaone;  // 一级区域
    private String areatwo;  // 二级区域
    private String areathree;  // 三级区域
    private Boolean iscontainmobile; // 是否有手机号
    private String specialBirthday; // 生日（月-日）
    private String referrer;  // 推荐人
    private String nation;  // 民族
    private Boolean islimitperm; // 是否限制权限（内部使用）
}
