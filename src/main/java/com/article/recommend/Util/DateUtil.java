package com.article.recommend.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtil {
    public static final String DATE="yyyy-MM-dd";
    public static final String DATETIME="yyyy-MM-dd HH:mm:ss";

    /**
     * 获取多少天前日期
     * @param beforeDays 多少天
     * @param dataFormate 日期格式化
     * @return
     */
    public static String getBeforeDate(int beforeDays,String dataFormate){

        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeDays); // 设置为前beforeNum天
        Date d = calendar.getTime();
        return createSimpleDateFormat(dataFormate).format(d);
    }

    /**
     * 字符串转日期
     * @param dateStr
     * @param patten
     * @return
     */
    public static Date stringToDate(String dateStr,String patten){
        try {
            return createSimpleDateFormat(patten).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转字符串
     * @param date
     * @param patten
     * @return
     */
    public static  String dateToString(Date date,String patten){
        return createSimpleDateFormat(patten).format(date);
    }

    protected  static  SimpleDateFormat createSimpleDateFormat(String dateFormate){
        SimpleDateFormat simpleDateFormat=null;
        if(null==dateFormate || dateFormate.equals("")){
            new SimpleDateFormat(DATE);
        }else{
            simpleDateFormat= new SimpleDateFormat(dateFormate);
        }
        return simpleDateFormat;
    }

    public static void main(String[] args) {
        String date="2018-05-01 13:21:22";
        System.out.println(stringToDate(date,DATETIME));
        System.out.println(dateToString(stringToDate(date,DATETIME),DATETIME));
    }
}
