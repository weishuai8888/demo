package com.weishuai.util.excel;

import com.google.common.collect.Lists;
import com.weishuai.common.exception.BizException;
import com.weishuai.rateLimiter.enums.ExcelSuffixEnum;
import com.weishuai.util.common.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.weishuai.common.exception.SystemExceptionEnum.SYSTEM_IO_EXCEPTION;
import static com.weishuai.common.exception.UserExceptionEnum.USER_REQUIRE_FILE_FORM_NON_MATCH_EXCEPTION;
import static com.weishuai.common.exception.UserExceptionEnum.USER_REQUIRE_FILE_NON_EXIST_EXCEPTION;

/**
 * @Description : 导入excel - poi-ooxml V4.1.2
 * @Author : Future Buddha
 * @Date: 2022-04-14 10:21
 */
@Slf4j
public class ImportExcelUtil {

    /**
     * 导入方法入口
     * @param filePath 导入文件路径地址
     * @return
     * @throws BizException
     */
    public static List<List<String>> importExcel(String filePath) throws BizException {

        Workbook wookbook = getWorkbook(filePath);
        if (Objects.isNull(wookbook)) {
            return Lists.newArrayList();
        }
        List<List<String>> list = Lists.newArrayList();
        int numberOfSheets = wookbook.getNumberOfSheets();
        //遍历sheet - 此例中只有一个sheet
        for (int i = 0; i < numberOfSheets; i++) {
            if (i > 0) {
                break;
            }
            //获取第一个sheet
            Sheet sheet = wookbook.getSheetAt(i);
            if (Objects.isNull(sheet)) {
                continue;
            }
            //获取第一行和最后一行
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            //遍历row并获取改行个单元格的值
            for (int j = firstRowNum; j < lastRowNum; j++) {
                Row row = sheet.getRow(j);
                if (Objects.isNull(row)) {
                    continue;
                }
                List<String> objs = Lists.newArrayList();
                short firstCellNum = row.getFirstCellNum();
                short lastCellNum = row.getLastCellNum();
                for (int m = firstCellNum; m < lastCellNum; m++) {
                    Cell cell = row.getCell(m);
                    String obj = getCellVal(cell);
                    objs.add(obj);
                }
                list.add(objs);
            }
        }
        return list;
    }

    /**
     * 获取该单元格的值
     * @param cell
     * @return
     */
    private static String getCellVal(Cell cell) {
        CellType cellTypeEnum = cell.getCellTypeEnum();
        String value;
        switch (cellTypeEnum) {
            case STRING:
                value =  cell.getStringCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    value = TimeUtil.dateToString(DateUtil.getJavaDate(cell.getNumericCellValue()));
                }else{
                    value = String.valueOf(cell.getNumericCellValue());
                }
                break;
            case BOOLEAN:
                value = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
            default:
                value = "";
        }
        return value;
    }

    /**
     * 创建基于路径下Excel的Workbook对象
     * @param filePath
     * @return
     */
    private static Workbook getWorkbook(String filePath) {
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
        Workbook workbook;
        try {
            if (Objects.equals(ExcelSuffixEnum.XLS.getSuffix(), fileType)) {
                workbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
            } else if (Objects.equals(ExcelSuffixEnum.XLSX.getSuffix(), fileType)) {
                workbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            } else {
                throw new BizException(USER_REQUIRE_FILE_FORM_NON_MATCH_EXCEPTION.detailMsg(filePath));
            }
        } catch (FileNotFoundException e) {
            log.error(USER_REQUIRE_FILE_NON_EXIST_EXCEPTION.getReason(), e);
            throw new BizException(USER_REQUIRE_FILE_NON_EXIST_EXCEPTION);
        } catch (IOException e) {
            log.error(SYSTEM_IO_EXCEPTION.getReason(), e);
            throw new BizException(SYSTEM_IO_EXCEPTION);
        }
        return workbook;
    }

}
