package com.article.recommend.hadoop.util;

import com.article.recommend.dbsourcemanager.HiveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * hive链接工具类
 */
@Service
public class HiveUtil {
    @Autowired
    private  HiveProperties hiveProperties;

    private    Connection con = null;
    private  PreparedStatement stmt = null;
    private  ResultSet rs = null;


    public    Connection getConn() throws SQLException, ClassNotFoundException {
        if(con==null || con.isClosed()){
            System.out.println("初始化connection");
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            con = DriverManager.getConnection("jdbc:hive2://"+hiveProperties.getHost()+"/"+hiveProperties.getDatabase(), hiveProperties.getUser(), hiveProperties.getPassword());
        }
        return con;
    }

    /**
     * 创建表
     * @param table 表名
     * @param columns 列名
     * @param dataType 数据类型
     * @param splitChar 分割符
     */
    public  void createTable(String table,String [] columns ,String [] dataType,String splitChar)  {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("create table " +table+"(");
        StringBuilder clu=new StringBuilder();
        for(int i=0;i<columns.length;i++){
            clu.append(columns[i]+" "+dataType[i]+",");

        }
        stringBuilder.append(clu.substring(0,clu.lastIndexOf(",")));
        stringBuilder.append(") row format delimited fields terminated by '-->'" );
        System.out.println(stringBuilder.toString());
        try {
            stmt=getConn().prepareStatement(stringBuilder.toString());
            stmt.execute();
        }catch (ClassNotFoundException e){
            System.out.println("驱动没有找到:"+e.getMessage());
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("创建表失败："+e.getMessage());
            e.printStackTrace();
        }finally {
            closeHiveJdbc();
        }
    }

    /**
     * 暂时不考虑并发执行
     */
    protected  void closeHiveJdbc(){
        try {
            if (null != con) {
                con.close();
            }
            if(null!=stmt){
                stmt.close();
            }
            if(null!=rs){
                rs.close();
            }
        }catch (Exception e){
            System.out.println("关闭hive jdbc异常："+e.getMessage());
        }
    }
    public void showTables() {
        String sql = "show tables";
        System.out.println("Running: " + sql);
        try {
            stmt = getConn().prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public    void initConnection(){
        try {
            if(con!=null && !con.isClosed()){
                con.close();
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        try {
            Class.forName("org.apache.hive.jdbc.HiveDriver");
            con = DriverManager.getConnection("jdbc:hive2://"+hiveProperties.getHost()+"/"+hiveProperties.getDatabase(), hiveProperties.getUser(), hiveProperties.getPassword());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("12,q123,");
        System.out.println(stringBuilder.substring(0,stringBuilder.lastIndexOf(",")));
    }
}
