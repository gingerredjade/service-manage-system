package com.smp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Time工具类
 * @author Hongyu Jiang
 * @since  Apr. 27 2020
 */
public class TimeUtil {

    public static String getStringByDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }


    public static Date getDateByString(String str, String pattern) {
        try {
            DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取当前时间（线程安全）
     *
     * @param date 日期
     * @return 返回时间字符串
     */
    public static String getLocalTimeBySync(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime now = date.toInstant().atZone(zoneId).toLocalDateTime();
        return now.format(formatter);
    }

}
