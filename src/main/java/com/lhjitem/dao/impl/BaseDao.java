package com.lhjitem.dao.impl;

import com.lhjitem.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDao {
    //使用dbutils操作数据库，跟spring-jdbc相同作用，都是简化操作代码流程
    //下列可变长参数都是sql对于的属性值
    //因为我们更改为事务管理，让事务来管理异常处理异常，尽管我们能在dao中进行捕获，但我们必须要抛出去
    private QueryRunner queryRunner = new QueryRunner();

    public int update(String sql,Object...args){
        //这个方法用来执行DML，增删改
        //1.连接数据库
        try {
            Connection conn = JdbcUtils.getConnection();
            return queryRunner.update(conn,sql,args);//成功返回影响的行数
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        return -1;//如果返回-1说明执行失败
    }

    public <T> T queryForOne(Class<T> type,String sql,Object...args){
        //这是查询一个JavaBean方法体
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanHandler<T>(type),args);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        return null;
    }

    public <T> List<T> queryForList(Class<T> type, String sql, Object...args) {
        //这是查询多个JavaBean的sql语句方法体
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new BeanListHandler<T>(type),args);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        return null;
    }

    public Object queryForSingleValue(String sql,Object...args){
        //这是执行返回一行一列的sql语句
        Connection conn = JdbcUtils.getConnection();
        try {
            return queryRunner.query(conn,sql,new ScalarHandler(),args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
//        return null;
    }

}
