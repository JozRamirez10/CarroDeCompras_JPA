package webapp.interceptors;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Los interceptores se encargar de hacer puente entre la clase y la inyección en un método, atributo o clase
// Sirven para hacer algun ajuste de configuración o mostrar algún mensaje

@InterceptorBinding // Enlazar clase interceptora con un método
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Logging {
}
