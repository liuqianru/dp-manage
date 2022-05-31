/*
 * 项目名称:platform-plus
 * 类名称:ExcelUtil.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2021/8/10 9:23            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * ExcelUtil
 *
 * @author liuqianru
 * @date 2021/8/10 9:23
 */
public class ExcelUtil {
    /**
     * 给Cell添加批注
     *
     * @param cell 单元格
     * @param value 批注内容
     * @param extension 扩展名
     */
    public static void addComment(Cell cell, String value, String extension) {
        Sheet sheet = cell.getSheet();
        cell.removeCellComment();
        if ("xls".equals(extension)) {
            ClientAnchor anchor = new HSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 5);
            anchor.setRow2(cell.getRowIndex() + 6);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new HSSFRichTextString(value));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        } else if ("xlsx".equals(extension)) {
            ClientAnchor anchor = new XSSFClientAnchor();
            // 关键修改
            anchor.setDx1(0);
            anchor.setDx2(0);
            anchor.setDy1(0);
            anchor.setDy2(0);
            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 5);
            anchor.setRow2(cell.getRowIndex() + 6);
            // 结束
            Drawing drawing = sheet.createDrawingPatriarch();
            Comment comment = drawing.createCellComment(anchor);
            // 输入批注信息
            comment.setString(new XSSFRichTextString(value));
            // 将批注添加到单元格对象中
            cell.setCellComment(comment);
        }
    }

    public static void setStyles(Cell cell, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.RED.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(style);
    }

    public static void setStylesBlue(Cell cell, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        cell.setCellStyle(style);
    }

    public static void setStylesNoEdit(Cell cell, Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style = workbook.createCellStyle();
        style.setLocked(true);
        cell.setCellStyle(style);


    }
}
