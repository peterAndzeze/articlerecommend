package com.article.recommend.constant;

public final class RecommendConstant {
    public  static final  String HDFSBASEPATH="hdfs://hadoop2.campus-card.com:8020/opt/sunwei";
    public static final  String BASEPATH="/opt/sunwei/";//根目录
    /**用户文件夹路径**/
    public static final  String USERINFO_PATH="userinfo";

    /***文章存放路径**/
    public static final String ARTICLE_PATH="articleinfo";
    /***用户操作历史***/
    public static final String USER_HAND_HISTORY_PATH="userhandhistory";
    /** hdfs 执行数据导入后生成的默认文件**/
    public static  final  String HDFS_SUCCESS_FILE="part-00000";
    /***文章数据类型***/
    public static  final String  DB_ARTICLE_TYPE="0";
    /***用户数据类型***/
    public static  final String DB_USER_INFO_TYPE="1";
    /****用户操作历史数据类型******/
    public static  final String DB_USER_HISTORY_TYPE="2";
}
