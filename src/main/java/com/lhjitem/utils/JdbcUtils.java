package com.lhjitem.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {
    private static DruidDataSource dataSource;
    //使用ThreadLocal(使用静态来声明)来解决filter过滤器中不同连接数据库对象的问题，使之对象相统一
    //统一之后才能使用事务（要么都成功，要么都失败）等功能处理订单异常
    private static ThreadLocal<Connection> conns = new ThreadLocal<>();
    static {
        //2.加载配置文件
        try {
            Properties pro = new Properties();
            pro.load(new FileReader("D:\\FileStore\\src\\main\\java\\druid.properties"));
            dataSource=(DruidDataSource) DruidDataSourceFactory.createDataSource(pro);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //获取数据库连接池中的连接
    public static Connection getConnection(){
        //1.获取ThreadLocal对象的连接池对象，使之统一
        Connection conn = conns.get();
        if(conn == null){
            try {
                conn = dataSource.getConnection();
                //保存到ThreadLocal对象中去，统一jdbc的连接对象
                conns.set(conn);
                //设置事务，调整为手动
                conn.setAutoCommit(false);
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
        }
        return conn;
       /* Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;*/
    }

    /**
     * 提交事务并关闭连接，集成两个功能
     */
    public static void commitAndClose(){
        //得到连接对象
        Connection connection = conns.get();
        if(connection != null){
            //不为null，说明使用过提交数据库，交互过数据，所以我们对事务进行提交
            try {
                connection.commit();

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    //不管成不成功都关闭
                    connection.close();
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要进行remove，因为tomcat底层有线程池的使用
        conns.remove();
    }

    /**
     * 回滚事务并关闭连接，集成两个功能
     */
    public static void rollbackAndClose(){
        //得到连接对象
        Connection connection = conns.get();
        if(connection != null){
            //不为null，说明使用过提交数据库，交互过数据，所以我们对事务进行回滚
            try {
                connection.rollback();

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    //不管成不成功都关闭
                    connection.close();
                } catch (Exception throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        //一定要进行remove，因为tomcat底层有线程池的使用
        conns.remove();
    }

    //下面注释的关闭方法被上面的事务管理所代替


    /*//归还数据库连接池对象,释放资源方法
    public static void close(Connection conn){
        close(conn,null);//优化代码
    }
    public static void close(Connection conn,Statement stat){
        close(conn,null,stat);//优化代码
    }

    public static void close(Connection conn,ResultSet rs, Statement stat) {
        //专门释放资源的函数
        //运用重载的形式再写一个包含ResultSet类的方法，因为增删改并不会用到ResultSet
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat!=null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/
    //5.获取连接池方法
    public static DruidDataSource getDataSource(){
        return dataSource;
    }
}
