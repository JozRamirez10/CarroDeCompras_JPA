package webapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBaseDatos {
    private static String url = "jdbc:mysql://localhost:3306/Java_Master_Curso?";
    private static String username = "root";
    private static String password = "";

    public static Connection getConnection() throws SQLException {
        /* Si da error al querer conectarse a la base de datos se usa el siguiente código:
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        return DriverManager.getConnection(url, username, password);
    }
}
