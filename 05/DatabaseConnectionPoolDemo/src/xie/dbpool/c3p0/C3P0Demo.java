package xie.dbpool.c3p0;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * C3P0 的简单示例
 */
public class C3P0Demo {
    public static void main(String[] args) {
        //1.创建数据库连接池对象
        //new ComboPoolDataSource()可以传参指定要用的配置 ，该参数对应配置文件中name-config 中name的名称
        //如 new ComboPoolDataSource("mySource")说明我要使用这个配置标签下的内容
        DataSource ds = new ComboPooledDataSource();
        //2.获取连接对象
        try {
            Connection conn = ds.getConnection();
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
