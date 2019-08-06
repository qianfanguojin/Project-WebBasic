package cn.jdbc.demo;

import java.sql.*;

/**
 * @author qianfanguojin
 *使用PrepareStatement 数据库操作对象之后的数据库连接操作
 */
public class PreDemo {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动,告诉程序要连接哪个数据库.
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接对象，和数据库牵个手，建立联系。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hk1","root","987075");
        //3.编写带有占位符 ”?“ 的SQL语句。
        String sql = "select * from user where password = ?";
        //4.获取SQL语句执行对象并传入带有占位符的sql语句。
        PreparedStatement pst = connection.prepareStatement(sql);
        //5. 对占位符进行赋值。
        pst.setString(1,"123456");
        //6.执行SQL语句，执行组装好的SQL语句。
        ResultSet rs = pst.executeQuery();
        //7.对执行SQL的结果进行处理
        while(rs.next()) {
            String user = rs.getString("user");
            String pas = rs.getString("password");
            System.out.println("用户名：" + user + "    密码：" + pas);
        }
        //8.释放连接对象，和数据库分手。
        rs.close();
        pst.close();
        connection.close();

    }
}
