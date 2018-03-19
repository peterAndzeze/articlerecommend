package com.article.recommend.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 */
public class DateUtil {
    private static final String DATE="yyyy-MM-dd";
    private static final String DATETIME="yyyy-MM-dd hh24:mm:ss";

    /**
     * 获取多少天前日期
     * @param beforeDays 多少天
     * @param dataFormate 日期格式化
     * @return
     */
    public static String getBeforeDate(int beforeDays,String dataFormate){
        SimpleDateFormat simpleDateFormat=null;
        if(null==dataFormate || dataFormate.equals("")){
            new SimpleDateFormat(DATE);
        }else{
            simpleDateFormat= new SimpleDateFormat(dataFormate);
        }
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeDays); // 设置为前beforeNum天
        Date d = calendar.getTime();
        return "'" + simpleDateFormat.format(d) + "'";
    }

}
