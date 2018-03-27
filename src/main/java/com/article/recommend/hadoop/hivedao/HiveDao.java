package com.article.recommend.hadoop.hivedao;

import com.alibaba.fastjson.JSON;
import com.article.recommend.dbsourcemanager.HiveProperties;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;

/**
 * hive链接工具类
 *
 * 暂时不考虑资源消耗、并发执行等问题
 *
 */
@Component
public class HiveDao {
    @Autowired
    private  HiveProperties hiveProperties;

    public    Connection getConn() throws SQLException, ClassNotFoundException {
        Connection con = null;
        System.out.println("初始化connection");
        Class.forName("org.apache.hive.jdbc.HiveDriver");
        con = DriverManager.getConnection("jdbc:hive2://"+hiveProperties.getHost()+"/"+hiveProperties.getDatabase(), hiveProperties.getUser(), hiveProperties.getPassword());

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
        stringBuilder.append(") row format delimited fields terminated by '"+splitChar+"'" );
        System.out.println(stringBuilder.toString());
        PreparedStatement stmt=null;
        Connection con=null;
        try {
            con=getConn();
            stmt=con.prepareStatement(stringBuilder.toString());
            stmt.execute();
        }catch (ClassNotFoundException e){
            System.out.println("驱动没有找到:"+e.getMessage());
            e.printStackTrace();
        }catch (SQLException e){
            System.out.println("创建表失败："+e.getMessage());
            e.printStackTrace();
        }finally {
            closeHiveJdbc(con,stmt,null);
        }
    }

    /**
     * 导入数据进入hive
     * @param hdfsFile 数据文件
     * @param table hive表
     * @param type 导入类型（覆盖、追加）
     */
    public void  loadDataFromFile(String hdfsFile,String table ,String type){
        String sql="load data  inpath '"+hdfsFile+"' "+type+" table "+table;
        System.out.println(sql+"**********");
        Connection con=null;
        PreparedStatement statement=null;
        try {
            con = getConn();
            statement=con.prepareStatement(sql);
            statement.execute();
        }catch (Exception e){
            System.out.println("导入数据异常");
            e.printStackTrace();
        }finally {
            closeHiveJdbc(con,statement,null);
        }
    }

    /**
     * 执行数据查询
     * @param sql
     */
    public void queryData(String sql){
        Connection con=null;
        PreparedStatement statement=null;
        ResultSet rs=null;
        try {
            con = getConn();
            statement=con.prepareStatement(sql);
            rs=statement.executeQuery();
            while(rs.next()){
                System.out.println("id:"+rs.getInt("tb_article_info.id")+"title："+rs.getString("tb_article_info.title")+"content"+rs.getString("tb_article_info.content")+"releaseTime"+rs.getString("tb_article_info.release_time")+"article_lables"+rs.getString("tb_article_info.article_lables"));
            }
        }catch (Exception e){
            System.out.println("查询数据异常");
            e.printStackTrace();
        }finally {
            closeHiveJdbc(con,statement,rs);
        }
    }

    /**
     * 删除table
     * @param table
     */
    public void dropTable(String table){
        Connection con=null;
        PreparedStatement statement=null;
        try{
            con=getConn();
            statement=con.prepareStatement("drop table if exists "+table);
            statement.execute();
        }catch (Exception e){
            System.out.println("删除表失败:"+e.getMessage());
            e.printStackTrace();
        }finally {
            closeHiveJdbc(con,statement,null);
        }
    }
    /**
     * 关闭资源
     * @param con
     * @param stmt
     * @param rs
     */
    protected  void closeHiveJdbc(Connection con,PreparedStatement stmt,ResultSet rs){
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

    /**
     * 获取所有表信息
     */
    public void showTables() {
        ResultSet rs = null;
        PreparedStatement stmt=null;
        String sql = "show tables";
        System.out.println("Running: " + sql);
        Connection con=null;
        try {
            con=getConn();
            stmt =con.prepareStatement(sql);
            rs=stmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeHiveJdbc(con,stmt,rs);
        }
    }

   /* public    void initConnection(){
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


    */

    public static void main(String[] args) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("12,q123,");
        System.out.println(stringBuilder.substring(0,stringBuilder.lastIndexOf(",")));
    }
}
