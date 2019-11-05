package xie.dbpool.druid;



import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Druid 数据库连接池的简单示例
 */
public class DruidDemo {

    public static void main(String[] args) throws Exception {

        //1.获取配置文件
        Properties pro = new Properties();
        InputStream in = DruidDemo.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(in);
        System.out.println(pro);
        //2.创建数据库连接池对象
        //需要传入properties对象获取其中的配置信息
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);

        //3.获取连接对象
        Connection conn = ds.getConnection();
        System.out.println(conn);

        //4.将连接对象放回连接池
        //使用了连接池时,close方法功能为连接对象退回连接池
        conn.close();
    }
}


