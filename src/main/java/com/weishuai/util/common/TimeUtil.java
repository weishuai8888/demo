package com.weishuai.util.common;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * @Description : 时间转换工具类
 *  - https://www.cnblogs.com/zszxz/p/12255663.html
 * @Author : Future Buddha
 * @Date: 2022-02-26 07:31
 */
public class TimeUtil {

    public static final String FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(FORMAT_TIME);


    /**
     * java.util.Date 转String
     * - 格式化：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        LocalDateTime localDateTime = date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * LocalDateTime 转 时间戳（秒级）
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToTimestampS1(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
    }

    /**
     * LocalDateTime 转 时间戳（秒级）
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToTimestampS2(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).getEpochSecond();
    }

    /**
     * LocalDateTime 转 时间戳（毫秒级）
     * @param localDateTime
     * @return
     */
    public static long localDateTimeToTimestampM(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * 时间戳 转 LocalDateTime（秒级）
     *  - long second = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
     * @param seconds
     * @return
     */
    public static LocalDateTime timestampToLocalDateTimeS(long seconds) {
        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 时间戳 转 LocalDateTime（秒级）
     *  - long milliseconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
     * @param milliseconds
     * @return
     */
    public static LocalDateTime timestampToLocalDateTimeS1(long milliseconds) {
        return LocalDateTime.ofEpochSecond(milliseconds/1000, 0, ZoneOffset.ofHours(8));
    }

    /**
     * 时间戳 转 LocalDateTime（毫秒级）
     *  - 本方式精确值是毫秒级别，故得到的结果会存在三位小数点；
     *  - long milliseconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
     * @param milliseconds
     * @return
     */
    public static LocalDateTime timestampToLocalDateTimeM(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * 时间戳 转 LocalDate（毫秒级）
     *  - long milliseconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
     * @param milliseconds
     * @return
     */
    public static LocalDate timestampToLocalDateM(long milliseconds) {
        return Instant.ofEpochMilli(milliseconds).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    /**
     * 时间戳 转 LocalDate（秒级）
     *  - long seconds = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).getEpochSecond();
     * @param seconds
     * @return
     */
    public static LocalDate timestampToLocalDateS(long seconds) {
        return Instant.ofEpochSecond(seconds).atZone(ZoneOffset.ofHours(8)).toLocalDate();
    }

    /**
     * 时间戳 转 LocalDate（秒级）
     *  - LocalDate localDate = LocalDate.now();
     * @param localDate
     * @return
     */
    public static long localDateToTimestampS(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().getEpochSecond();
    }

    /**
     * 时间戳 转 LocalDate（毫秒级）
     *  - LocalDate localDate = LocalDate.now();
     * @param localDate
     * @return
     */
    public static long localDateToTimestampM(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    /**
     * java.util.Date 转 LocalDateTime (毫秒级)
     *  - Date date = new Date();
     *  - 得出结果是有小数点，毫秒级精确
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTimeM(Date date) {
        return date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDateTime();
    }

    /**
     * java.util.Date 转 LocalDateTime (秒级)
     *  - Date date = new Date();
     * @param date
     * @return
     */
    public static LocalDateTime dateToLocalDateTimeS(Date date) {
        long seconds = date.toInstant().atOffset(ZoneOffset.ofHours(8)).toEpochSecond();
        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.ofHours(8));
    }

    /**
     * LocalDateTime 转 java.util.Date (秒级)
     *  - LocalDateTime localDateTime = LocalDateTime.now();
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDateS(LocalDateTime localDateTime) {
        Instant instant = Instant.ofEpochSecond(localDateTime.toEpochSecond(ZoneOffset.ofHours(8)));
        return Date.from(instant);
    }

    /**
     * LocalDateTime 转 java.util.Date (秒级)
     *  - LocalDateTime localDateTime = LocalDateTime.now();
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant();
        return Date.from(instant);
    }

    /**
     * LocalDate 转 java.util.Date
     *  - LocalDate localDate = LocalDate.now();
     * @param localDate
     * @return
     */
    public static Date localDateToDateS(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay(ZoneOffset.ofHours(8)).toInstant();
        return Date.from(instant);
    }

    /**
     * Date 转 LocalDate
     *  - Date date = new Date();
     * @param date
     * @return
     */
    public static LocalDate localDateToDate(Date date) {
        return date.toInstant().atOffset(ZoneOffset.ofHours(8)).toLocalDate();
    }

    /**
     * LocalDateTime 转 字符串
     *  - LocalDateTime localDateTime = LocalDateTime.now();
     * @param
     * @return
     */
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 字符串 转 LocalDateTime
     * @param
     * @return
     */
    public static LocalDateTime stringToLocalDateTime(String time) {
        return LocalDateTime.parse(time, DATE_TIME_FORMATTER);
    }

    /**
     * 时间戳 转 String
     * @param time
     * @return
     */
    public static String timestampToString(Long time) {
        LocalDateTime localDateTime = TimeUtil.timestampToLocalDateTimeS1(time);
        return TimeUtil.localDateTimeToString(localDateTime);
    }

    public static void main(String[] args) {
        String s = "ant";
        Object obj = "ant";
        boolean b = Objects.equals(s, obj);
        System.out.println(b);
    }


}









































