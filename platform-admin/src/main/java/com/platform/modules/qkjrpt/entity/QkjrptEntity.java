/*
 * 项目名称:platform-plus
 * 类名称:QkjrptReportMembertempEntity.java
 * 包名称:com.platform.modules.qkjrpt.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021-09-15 10:51:12        liuqianru     初版做成
 *
 * Copyright (c) 2019-2019 微同软件
 */
package com.platform.modules.qkjrpt.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 地图
 *
 * @author liuqianru
 * @date 2021-09-15 10:51:12
 */
@Getter
public enum  QkjrptEntity  implements IEnum<Integer> {
    Pathology(1, "病理检测"),
    PCR(2, "PCR检测"),
    Stone(3, "结石检测"),
    Endocrine(4, "内分泌检测"),
    Micr(5, "微生物检测"),
    Allergen(6, "过敏原检测"),
    Gene(7, "基因检测"),
    Antigen(8, "抗原抗体检测"),
    PARR(9, "PARR检测");

    private int value;
    private String name;

    @JsonCreator
    QkjrptEntity(int v, String strName) {
        this.value = v;
        this.name = strName;
    }

    @JsonValue
    public int value() {
        return this.value;
    }
    public String GetDescr() {
        return this.name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
