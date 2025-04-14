package com.weishuai.actual.work.doc.first;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @Description Excel试题处理工具类
 * @Author ws
 * @Date 2025/3/13 13:42
 */
public class DocHandle {
    private static final String SOURCE_FILE = "/opt/source/demo/src/main/java/com/weishuai/actual/work/doc/first/试题1124.xlsx";
    private static final String OUTPUT_FILE = "/opt/source/demo/src/main/java/com/weishuai/actual/work/doc/first/试题分类结果.xlsx";

    public static void main(String[] args) {
        try {
            processExcel();
            System.out.println("试题分类完成，请查看输出文件：" + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("处理Excel文件时发生错误：" + e.getMessage());
        }
    }

    /**
     * 处理Excel文件
     */
    public static void processExcel() throws IOException {
        try (FileInputStream fis = new FileInputStream(SOURCE_FILE);
             Workbook sourceWorkbook = new XSSFWorkbook(fis)) {

            Workbook outputWorkbook = new XSSFWorkbook();
            Sheet sourceSheet = sourceWorkbook.getSheetAt(0);
            
            // 获取原文件的表头行
            Row headerRow = sourceSheet.getRow(0);
            int columnCount = headerRow.getLastCellNum();
            
            // 保存表头信息
            List<String> headers = new ArrayList<>();
            List<CellStyle> headerStyles = new ArrayList<>();
            
            // 添加新的序号列表头
            headers.add("序号");
            CellStyle firstHeaderStyle = headerRow.getCell(0).getCellStyle();
            headerStyles.add(firstHeaderStyle);
            
            // 修改原序号列为原题号，并添加其他表头
            for (int i = 0; i < columnCount; i++) {
                Cell cell = headerRow.getCell(i);
                String headerText = cell.getStringCellValue();
                if (i == 0) {
                    headerText = "原题号";
                }
                headers.add(headerText);
                headerStyles.add(cell.getCellStyle());
            }

            // 按问题类型分组存储数据
            Map<String, List<List<Object>>> questionTypeMap = new HashMap<>();
            
            // 读取数据并分组
            Iterator<Row> rowIterator = sourceSheet.iterator();
            rowIterator.next(); // 跳过表头

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String questionType = getCellValue(row.getCell(1)); // 问题类型列
                
                List<Object> rowData = new ArrayList<>();
                // 原数据作为原题号保留
                for (int i = 0; i < columnCount; i++) {
                    Cell cell = row.getCell(i);
                    rowData.add(cell != null ? getCellValueWithType(cell) : "");
                }
                
                questionTypeMap.computeIfAbsent(questionType, k -> new ArrayList<>()).add(rowData);
            }

            // 创建分类后的sheet
            createTypeSheets(outputWorkbook, questionTypeMap, headers, headerStyles);

            // 保存结果
            try (FileOutputStream fos = new FileOutputStream(OUTPUT_FILE)) {
                outputWorkbook.write(fos);
            }
        }
    }

    /**
     * 创建分类Sheet
     */
    private static void createTypeSheets(Workbook workbook, 
                                       Map<String, List<List<Object>>> questionTypeMap,
                                       List<String> headers,
                                       List<CellStyle> headerStyles) {
        // 创建居中样式
        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.setAlignment(HorizontalAlignment.CENTER);
        centerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // 创建自动换行样式
        CellStyle wrapStyle = workbook.createCellStyle();
        wrapStyle.setWrapText(true);
        wrapStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        questionTypeMap.forEach((questionType, questions) -> {
            Sheet sheet = workbook.createSheet(questionType);
            
            // 复制表头及其样式
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers.get(i));
                
                // 复制原表头样式并添加居中对齐
                CellStyle newStyle = workbook.createCellStyle();
                newStyle.cloneStyleFrom(headerStyles.get(i));
                newStyle.setAlignment(HorizontalAlignment.CENTER);
                newStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cell.setCellStyle(newStyle);
            }
            
            // 填充数据
            for (int i = 0; i < questions.size(); i++) {
                Row row = sheet.createRow(i + 1);
                List<Object> rowData = questions.get(i);
                
                // 添加序号列（从1开始）
                Cell serialCell = row.createCell(0);
                serialCell.setCellValue(i + 1);
                serialCell.setCellStyle(centerStyle);
                
                // 填充其他数据
                for (int j = 0; j < rowData.size(); j++) {
                    Cell cell = row.createCell(j + 1);
                    setCellValue(cell, rowData.get(j));
                    
                    // 根据列类型设置不同的样式
                    if (j == 0 || j == 1 || isAnswerColumn(j, headers)) { // 原题号、问题类型、答案列
                        cell.setCellStyle(centerStyle);
                    } else if (isOptionColumn(j, headers)) { // 选项列
                        cell.setCellStyle(wrapStyle);
                    }
                }
            }
            
            // 设置列宽和行高
            adjustColumnWidthAndRowHeight(sheet, headers);
        });
    }

    /**
     * 调整列宽和行高
     */
    private static void adjustColumnWidthAndRowHeight(Sheet sheet, List<String> headers) {
        // 自动调整特定列的宽度
        for (int i = 0; i < headers.size(); i++) {
            if (isAutoWidthColumn(i, headers)) {
                sheet.autoSizeColumn(i);
            } else {
                // 其他列设置固定宽度
                sheet.setColumnWidth(i, 256 * 40); // 40字符宽度
            }
        }

        // 自动调整行高
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                row.setHeight((short)-1); // 自动调整行高
            }
        }
    }

    /**
     * 判断是否是需要自动调整宽度的列
     */
    private static boolean isAutoWidthColumn(int columnIndex, List<String> headers) {
        String header = headers.get(columnIndex);
        return header.equals("序号") ||
               header.equals("原题号") ||
               header.equals("问题类型") ||
               header.contains("答案");
    }

    /**
     * 判断是否是选项列
     */
    private static boolean isOptionColumn(int columnIndex, List<String> headers) {
        if (columnIndex >= headers.size()) {
            return false;
        }
        String header = headers.get(columnIndex);
        return header.startsWith("选项");
    }

    /**
     * 判断是否是答案列
     */
    private static boolean isAnswerColumn(int columnIndex, List<String> headers) {
        if (columnIndex >= headers.size()) {
            return false;
        }
        String header = headers.get(columnIndex);
        return header.contains("答案");
    }

    /**
     * 获取单元格值（保持原始类型）
     */
    private static Object getCellValueWithType(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue();
            case BOOLEAN:
                return cell.getBooleanCellValue();
            default:
                return "";
        }
    }

    /**
     * 获取单元格值（字符串形式）
     */
    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    /**
     * 设置单元格值（保持原始类型）
     */
    private static void setCellValue(Cell cell, Object value) {
        if (value == null) {
            cell.setCellValue("");
        } else if (value instanceof String) {
            String strValue = (String) value;
            try {
                String header = cell.getSheet().getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                if (header.startsWith("选项")) {
                    // 处理选项格式
                    strValue = formatOptions(strValue);
                }
            } catch (Exception e) {
                // 如果获取表头失败，直接使用原值
            }
            cell.setCellValue(strValue);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    /**
     * 格式化选项内容，使每个选项占一行
     */
    private static String formatOptions(String options) {
        if (options == null || options.trim().isEmpty()) {
            return "";
        }

        // 首先将所有分号替换为统一的分隔符
        options = options.replace('；', ';');

        // 分割选项
        String[] optionArray = options.split(";");

        // 构建格式化后的选项
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < optionArray.length; i++) {
            String option = optionArray[i].trim();
            if (!option.isEmpty()) {
                // 确保选项以A、B、C、D开头
                char optionLetter = (char) ('A' + i);
                if (!option.startsWith(optionLetter + ".") && !option.startsWith(optionLetter + "、") && !option.startsWith(optionLetter + " ")) {
                    formatted.append(optionLetter).append(". ");
                }
                formatted.append(option);
                
                // 如果不是最后一个选项，添加换行符
                if (i < optionArray.length - 1) {
                    formatted.append("\n");
                }
            }
        }
        
        return formatted.toString();
    }
}
