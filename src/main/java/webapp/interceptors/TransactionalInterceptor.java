package webapp.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import webapp.configs.MysqlConn;
import webapp.service.ServiceJdbcException;

import java.sql.Connection;
import java.util.logging.Logger;

@TransactionalJdbc
@Interceptor
public class TransactionalInterceptor {
    @Inject
    @MysqlConn
    private Connection conn;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception {
        if(conn.getAutoCommit())
            conn.setAutoCommit(false);
        try{
            log.info("------> Iniciando transaccion " + invocation.getMethod().getName() + " de la clase " +
                    invocation.getMethod().getDeclaringClass());
            Object resultado = invocation.proceed();
            conn.commit();
            log.info("------> Realizando el commit y finalizando la transacción " + invocation.getMethod().getName()
                    + " de la clase " + invocation.getMethod().getDeclaringClass());
            return resultado;
        }catch(ServiceJdbcException e){
            conn.rollback();
            throw e;
        }
    }

}
