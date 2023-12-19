package webapp.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatosDS {
    private static String url = "jdbc:mysql://localhost:3306/Java_Master_Curso?";
    private static String username = "root";
    private static String password = "";

    // Configuraion para el pool de conexiones de tomcat
    public static Connection getConnection() throws SQLException, NamingException {
        Context initContext = new InitialContext();
        Context envContext  = (Context)initContext.lookup("java:/comp/env");
        DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlDB");
        return ds.getConnection();
    }
}
