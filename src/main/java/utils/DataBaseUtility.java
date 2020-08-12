package utils;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DataBaseUtility {
    private static BasicDataSource dataSource;
    public static BasicDataSource getDataSource() throws IOException {
        if(dataSource == null){
            BasicDataSource ds = new BasicDataSource();
            try(InputStream inputStream = BasicDataSource.class.getClassLoader().getResourceAsStream("config.properties")){
                Properties properties = new Properties();
                properties.load(inputStream);
                ds.setUrl(properties.getProperty("db.url"));
                ds.setUsername(properties.getProperty("db.user"));
                ds.setPassword(properties.getProperty("db.password"));
                ds.setDriverClassName("org.postgresql.Driver");
                ds.setMinIdle(5);
                ds.setMaxIdle(10);
                ds.setMaxOpenPreparedStatements(100);
                dataSource = ds;
            }
            catch (IOException ex){
                ex.printStackTrace();
                throw ex;
            }
        }
        return dataSource;
    }
}
