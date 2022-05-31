package com.platform.modules.qkjvip.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName QkjvipQuestionnaireExcelEntity
 * @Description 青海华实科技
 * @Author 孟学涛
 * @Date 2022/5/13 17:59
 * @Version 1.2
 **/
@Data
public class QkjvipQuestionnaireExcelEntity implements Serializable {

    /**
     * 题目
     */
    @Excel(name = "题目", orderNum = "1", width = 25)
    private String topic;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称", orderNum = "2", width = 25)
    private String nickname;

    /**
     * unionid
     */
    @Excel(name = "unionid", orderNum = "3", width = 25)
    private String unionId;

    /**
     * 添加時間
     */
    @Excel(name = "添加时间", orderNum = "4", width = 25, format = "yyyy-MM-dd HH:mm:ss")
    private Date addTime;

    /**
     * 选项
     */
    @Excel(name = "选项", orderNum = "5", width = 25)
    private String perOption;

    /**
     * 内容
     */
    @Excel(name = "内容", orderNum = "6", width = 25)
    private String perContent;

    /**
     * 渠道
     */
    @Excel(name = "渠道", orderNum = "7", width = 25)
    private String serviceName;
}
