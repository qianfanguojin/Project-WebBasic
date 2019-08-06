package cn.jdbc.demo;

import cn.jdbc.utils.JDBCUtils;

import java.sql.*;

public class UtilPreDemo {

    public static void main(String[] args){

        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            //1.注册驱动,告诉程序要连接哪个数据库.
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取连接对象，和数据库牵个手，建立联系。
            connection = JDBCUtils.getConnection();
            //3.编写带有占位符 ”?“ 的SQL语句。
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = connection.prepareStatement(sql);
            //5. 对占位符进行赋值。
            pst.setString(1,"123456");
            //6.执行SQL语句，执行组装好的SQL语句。
            rs = pst.executeQuery();
            //7.对执行SQL的结果进行处理
            while(rs.next()) {
                String user = rs.getString("user");
                String pas = rs.getString("password");
                System.out.println("用户名：" + user + "     密码：" + pas);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            //8.释放连接对象，和数据库分手。
            JDBCUtils.close(rs,pst,connection);
        }
    }

}
