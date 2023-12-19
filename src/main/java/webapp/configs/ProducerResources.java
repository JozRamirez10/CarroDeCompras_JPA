package webapp.configs;


import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import webapp.util.JpaUtil;


import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@ApplicationScoped
public class ProducerResources {
    @Inject
    private Logger log;

    @Resource(name="jdbc/mysqlDB")
    private DataSource ds;

    // jakarta.enterprise.inject
    @Produces // Método que produce un objeto en el contexto
    @RequestScoped // Contexto, cada vez que se haga un request.
    // @Named("conn") // Cada que sea inyectado se usará el nombre "conn"
    @MysqlConn // Usando @Qualifiers
    private Connection beanConnection() throws NamingException, SQLException {
        //Context initContext = new InitialContext();
        //Context envContext  = (Context)initContext.lookup("java:/comp/env");
        //DataSource ds = (DataSource)envContext.lookup("jdbc/mysqlDB");
        return ds.getConnection();
    }

    @Produces
    private Logger beanLogger(InjectionPoint injectionPoint){ // Este componente se encarga de pasar metadatos de la clase
                                                                // donde se inyecte el logger
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }


    // La conexión se cierra en cada request
    public void closeConnection(@Disposes @MysqlConn Connection connection) throws SQLException {
        connection.close();
        log.info("Cerrando la conexión a la bd mysql");
    }

    @Produces
    @RequestScoped
    private EntityManager beanEntityManager(){
        return JpaUtil.getEntityManager();
    }

    public void close(@Disposes EntityManager entityManager){
        if(entityManager.isOpen()) {
            entityManager.close();
            log.info("Cerrando la conexión del EntityManager");
        }
    }

}
