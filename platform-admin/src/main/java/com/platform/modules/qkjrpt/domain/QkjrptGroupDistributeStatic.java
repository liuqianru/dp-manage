package com.platform.modules.qkjrpt.domain;

import lombok.Data;

/**
 * 区域统计实体
 *
 * @author hanjie
 * @date 2021-11-11 15:44:11
 */
@Data
public class QkjrptGroupDistributeStatic {

    /**
     * 区域字典值
     */
    public int areavalue;

    /**
     * 区域名称
     */
    public String areaname;

    /**
     * 经销商数量
     */
    public int dstcount;
}
