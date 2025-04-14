package com.weishuai.util.file;

import com.google.common.collect.Lists;
//import com.sun.org.slf4j.internal.Logger;
//import com.sun.org.slf4j.internal.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Description : 读文件
 * file(内存)----输入流---->【程序处理】----输出流---->file(内存)
 * @Author : Future Buddha
 * @Date: 2021-09-18 07:07
 */
@Slf4j
public class ReadFile {

//    private static final Logger LOGGER = LoggerFactory.getLogger(ReadFile.class);

    private static final String WE_CHAT = "weChat";
    private static final String ALIPAY = "alipay";
    private static final ArrayList<String> weChatList = Lists.newArrayList("微信支付账单-转账(20180101-20180401).csv",
            "微信支付账单-转账(20180401-20180701).csv", "微信支付账单-转账(20180701-20181001).csv", "微信支付账单-转账(20181001-20190101).csv",
            "微信支付账单-转账(20190101-20190401).csv", "微信支付账单-转账(20190401-20190701).csv", "微信支付账单-转账(20190701-20191001).csv",
            "微信支付账单-转账(20191001-20200101).csv", "微信支付账单-转账(20200101-20200401).csv", "微信支付账单-转账(20200401-20200701).csv",
            "微信支付账单-转账(20200701-20201001).csv", "微信支付账单-转账(20201001-20210101).csv", "微信支付账单-转账(20210101-20210401).csv",
            "微信支付账单-转账(20210401-20210701).csv", "微信支付账单-转账(20210701-20210918).csv");
    private static final ArrayList<String> alipayList = Lists.newArrayList("支付宝20180110-20190101.csv",
            "支付宝20190101-20191201.csv", "支付宝20191201-20200701.csv", "支付宝20200701-20201231.csv", "支付宝20210101-20210918.csv");
    private static final String FILE_PATH = "/Users/ws/Documents/自己/交易记录/转账汇总";
    private static final String WRITE_FILE = FILE_PATH + File.separator + "chuankui.txt";

    public static void main(String[] args) {
        weChatList.forEach(fileName -> {
            String realReadPath = FILE_PATH + File.separator + fileName;
            covertFile(realReadPath, WE_CHAT);
        });

        alipayList.forEach(fileName -> {
            String realReadPath = FILE_PATH + File.separator + fileName;
            covertFile(realReadPath, ALIPAY);
        });
    }

    private static void covertFile(String realReadPath, String source) {
        File writeFile = new File(WRITE_FILE);
        File parentFile = writeFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        if (!writeFile.exists()) {
            try {
                writeFile.createNewFile();
            } catch (IOException e) {
                log.error("无效的文件路径：" + WRITE_FILE);
            }
        }
        InputStreamReader isr = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        int line = 1;
        try {
            if (WE_CHAT.equals(source)) {
                isr = new InputStreamReader(new FileInputStream(realReadPath));
            }else if (ALIPAY.equals(source)) {
                isr = new InputStreamReader(new FileInputStream(realReadPath), "GBK");
            }
            bufferedReader = new BufferedReader(isr);
            bufferedWriter = new BufferedWriter(new FileWriter(writeFile, true));
        } catch (FileNotFoundException e) {
            log.error("文件[" + realReadPath + "]转换为FileReader失败", e);
        } catch (IOException e) {
            log.error("文件[" + realReadPath + "]转换为FileWriter失败", e);
        }
        try {
            String temp = null;
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            // 一次读入一行，直到读入null为文件结束
            while ((temp = bufferedReader.readLine()) != null) {
                if (temp.contains("传奎")) {
                    // 显示行号
                    String row = temp.replace(" ", "") + "\n";
                    System.out.println("文件[" + realReadPath + "]，第[" + line + "]行: " + row);
                    bufferedWriter.append(row);
                }
                line++;
            }
        } catch (IOException e) {
            log.error("文件[" + realReadPath + "]第[" + line + "]行数据读取失败");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("关闭BufferedReader后，再次read(), ready(), mark(), reset(), skip()或BufferedReader已经关闭");
                }
            }

            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    log.error("BufferedWriter已经关闭");
                }
            }

        }
    }
}

























