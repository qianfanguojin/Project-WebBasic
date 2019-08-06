package cn.jdbc.demo;


import cn.jdbc.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author qianfanguojin
 * JDBC 事务的简单示例
 */

public class TransactionDemo {



    public static void main(String[] args){
        Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        try {

            //1.注册驱动以及获取数据库连接对象.
            conn = JDBCUtils.getConnection();
            //开启事务
            //默认情况下，所有数据库操作都会被自动提交没执行一条SQL语句就提交一次
            //如果要开启事务就应将其设为false，手动选择提交时间
            conn.setAutoCommit(false);
            //3.编写带有占位符 ”?“ 的SQL语句，并对相应的SQL语句占位符赋值
            //3.1 小明账户减500
            String sql1 = "update user set money = money - ? where user = ?";
            pst1 = conn.prepareStatement(sql1);
            pst1.setInt(1,500);
            pst1.setString(2,"小明");
            //3.2 小李账户加500
            String sql2 = "update user set money = money + ? where user = ?";
            pst2 = conn.prepareStatement(sql2);
            pst2.setInt(1,500);
            pst2.setString(2,"小李");

            //4.执行SQL语句
            int count1 = pst1.executeUpdate();
            int a = 3/0;
            int count2 = pst2.executeUpdate();

            System.out.println(count1);
            System.out.println(count2);

            //提交事务
            //代码运行到这里该次业务操作的逻辑不出任何差错，此时提交事务
            conn.commit();

            // 注意这里catch的异常应该要用范围更大的异常类
            // 这样才能在无论代码中出现什么异常的情况下我们都能抓到然后进行事务回滚
        } catch (Exception e) {
            //事务回滚
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }finally {
            //8.释放连接对象，和数据库分手。
            JDBCUtils.close(pst1,conn);
            JDBCUtils.close(pst2,conn);
        }
    }

}
