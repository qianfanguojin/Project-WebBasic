import org.junit.Test;
import xie.dbpool.me.MyDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库连接池的测试类
 */
public class MyDataSourceTest {

    @Test
    public void test_getConnection(){

        //1.初始化一个连接池对象
        MyDataSource dataSource = new MyDataSource();

        PreparedStatement pst = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            //2.获取连接池中的Connection对象
            conn = dataSource.getConnection();
            //3.编写带有占位符 ”?“ 的SQL语句。
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
            dataSource.backConnection(conn);
        }
    }


}
