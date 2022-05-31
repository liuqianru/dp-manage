/*
 * 项目名称:platform-plus
 * 类名称:Double2Serializer.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/2/3 15:11            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Double2Serializer
 *
 * @author liuqianru
 * @date 2021/2/3 15:11
 */
public class Double2Serializer extends JsonSerializer<Double> {
    private DecimalFormat df = new DecimalFormat("0.00");

    /**
     * 小数保留2位返回给前端序列化器
     * @param data
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(Double data, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {
        if (data != null) {
            if (data == 0) {
                jsonGenerator.writeString("0");
            } else {
                jsonGenerator.writeString(df.format(data));
            }
        }
    }
}
