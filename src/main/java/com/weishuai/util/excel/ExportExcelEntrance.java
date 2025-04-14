package com.weishuai.util.excel;

import com.google.common.collect.Lists;
import com.weishuai.common.CommonResult;
import com.weishuai.rateLimiter.entity.dto.ExampleDataDTO;
import com.weishuai.rateLimiter.entity.dto.ExampleParamsDTO;
import com.weishuai.rateLimiter.enums.ExcelSuffixEnum;
import com.weishuai.util.common.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.weishuai.common.exception.SystemExceptionEnum.SYSTEM_DATA_NON_EXIST_EXCEPTION;
import static com.weishuai.common.exception.SystemExceptionEnum.SYSTEM_REQUIRED_PARAM_NON_RIGHTFUL_EXCEPTION;

/**
 * @Description : 导出入口
 * @Author : Future Buddha
 * @Date: 2022-02-26 06:09
 */
@Slf4j
public class ExportExcelEntrance {

    public static void main(String[] args) {
        final String title = "导出demo";
        ExampleDataDTO.Builder builder = ExampleDataDTO.Builder.of();
        builder.createTimeFormat(TimeUtil.localDateTimeToString(LocalDateTime.now())).personName("personName")
                .businessPersonName("businessName").midPersonName("midPersonName").financialName("financialName").legalName("legalName");
        ArrayList<ExampleDataDTO> list = Lists.newArrayList();
        list.add(builder.build());
        list.add(builder.build());
        ExampleParamsDTO.Builder b = ExampleParamsDTO.Builder.of();
        b.rowNames(getRowNames()).sheetNum(1).title(title).voList(list);
        CommonResult result = exportData(b.build(), "xls", null);
        System.out.println(result.toString());
    }

    /**
     * 设置表头
     * @return
     */
    private static List<String> getRowNames() {
        ArrayList<String> list = Lists.newArrayList();
        list.add("日期");
        list.add("店铺开设人店铺开设人店铺开设人店铺开设人店铺开设人");
        list.add("业务人员");
        list.add("中台人员");
        list.add("财务人员");
        list.add("法务人员");
        return list;
    }

    /**
     *
     * @param exampleParamsDTO
     * @param excelSuffix
     * @param response
     * @return
     */
    public static CommonResult exportData(ExampleParamsDTO exampleParamsDTO, String excelSuffix, HttpServletResponse response) {
        if (!ExcelSuffixEnum.checkSuffix(excelSuffix)) {
            log.error(SYSTEM_REQUIRED_PARAM_NON_RIGHTFUL_EXCEPTION.getReason() + ": excelSuffix=[{}]", excelSuffix);
            CommonResult.fail(SYSTEM_REQUIRED_PARAM_NON_RIGHTFUL_EXCEPTION);
        }
        List<ExampleDataDTO> voList = exampleParamsDTO.getVoList();
        if (CollectionUtils.isEmpty(voList)) {
            CommonResult.fail(SYSTEM_DATA_NON_EXIST_EXCEPTION);
        }
        List<List<String>> valList = Lists.newArrayList();
        voList.forEach(vo -> {
            List<String> list = ExportExcelEntrance.entityToList(vo);
            valList.add(list);
        });
        Integer sheetNum = exampleParamsDTO.getSheetNum();
        if (Objects.equals(ExcelSuffixEnum.XLS.getSuffix(), excelSuffix)) {
            if (Objects.nonNull(sheetNum)) {
                for (int i = 0; i < sheetNum; i++) {
                    String title = exampleParamsDTO.getTitle() + "_" + i;
                    HSSFWorkbook workbook = new HSSFWorkbook();
                    //创建工作簿
                    HSSFSheet sheet = workbook.createSheet(title);
                    ExportExcelUtilXls.export(ExcelSuffixEnum.XLS.getSuffix(), valList, getRowNames(), "模板", response);
                }
            }
        }
        if (Objects.equals(ExcelSuffixEnum.XLSX.getSuffix(), excelSuffix)) {
            if (Objects.nonNull(sheetNum)) {
                for (int i = 0; i < sheetNum; i++) {
                    String title = exampleParamsDTO.getTitle() + i;
                    XSSFWorkbook workbook = new XSSFWorkbook();
                    //创建工作簿
                    XSSFSheet sheet = workbook.createSheet(title);
                    ExportExcelUtilXlsx.export(ExcelSuffixEnum.XLSX.getSuffix(), valList, getRowNames(), "模板", response);
                }
            }
        }
        return CommonResult.success();
    }



    public static List<String> entityToList(ExampleDataDTO vo) {
        List<String> list = Lists.newArrayList();
        list.add(vo.getCreateTimeFormat());
        list.add(vo.getBusinessPersonName());
        list.add(vo.getMidPersonName());
        list.add(vo.getFinancialName());
        list.add(vo.getLegalName());
        list.add(vo.getPersonName());
        return list;
    }
}
