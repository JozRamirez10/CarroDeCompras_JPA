package webapp.configs;

import jakarta.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Qualifier // Calificadores
@Retention(RetentionPolicy.RUNTIME) // Se va aplicar en tiempo de ejecución
@Target({METHOD, FIELD, PARAMETER, TYPE, CONSTRUCTOR}) // Se va aplicar en todos los campos descritos
public @interface MysqlConn {
}
