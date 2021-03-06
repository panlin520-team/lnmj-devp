package com.lnmj.k3cloud.comfig;

import java.sql.*;

/**
 * @Auther: panlin
 * @Date: 2020-03-10 14:09
 * @Description:
 */
public class DBUtil {
    //对外提供一个方法来获取数据库连接
    public Connection getConnection(String dataCentreName) {
        Connection conn = null;
        try {
            //1.加载驱动程序
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            //2.获得数据库的连接

          conn = (Connection) DriverManager.getConnection("jdbc:sqlserver://47.108.29.183:1433;DatabaseName=" + dataCentreName, "sa", "SQLSERVER123sql");
          //  conn = (Connection) DriverManager.getConnection("jdbc:sqlserver://192.168.100.2:1433;DatabaseName=" + dataCentreName, "sa", "a123456");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }


    //测试用例
    public static void main(String[] args) throws Exception {
      /*  Connection conn  = this.getConnection(dataCentreId);
        //3.通过数据库的连接操作数据库，实现增删改查
        Statement stmt = conn.createStatement();
        //ResultSet executeQuery(String sqlString)：执行查询数据库的SQL语句   ，返回一个结果集（ResultSet）对象。
        ResultSet rs = stmt.executeQuery("select FDATACENTERID,FNUMBER from T_BAS_DATACENTER where FRUNTASK = 1");
        while (rs.next()) {//如果对象中有数据，就会循环打印出来
            System.out.println(rs.getString("fDATACENTERID") + "-" + rs.getString("fNUMBER"));
        }*/
    }
}
