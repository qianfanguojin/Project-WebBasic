package cn.jdbc.utils;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author qianfanguojin
 * 简单的JDBC工具类
 */
public class JDBCUtils {
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    /**
     *静态代码块，仅在 JDBCUtils 类加载时调用一次
     */
    static {
        try {
            //处理properties配置文件的对象
            Properties pro = new Properties();
            //获取文件输入流
            InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
            //加载文件
            pro.load(in);
            //将获取到数值赋给相应的参数
            URL = pro.getProperty("url");
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("password");
            //注册驱动
            Class.forName(pro.getProperty("driver"));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 返回获取的连接对象
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }


    /**
     * 关闭资源，关闭三个对象
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst,Connection conn){
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

        //关闭连接对象
        if(conn != null){
            try{
                conn.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * 关闭资源，关闭两个对象
     * @param pst
     * @param conn
     */
    public static void close(PreparedStatement pst,Connection conn){
        //调用三参的方法，将第一个参数设为null，间接关闭两个对象
        close(null,pst,conn);
    }
}
