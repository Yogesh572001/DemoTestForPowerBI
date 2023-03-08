package com.oracle.communications.brm.cc.ws.sql;

import com.portal.pcm.DefaultLog;
import com.portal.pcm.ErrorLog;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jxk305
 */
public class DbConnector {

    private static HikariConfig config = null;
    private static HikariDataSource ds;
    
    private static String username = "";
    private static String password = "";
    private static String finalURL = "";
    
    
    
    private static Properties properties = null;

    
    public static Connection getConnection() throws SQLException {
        
        String homepath = System.getProperty("user.home");
        try {
                //username = System.getenv("BRM_ENV_USER");
                //password = System.getenv("BRM_DB_PIN_PASSWORD_CLEAR");
                //finalURL = "jdbc:oracle:thin:@" + System.getenv("BRM_DB_HOSTNAME") + ":" +System.getenv("BRM_DB_PORT") + ":" + System.getenv("BRM_DB_SERVICENAME");
            if (properties==null){
                    properties = new Properties();
                    properties.load(new FileInputStream(homepath+"/Infranet.properties"));
                username = properties.getProperty("LOGIN");
                password = properties.getProperty("PASS");
                finalURL = properties.getProperty("URL");
            }
            if (config== null){
            config = new HikariConfig();
            config.setJdbcUrl(finalURL);
            config.setUsername( username );
            config.setPassword( password );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            }
            if(ds==null){
            ds = new HikariDataSource( config );
            }
            System.out.println(System.getenv("BRM_DB_HOSTNAME"));
            System.out.println(System.getenv("BRM_DB_PORT"));
            System.out.println(System.getenv("BRM_DB_SERVICENAME"));
            
        } catch (Exception e) {
//            e.printStackTrace();
             DefaultLog.log(ErrorLog.Error,"Connection Failed" + e.getMessage());
                /*if (properties==null){
                    properties = new Properties();
                    properties.load(new FileInputStream(homepath+"/Infranet.properties"));
            username = properties.getProperty("LOGIN");
            password = properties.getProperty("PASS");
            finalURL = properties.getProperty("URL");
            }
            if (config== null){
            config = new HikariConfig();
            config.setJdbcUrl(finalURL);
            config.setUsername( username );
            config.setPassword( password );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            }
            if(ds==null){
            ds = new HikariDataSource( config );
            }
            DefaultLog.log(ErrorLog.Error,"infranet properties file not found" + e.getMessage());*/
        }
        return ds.getConnection();
    }
}