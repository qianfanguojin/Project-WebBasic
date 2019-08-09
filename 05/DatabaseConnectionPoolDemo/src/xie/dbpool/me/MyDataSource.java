package xie.dbpool.me;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * @author qianfanguojin
 * 自己简单设计的一个数据库连接池
 * 须实现DataSource接口
 */
public class MyDataSource implements DataSource {

        //使用链表来存放多个Connection对象
        private LinkedList<Connection> connectionLinkedList = new LinkedList<Connection>();


        /**
         * 初始化多个连接对象
         */
        public MyDataSource(){
            //初始化十个连接对象，并将其放到链表中
            for (int i = 0 ; i < 10 ; i++ ) {
                try {
                    //注册驱动
                    Class.forName("com.mysql.jdbc.Driver");
                    //获取连接对象
                    Connection connection = DriverManager.
                            getConnection("jdbc:mysql://localhost:3306/hk1", "root", "987075");
                    //将连接对象放到链表（池子）中
                    connectionLinkedList.add(connection);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }


        /**
         * 获得连接池中的一个连接对象
         * @return
         * @throws SQLException
         */
        @Override
        public Connection getConnection() throws SQLException {
            //返回第一个连接对象，并将该对象从链表中去除
            return connectionLinkedList.removeFirst();
        }

        /**
         *将使用完的连接对象回收进连接池
         * @param conn
         */
        public void backConnection(Connection conn) {
                connectionLinkedList.add(conn);
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            return null;
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return null;
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return false;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return null;
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {

        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {

        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return 0;
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return null;
        }
}
