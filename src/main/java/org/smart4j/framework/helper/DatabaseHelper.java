package org.smart4j.framework.helper;

import com.mysql.jdbc.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 数据库助手
 */
public final class DatabaseHelper {

    {
        Properties conf = PropsUtil.loadProps("config.propertis");
    }
private static final Logger LOGGER= LoggerFactory.getLogger(DatabaseHelper.class);
    /**
     * 开启事务
     */
    public static void beginTransaction(){
        Connection conn=getConnection();
        if(conn!=null){
            try{
                conn.setAutoCommit(false);
            }catch (Exception e){
                LOGGER.error("begin transaction error",e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction(){
        Connection conn = getConnection();
        if(conn != null){
            try{
                conn.commit();
                conn.close();
            }catch (Exception e){
                LOGGER.error("commit transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction(){
        Connection conn = getConnnection();
        if(conn != null){
            try {
                conn.rollback();
                conn.close();
            }catch (SQLException e){
                LOGGER.error("rollback transaction failure",e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }
}
