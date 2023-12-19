package webapp.interceptors;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.logging.Logger;

// Para usar el interceptor se tiene que declarar en beans.xml

@Logging
@Interceptor
public class LoggingInterceptor {
    @Inject
    private Logger log;

    @AroundInvoke
    public Object logging(InvocationContext invocation) throws Exception { // Obtiene todos los datos de la clase que esta interceptando
        log.info("**** Entrando antes de invocar el método " + invocation.getMethod().getName() + " de la clase " +
                invocation.getMethod().getDeclaringClass());
        Object resultado = invocation.proceed();
        log.info("**** Saliendo de la invocación del método " + invocation.getMethod().getName());
        return resultado;
    }
}
