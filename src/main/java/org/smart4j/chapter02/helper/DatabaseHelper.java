package org.smart4j.chapter02.helper;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter02.Utils.CollectionUtil;
import org.smart4j.chapter02.Utils.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库操作助手类
 * @author Garwen
 * @date 2019-11-28
 */
public class DatabaseHelper {
    private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);
    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    private static final BasicDataSource DATA_SOURCE ;

    static{
        QUERY_RUNNER = new QueryRunner();
        CONNECTION_HOLDER = new ThreadLocal<>();
//        Properties conf = PropsUtil.loadProps("config.properties");
        Properties conf = new Properties();
        conf.setProperty("jdbc.driver", "com.mysql.cj.jdbc.Driver");
        conf.setProperty("jdbc.url", "jdbc:mysql://104.199.203.155:3306/demo");
        conf.setProperty("jdbc.username", "smart4j");
        conf.setProperty("jdbc.password", "Ztw6020485!");

        String driver=PropsUtil.getString(conf, "jdbc.driver");
        String url=PropsUtil.getString(conf, "jdbc.url");
        String username=PropsUtil.getString(conf, "jdbc.username");
        String password=PropsUtil.getString(conf, "jdbc.password");

        //add apache common dbcp pool
        DATA_SOURCE = new BasicDataSource();
        DATA_SOURCE.setDriverClassName(driver);
        DATA_SOURCE.setUrl(url);
        DATA_SOURCE.setUsername(username);
        DATA_SOURCE.setPassword(password);
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
                conn = DATA_SOURCE.getConnection();
                CONNECTION_HOLDER.set(conn);
            } catch (SQLException e) {
                LOGGER.error("Get Connection Failure ", e);
            }
        }
        return conn;
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
        }
        return entityList;
    }

    /**
     *执行实体查询
     *@creator Garwen
     *@date 2019-11-29 9:17
     *@param entityClass
     *@param sql
     *@param params
     *@return T
     *@throws
     */
    public static <T> T queryEntity(Class<T> entityClass, String sql, Object... params){
        T entity;
        Connection conn = getConnection();
        try {
            entity = QUERY_RUNNER.query(conn, sql, new BeanHandler<T>(entityClass), params);
        } catch (SQLException e) {
            LOGGER.error("Query Entity failure ", e);
            throw new RuntimeException(e);
        }
        return entity;
    }

    /**
     * 执行查询语句
     *@author Garwen
     *@date 2019/11/28 21:16
     *@params [sql, params]
     *@return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     *@throws
     */
    public static List<Map<String, Object>> executeQuery(String sql, Object... params){
        List<Map<String, Object>> result;
        try {
            Connection conn = getConnection();
            result = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("QUERY EXECUTED FAILURE ", e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 执行更新语句
     *@author Garwen
     *@date 2019/11/28 21:28
     *@params [sql, params]
     *@return int
     *@throws
     */
    public static int executeUpdate(String sql, Object... params){
        int rows;
        try {
            Connection conn = getConnection();
            rows = QUERY_RUNNER.update(conn, sql, params);
        } catch (SQLException e) {
            LOGGER.error("Execute update failure ", e);
            throw new RuntimeException(e);
        }
        return rows;
    }

    /**
     * 插入实体
     *@author Garwen
     *@date 2019/11/28 22:00
     *@params [entityClass, filedMap]
     *@return boolean
     *@throws
     */
    public static <T> boolean insertEntity(Class<T> entityClass, Map<String, Object> filedMap){
        if(CollectionUtil.isEmpty(filedMap)){
            LOGGER.error("can not insert entity: fieldMap is empty.");
            return false;
        }

        String sql = "INSERT INTO " + getTableName(entityClass) +" ";
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for(String item:filedMap.keySet()){
            columns.append(item).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf(", "), columns.length(), ")");
        sql += columns + " VALUES " + values;

        Object[] params=filedMap.values().toArray();
        return 1==executeUpdate(sql, params);
    }

    /**
     * 更新实体
     *@author Garwen
     *@date 2019/11/28 22:29
     *@params [entityClass, id, fieldMap]
     *@return boolean
     *@throws
     */
    public static <T> boolean updateEntity(Class<T> entityClass, long id, Map<String, Object> fieldMap){
        if(CollectionUtil.isEmpty(fieldMap)){
            LOGGER.error("Update Entity failure: fieldMap is Empty.");
            return false;
        }

        String sql = "UPDATE " + getTableName(entityClass) + " SET ";
        StringBuilder columns = new StringBuilder();
        for(String item : fieldMap.keySet()){
            columns.append(item).append("=?, ");
        }
        sql += columns.substring(0, columns.lastIndexOf(", ")) + "WHERE id=?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        return executeUpdate(sql, paramList.toArray())==1;
    }

    /**
     *删除实体
     *@author Garwen
     *@date 2019/11/28 22:37
     *@params [entityClass, id]
     *@return boolean
     *@throws
     */
    public static <T> boolean deleteEntity(Class<T> entityClass, long id){
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id=?";
        return 1== executeUpdate(sql, id);
    }

    /**
     * 获取实体表名
     *@author Garwen
     *@date 2019/11/28 21:39
     *@params [entityClass]
     *@return java.lang.String
     *@throws
     */
    private static String getTableName(Class<?> entityClass){
        return entityClass.getSimpleName().toLowerCase();
    }

    /**
     *执行sql文件
     *@creator Garwen
     *@date 2019-11-29 10:36
     *@param filepath
     *@return void
     *@throws
     */
    public static void executeSqlfile(String filepath){
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filepath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        try {
            String sql;
            while((sql=reader.readLine())!=null){
                DatabaseHelper.executeUpdate(sql);
            }
        } catch (IOException e) {
            LOGGER.error("Execute sql failure ", e);
            throw new RuntimeException(e);
        }
    }
}
