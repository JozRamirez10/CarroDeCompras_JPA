package webapp.filters;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import webapp.configs.MysqlConn;
import webapp.service.ServiceJdbcException;
import webapp.util.ConexionBaseDatos;
import webapp.util.ConexionBaseDatosDS;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebFilter("/*") // Se aplica al proyecto completo
public class ConexionFilter implements Filter {
    /*
    @Inject // Vamos a realizar inyecciones
    // @Named("conn") // Con el nombre conn
    @MysqlConn
    private Connection conn;
    */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // try (Connection conn = ConexionBaseDatos.getConnection()){ // Realiza la conexión a la base de datos
        /* try// (// Connection conn = ConexionBaseDatosDS.getConnection()){ // Pool de conexiones
            //Connection connRequest = this.conn) { // Usando CDI
            {
            Connection connRequest = this.conn; // Se inluye dentro de un try normal porque se va cerrar la sesión con una
                                                // anotación
            if(connRequest.getAutoCommit()) // Verifica el autocommit
                connRequest.setAutoCommit(false); // Lo coloca en false
         */
            try{
                // servletRequest.setAttribute("conn", connRequest); // Da como atributo al request la conexión a la base de datos
                filterChain.doFilter(servletRequest, servletResponse); // filtra la conexión
                // connRequest.commit(); // Si sale bien, realiza el commit
            }catch(ServiceJdbcException e){ // En caso de que el request lance una expcetion
                // connRequest.rollback(); // Realiza rollback
                ((HttpServletResponse)servletResponse).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        e.getMessage()); // Muestra el mensaje de error
                throw new RuntimeException(e);
            }
/*        } catch (SQLException e) {
            throw new RuntimeException(e);
        } */
    }
}
