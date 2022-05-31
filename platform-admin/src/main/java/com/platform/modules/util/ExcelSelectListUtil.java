/*
 * 项目名称:platform-plus
 * 类名称:ExcelSelectListUtil.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/8/19 16:54            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * ExcelSelectListUtil
 *
 * @author liuqianru
 * @date 2020/8/19 16:54
 */
public class ExcelSelectListUtil {
    /**
     * firstRow 開始行號 根据此项目，默认为2(下标0开始)
     * lastRow  根据此项目，默认为最大65535
     * firstCol 区域中第一个单元格的列号 (下标0开始)
     * lastCol 区域中最后一个单元格的列号
     * strings 下拉内容
     * */
    public static void selectList(Workbook workbook, int firstCol, int lastCol, String[] strings ){


        Sheet sheet = workbook.getSheetAt(0);
        //  生成下拉列表
        //  只对(x，x)单元格有效
        CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(2, 65535, firstCol, lastCol);
        //  生成下拉框内容
        DVConstraint dvConstraint = DVConstraint.createExplicitListConstraint(strings);
        HSSFDataValidation dataValidation = new HSSFDataValidation(cellRangeAddressList, dvConstraint);
        //  对sheet页生效
        sheet.addValidationData(dataValidation);

    }

    /**
     * POI导出Excel时下拉列表值超过255的问题（String literals in formulas can't be bigger than 255 characters ASCII）
     * @param workbook Excel对象
     * @param sheetName 临时存放下拉值的sheet的名字
     * @param sheetNameIndex 临时存放下拉值的sheet的index
     * @param sheetData 下拉值数组
     * @param firstRow 从第几行开始
     * @param lastRow 到第几行结束
     * @param firstCol 目标下拉框从第几列开始
     * @param lastCol 目标下拉框到第几列结束
     */
    public static void ExcelTo255(Workbook workbook,String sheetName,int sheetNameIndex,String[] sheetData,int firstRow,int lastRow,int firstCol,int lastCol){
        //将下拉框数据放到新的sheet里，然后excle通过新的sheet数据加载下拉框数据
        Sheet hidden = workbook.createSheet(sheetName);

        //创建单元格对象
        Cell cell =null;
        //遍历我们上面的数组，将数据取出来放到新sheet的单元格中
        for (int i = 0, length = sheetData.length; i < length; i++){
            //取出数组中的每个元素
            String name = sheetData[i];
            //根据i创建相应的行对象（说明我们将会把每个元素单独放一行）
            Row row = hidden.createRow(i);
            //创建每一行中的第一个单元格
            cell = row.createCell(0);
            //然后将数组中的元素赋值给这个单元格
            cell.setCellValue(name);
        }
        // 创建名称，可被其他单元格引用
        Name namedCell = workbook.createName();
        namedCell.setNameName(sheetName);
        // 设置名称引用的公式
        namedCell.setRefersToFormula(sheetName+"!$A$1:$A$" + sheetData.length);
        //加载数据,将名称为hidden的sheet中的数据转换为List形式
        DVConstraint constraint = DVConstraint.createFormulaListConstraint(sheetName);

        // 设置第一列的3-65534行为下拉列表
        // (3, 65534, 2, 2) ====> (起始行,结束行,起始列,结束列)
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);
        // 将设置下拉选的位置和数据的对应关系 绑定到一起
        DataValidation dataValidation = new HSSFDataValidation(regions, constraint);

        //将第二个sheet设置为隐藏
        workbook.setSheetHidden(sheetNameIndex, true);
        //将数据赋给下拉列表
        workbook.getSheetAt(0).addValidationData(dataValidation);
    }
}
