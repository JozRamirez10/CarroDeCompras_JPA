package webapp.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.persistence.EntityManager;
import webapp.service.ServiceJdbcException;

import java.util.logging.Logger;

@TransactionalJpa
@Interceptor
public class TransactionalInterceptorJpa {
    @Inject
    private EntityManager em;

    @Inject
    private Logger log;

    @AroundInvoke
    public Object transactional(InvocationContext invocation) throws Exception {
        try{
            log.info("------> Iniciando transaccion " + invocation.getMethod().getName() + " de la clase " +
                    invocation.getMethod().getDeclaringClass());
            em.getTransaction().begin();
            Object resultado = invocation.proceed();
            em.getTransaction().commit();
            log.info("------> Realizando el commit y finalizando la transacción " + invocation.getMethod().getName()
                    + " de la clase " + invocation.getMethod().getDeclaringClass());
            return resultado;
        }catch(ServiceJdbcException e){
            em.getTransaction().rollback();
            throw e;
        }
    }

}
