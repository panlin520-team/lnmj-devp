package com.lnmj.account.business;

/**
 * @Auther: panlin
 * @Date: 2020-04-02 10:52
 * @Description:
 */
import java.sql.*;

public class test
{
    public static void main(String[] args) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");

        //一开始必须填一个已经存在的数据库
        String url = "jdbc:mysql://192.168.100.2:3306/lnmj_account?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true";
        Connection conn = DriverManager.getConnection(url, "root", "root");
        Statement stat = conn.createStatement();

        //创建数据库hello
        stat.executeUpdate("create database hello");

        //打开创建的数据库
        stat.close();
        conn.close();
        url = "jdbc:mysql://192.168.100.2:3306/hello?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowMultiQueries=true";
        conn = DriverManager.getConnection(url, "root", "root");
        stat = conn.createStatement();

        //创建表test
        stat.executeUpdate("create table test(id int, name varchar(80))");

        //添加数据
        stat.executeUpdate("insert into test values(1, '张三')");
        stat.executeUpdate("insert into test values(2, '李四')");

        //查询数据
        ResultSet result = stat.executeQuery("select * from test");
        while (result.next())
        {
            System.out.println(result.getInt("id") + " " + result.getString("name"));
        }

        //关闭数据库
        result.close();
        stat.close();
        conn.close();
    }
}
