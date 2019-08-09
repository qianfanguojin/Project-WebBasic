package xie.dbpool.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author qianfanguojin
 * 测试工具类的使用
 */
public class JDBCUtilsDemo {

    public static void main(String[] args) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        try {
            //1. 从连接池获得连接对象
            conn = JDBCUtils.getConnection();
            //2.构建带占位符的SQL语句
            String sql = "select * from user where password = ?";
            //4.获取SQL语句执行对象并传入带有占位符的sql语句。
            pst = conn.prepareStatement(sql);
            //5. 对占位符进行赋值。
            pst.setString(1,"123456");
            //6.执行SQL语句，执行组装好的SQL语句。
            rs = pst.executeQuery();

            //7.对执行SQL的结果进行处理
            while(rs.next()) {
                String user = rs.getString("user");
                String pas = rs.getString("password");
                System.out.println("用户名：" + user + "    密码：" + pas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //8.关闭资源
            JDBCUtils.close(rs,pst,conn);
        }
    }


}
