package cn.jdbc.demo;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author qianfanguojin
 * 简单的 JDBC 示例
 */

public class simpleDemo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.注册驱动,告诉程序要连接哪个数据库.
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取连接对象，和数据库牵个手，建立联系。
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hk1","root","987075");
        //3.组装SQL语句，确定要对数据库的操作。
        String sql = "update user set user = '小李' where user = '小明'";
        //4.获取SQL语句执行对象，向数据库要执行sql语句的能力。
        Statement stm = connection.createStatement();
        //5.执行SQL语句，执行组装好的SQL语句。
        int result = stm.executeUpdate(sql);
        //6.对执行SQL的结果进行处理，确定SQL语句执行正确。
        System.out.println(result);
        //7.释放连接对象，和数据库分手。
        stm.close();
        connection.close();

    }
}
