package com.jzh.basemodule.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * <p>日期帮助类<p>
 *
 * @author jinzhenhua
 * @version 1.0 , create at 2020/4/8 16:51
 */
public class DateUtil {

    public static final String FORMAT_TYPE_ONE = "yyyyMMdd";
    public static final String FORMAT_TYPE_TWO = "yyMMdd";
    public static final String FORMAT_TYPE_THREE = "yyyy/MM/dd";
    public static final String FORMAT_TYPE_FOUR = "yyyy-MM-dd";
    public static final String FORMAT_TYPE_FIVE = "yyyyMMddHHmmss";
    public static final String FORMAT_TYPE_SIX = "yyyyMMddHHmm";
    public static final String FORMAT_TYPE_SEVEN = "yyMMddHHmmss";
    public static final String FORMAT_TYPE_EIGHT = "yyyy/MM/dd HH:mm:ss";
    public static final String FORMAT_TYPE_NINE = "HH:mm";
    public static final String FORMAT_TYPE_TEN = "HH:mm:ss";

    public static final String FORMAT_TYPE_ELEVEN = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_TYPE_TWELVE = "yyyy-MM-dd HH:mm";

    private DateUtil() {
    }

    /**
     * 获取当前时间
     *
     * @return date
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 按照格式转换当前时间
     *
     * @param format 格式{@link DateUtil}
     * @return 转换后的时间格式字符串
     */
    public static String getDateStyle(String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(getNowDate());
    }

    /**
     * 将毫秒时间转成指定格式
     *
     * @param format     格式
     * @param timeMillis 时间，毫秒数
     * @return 格式化的时间字符串
     */
    public static String getDateStyleByTimeMillis(String format, long timeMillis) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.format(new Date(timeMillis));
    }

    /**
     * 将时间字符串转换成date格式
     *
     * @param format  源格式，如：yyyy-MM-dd HH:mm:ss
     * @param strDate 时间
     * @return 格式化的时间字符串
     */
    public static Date strFormatDate(String format, String strDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        return dateFormat.parse(strDate);
    }

    /**
     * 转换时间格式
     * 如：源时间格式是2020-09-12 10:20:46  转换成2020/09/12 10:20:46 等等
     *
     * @param srcFormat 源时间格式
     * @param srcDate   源时间数据
     * @param tagFormat 转换的目标格式
     * @return 转换后的时间格式
     */
    public static String transitionDateFormat(String srcFormat, String srcDate, String tagFormat) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(srcFormat, Locale.CHINA);
        Date transitionDate = dateFormat.parse(srcDate);
        return new SimpleDateFormat(tagFormat, Locale.CHINA).format(transitionDate);
    }

    /**
     * 校验本地时间和服务端时间是否一致
     *
     * @param localTime    手机本地时间
     * @param serverTime   服务端时间
     * @param differSecond 表示相差多少秒内算是时间一致，单位：秒
     * @return true-时间正确
     */
    public static boolean verifyLocalTime(String format, long localTime, String serverTime, int differSecond) throws ParseException {
        if (localTime <= 0 || serverTime == null || serverTime.isEmpty()) {
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        Date serverDate = dateFormat.parse(serverTime);
        long differTime = Math.abs(localTime - serverDate.getTime());
        return differTime / 1000 <= differSecond;
    }

    /**
     * 获取两个时间的时间段，
     * 如：2020-09-07 12:00 和 2020-09-07 15:00
     *
     * @return 获取 12:00-15:00
     */
    public static String getTimeQuantum(String startTime, String endTime) throws ParseException {
        String planTime = DateUtil.transitionDateFormat(DateUtil.FORMAT_TYPE_TWELVE, startTime, DateUtil.FORMAT_TYPE_FOUR);
        String begTimeQuantum = DateUtil.transitionDateFormat(DateUtil.FORMAT_TYPE_TWELVE, startTime, DateUtil.FORMAT_TYPE_NINE);
        String endTimeQuantum = DateUtil.transitionDateFormat(DateUtil.FORMAT_TYPE_TWELVE, endTime, DateUtil.FORMAT_TYPE_NINE);
        return planTime + "(" + begTimeQuantum + "-" + endTimeQuantum + ")";
    }

    /**
     * 获取日期的时间差
     *
     * @return -2:前天  -1：昨天 0：今天   1：明天  2：后天
     */
    public static int getDateQuantum(String date, String format) throws ParseException {
        Date startDate = DateUtil.strFormatDate(format, date);
        int offSet = Calendar.getInstance().getTimeZone().getRawOffset();
        //转换成天数
        long today = (System.currentTimeMillis() + offSet) / 86400000;
        long start = (startDate.getTime() + offSet) / 86400000;
        System.out.println(start - today);
        return (int) (start - today);
    }

    /**
     * 判断时间是否大于今天
     *
     * @param date   时间字符串
     * @param format 参数date对应的时间格式
     * @return true-日期大于今天，可认定是明天及以后时间
     */
    public static boolean isGreaterThanToday(String date, String format) throws ParseException {
        return getDateQuantum(date, format) > 0;
    }

    /**
     * 判断是否是今天
     *
     * @param date   时间字符串
     * @param format 参数date对应的时间格式
     * @return true-是今天
     */
    public static boolean isToday(String date, String format) throws ParseException {
        return getDateQuantum(date, format) == 0;
    }

    /**
     * 获取明天的时间
     * @param format 时间字符串
     * @return
     */
    public static String getTomorrow(String format){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getNowDate());
        calendar.add(Calendar.DAY_OF_MONTH, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);

        return dateFormat.format(calendar.getTime());
    }
}
