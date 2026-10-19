
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Kauan
 */
public class ConnectionFactory {
    private static Properties properties;
    private ConnectionFactory(){        
    }
    
    public static Connection getConnection() throws SQLException, IOException{
        readProperties();
        String url = properties.getProperty("db.url");
        String root = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");
        
        return DriverManager.getConnection(url, root, password);
    }
    private static void readProperties()throws IOException{
        if (properties == null){
            Properties props = new Properties();
            FileInputStream file = new FileInputStream("src/main/java/DataBase.properties");
            props.load(file);
            properties = props;          
        }
    }
}
