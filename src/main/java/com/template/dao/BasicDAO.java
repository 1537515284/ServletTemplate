package com.template.dao;

import com.template.utils.JDBCUtilByDruid;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 开发BasicDAO，是其它DAO的父类
 */
public class BasicDAO<T> {   // 泛型指定具体类型
    private QueryRunner qr = new QueryRunner();

    //开发通用的dml方法，针对任意的表
    public int update(String sql, Object... params) {
        Connection connection = null;

        try {
            connection = JDBCUtilByDruid.getConnection();
            int update = qr.update(connection, sql, params);
            return update;
        } catch (SQLException e) {
            // 将编译异常转成运行异常并抛出
            throw new RuntimeException(e);
        } finally {
            JDBCUtilByDruid.close(null, null, connection);
        }
    }

    //返回多个对象(即查询结果是多行)，针对任意表
    /**
     * @param sql    sql语句，可以有 ？
     * @param cls    传入一个类的Class对象 比如Actor.class
     * @param params 传入 ？ 的具体的值，可以是多个
     * @return 根据Actor.class 返回对应的 ArrayList集合
     */
    public List<T> queryMulti(String sql, Class<T> cls, Object... params) {
        Connection connection = null;

        try {
            connection = JDBCUtilByDruid.getConnection();
            return qr.query(connection, sql, new BeanListHandler<>(cls), params);
        } catch (SQLException e) {
            // 将编译异常转成运行异常并抛出
            throw new RuntimeException(e);
        } finally {
            JDBCUtilByDruid.close(null, null, connection);
        }
    }

    // 查询单行结果的通用方法
    public T querySingle(String sql,Class<T> cls,Object... params){
        Connection connection = null;

        try {
            connection = JDBCUtilByDruid.getConnection();
            return qr.query(connection, sql, new BeanHandler<>(cls), params);
        } catch (SQLException e) {
            // 将编译异常转成运行异常并抛出
            throw new RuntimeException(e);
        } finally {
            JDBCUtilByDruid.close(null, null, connection);
        }
    }

    // 查询单行单列的方法，即返回单值的方法
    public Object queryScalar(String sql,Object... params){
        Connection connection = null;

        try {
            connection = JDBCUtilByDruid.getConnection();
            return qr.query(connection, sql, new ScalarHandler(), params);
        } catch (SQLException e) {
            // 将编译异常转成运行异常并抛出
            throw new RuntimeException(e);
        } finally {
            JDBCUtilByDruid.close(null, null, connection);
        }
    }
}
