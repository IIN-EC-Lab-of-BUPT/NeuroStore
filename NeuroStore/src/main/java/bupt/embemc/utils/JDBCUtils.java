package bupt.embemc.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Author:Jack Chen
 * 2021/12/3
 * description:
 */

@Slf4j
@Component
public class JDBCUtils {

    public static DataSource source;

    @Value("${druidMysqlConfigPath}")
    String configPath;
//ToDo:把这块代码变优雅
//    static {
//
//    }
    public void init(){
        try{
            Properties pros = new Properties();

            InputStream is = new FileInputStream(new File(configPath));
//                    ClassLoader.getSystemClassLoader().getResourceAsStream("application.properties");
//                    new ClassPathResource("application.properties").getInputStream();

            pros.load(is);

            source = DruidDataSourceFactory.createDataSource(pros);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnection() throws SQLException {
        Connection connection = source.getConnection();
        return connection;

    }
    public static void  closeResource(Connection connection, Statement ps){
        try{
            if(ps != null)
                ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        try{
            if(connection != null)
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
