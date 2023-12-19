package webapp.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Usando stereotype
// @ApplicationScoped
@RequestScoped
@Named
@Stereotype // Agrupa diferentes anotaciones
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // Se va aplicar en una clase
public @interface RepositoryStereotype {
}
