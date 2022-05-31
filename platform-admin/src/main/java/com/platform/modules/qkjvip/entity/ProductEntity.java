/*
 * 项目名称:platform-plus
 * 类名称:NewsEntity.java
 * 包名称:com.platform.modules.qkjvip.entity
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/3/11 13:12            liuqianru    初版做成
 *
 */
package com.platform.modules.qkjvip.entity;

import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.io.Serializable;

/**
 * ProductEntity
 *
 * @author liuqianru
 * @date 2021/3/11 13:12
 */
@Data
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pid;
    private String pname;
    private Integer price;
    private String showimg;
}
