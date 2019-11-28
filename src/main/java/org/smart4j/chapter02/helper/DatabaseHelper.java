package org.smart4j.chapter02.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.Utils.PropsUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 数据库操作助手类
 *
 * @Author Garwen
 * @Date 2019-11-28
 */
public class DatabaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();
    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    static{
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER=PropsUtil.getString(conf, "jdbc.driver");
        URL=PropsUtil.getString(conf, "jdbc.url");
        USERNAME=PropsUtil.getString(conf, "jdbc.username");
        PASSWORD=PropsUtil.getString(conf, "jdbc.password");

        try {
            //Driver在此处被初始化
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Can not load jdbc driver " + e);
        }
    }

    /**
     *获取数据库连接
     *@creator Garwen
     *@date 2019-11-28 11:23
     *@param
     *@return java.sql.Connection
     *@throws
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn==null){
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException e) {
                LOGGER.error("Get Connection Failure ", e);
            }
        }
        return conn;
    }

    /**
     *关闭数据库链接
     *@creator Garwen
     *@date 2019-11-28 14:10
     *@return void
     *@throws
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Connection close failure ", e);
                throw new RuntimeException(e);
            } finally{
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     *查询实体列表
     *@creator Garwen
     *@date 2019-11-28 14:22
     *@param entityClass
     *@param sql
     *@param params
     *@return java.util.List<T>
     *@throws
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql, Object... params){
        List<T> entityList;
        Connection conn = getConnection();
        try {
            entityList = QUERY_RUNNER.query(conn, sql, new BeanListHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query Entity List failure ", e);
            throw new RuntimeException(e);
        } finally{
            closeConnection();
        }
        return entityList;
    }

    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        Connection conn = getConnection();
        try {
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass));
        } catch (SQLException e) {
            LOGGER.error("Query Entity failure ", e);
            throw new RuntimeException(e);
        } finally{
            closeConnection();
        }
        return entity;
    }
}
