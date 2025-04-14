package com.weishuai.util.excel;

import com.google.common.base.Charsets;
import com.weishuai.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

import static com.weishuai.common.exception.SystemExceptionEnum.SYSTEM_EXPORT_FAIL_EXCEPTION;

/**
 * @Description : 导出Excel工具类 - 前端获取域名用：window.location.origin
 * - https://mvnrepository.com/artifact/org.apache.poi/poi 或 https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml
 * @Author : Future Buddha
 * @Date: 2022-02-26 06:09
 */
@Slf4j
public class ExportExcelUtilXls {


    /**
     * 导出
     *
     * @param response 响应
     */
    public static void export(String excelSuffix, List<List<String>> valList, List<String> rowNames, String titlePrefix, HttpServletResponse response) {
        // 产生表格标题行，设置高度、并填充样式、值
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheetName");
        HSSFRow rows = sheet.createRow(0);
        HSSFCell cellTitle = rows.createCell(0);
        rows.setHeight((short) (25 * 35));
        HSSFCellStyle titleStyle = configStyle(workbook, (short) 15, IndexedColors.PALE_BLUE, FillPatternType.SOLID_FOREGROUND);
        cellTitle.setCellStyle(titleStyle);
        cellTitle.setCellValue("表头标题");
        //设置表格列名范围、填充样式、值
        CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, (rowNames.size() - 1));
        sheet.addMergedRegion(cellRangeAddress);
        HSSFCellStyle columnStyle = configStyle(workbook, (short) 15, IndexedColors.LIGHT_BLUE, FillPatternType.SOLID_FOREGROUND);
        setColumnTopVal(rowNames, sheet, columnStyle);
        //设置sheet内容数据
        HSSFCellStyle cellStyle = configStyle(workbook, (short) 12, IndexedColors.BLACK1, FillPatternType.NO_FILL);
        setVal(valList, sheet, cellStyle);

        //让列宽随着导出的列名列长自动适应
        int columnNum = rowNames.size();
        for (int colNum = 0; colNum < columnNum; colNum++) {
            int columnWidth = sheet.getColumnWidth(colNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }
                if (currentRow.getCell(colNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(colNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            if (colNum == 0) {
                sheet.setColumnWidth(colNum, (columnWidth - 2) * 128);
            } else {
                sheet.setColumnWidth(colNum, (columnWidth + 4) * 256);
            }
        }

        long timeMillis = System.currentTimeMillis();
        String fileName = titlePrefix + "_" + timeMillis + ".xlsx";
        if (Objects.nonNull(response)) {
            //浏览器导出
            try (OutputStream out = response.getOutputStream()) {
                String headStr = "attachment; filename=" + URLEncoder.encode(fileName, Charsets.UTF_8.name());
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", headStr);
                workbook.write(out);
            } catch (IOException e) {
                log.error(SYSTEM_EXPORT_FAIL_EXCEPTION.getReason(), e);
                CommonResult.fail(SYSTEM_EXPORT_FAIL_EXCEPTION);
            }
        } else {
            //下载到本地
            try (FileOutputStream out = new FileOutputStream("/Users/ws/Desktop/" + fileName)) {
                workbook.write(out);
            } catch (Exception e) {
                log.error(SYSTEM_EXPORT_FAIL_EXCEPTION.getReason(), e);
                CommonResult.fail(SYSTEM_EXPORT_FAIL_EXCEPTION);
            }
        }

    }

    private static void setVal(List<List<String>> valList, HSSFSheet sheet, HSSFCellStyle style) {
        for (int i = 0; i < valList.size(); i++) {
            List<String> obj = valList.get(i);
            //创建数据行
            HSSFRow row = sheet.createRow(i + 2);
            //设置高度
            row.setHeight((short) (25 * 20));
            for (int j = 0; j < obj.size(); j++) {
                //设置单元格的数据类型
                HSSFCell cell = row.createCell(j, CellType.STRING);
                if (!CollectionUtils.isEmpty(obj)) {
                    //设置单元格的值
                    cell.setCellValue(obj.get(j));
                }
                //设置单元格样式
                cell.setCellStyle(style);
            }
        }
    }

    private static void setColumnTopVal(List<String> rowNames, HSSFSheet sheet, HSSFCellStyle columnTitleStyle) {
        // 定义所需列数
        int columnNum = rowNames.size();
        // 在索引2的位置创建行(最顶端的行开始的第二行)
        HSSFRow rowRowName = sheet.createRow(1);
        //设置高度
        rowRowName.setHeight((short) (25 * 25));
        for (int n = 0; n < columnNum; n++) {
            //创建列头单元格
            HSSFCell cellRowName = rowRowName.createCell(n);
            //设置列头单元格的数据类型
            cellRowName.setCellType(CellType.STRING);
            HSSFRichTextString text = new HSSFRichTextString(rowNames.get(n));
            //设置列头单元格的值
            cellRowName.setCellValue(text);
            //设置列头单元格样式
            cellRowName.setCellStyle(columnTitleStyle);
        }
    }

    /**
     * 设置列头单元格样式
     */
    public static HSSFCellStyle configStyle(HSSFWorkbook workbook, short fontSize, IndexedColors indexColorType, FillPatternType fillPatternType) {
        HSSFFont font = workbook.createFont();
        HSSFCellStyle style = workbook.createCellStyle();
        setPublicStyle(font, fontSize, style);
        //设置单元格背景颜色
        style.setFillForegroundColor(indexColorType.getIndex());
        style.setFillPattern(fillPatternType);

        return style;

    }


    private static void setPublicStyle(HSSFFont font, short fontSize, HSSFCellStyle style) {
        //设置字体大小
        font.setFontHeightInPoints(fontSize);
        final String fontName = "Courier New";
        //字体加粗
        font.setBold(Boolean.TRUE);
        //设置字体名字
        font.setFontName(fontName);
        //设置样式;
        //设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        //设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        //设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        //设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        //在样式用应用设置的字体;
        style.setFont(font);
        //设置自动换行;
        style.setWrapText(false);
        //设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
    }


}
