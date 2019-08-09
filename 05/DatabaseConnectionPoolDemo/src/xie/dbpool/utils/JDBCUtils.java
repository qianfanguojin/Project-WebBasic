package xie.dbpool.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qianfanguojin
 * 引入Druid数据库连接池后的JDBC工具类
 */
public class JDBCUtils {
    //数据库连接池引用
    private static DataSource dataSource;

    /**
     * 静态代码块，负责连接池的初始化操作
     */
    static {
        Properties pro = null;
        InputStream in = null;
        try {
            //1.加载配置文件，从中获取连接池的属性配置
            pro = new Properties();
            in = JDBCUtils.class.getClassLoader()
                    .getResourceAsStream("druid.properties");
            pro.load(in);

            //2.建立Druid数据库连接池并指定配置
            dataSource = DruidDataSourceFactory.createDataSource(pro);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取连接池
     * @return 连接池的引用
     */
    public static DataSource getDataSource() {
        return dataSource;
    }


    /**
     * 从连接池中获取一个连接
     * @return 连接池中的一个连接
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }


    /**
     * 关闭资源，连接对象会被归还进连接池
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn){
        //关闭结果集对象
        //释放前应该判断对象是否为空，不为空再执行关闭操作，避免空指针异常
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //关闭数据库操作对象
        if(pst != null){
            try{
                pst.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        //关闭本次连接，连接对象退回连接池
        if(conn != null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭资源，连接对象会被归还进连接池
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }

}
