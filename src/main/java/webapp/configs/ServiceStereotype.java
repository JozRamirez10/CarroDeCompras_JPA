package webapp.configs;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Stereotype;
import jakarta.inject.Named;
import webapp.interceptors.Logging;
import webapp.interceptors.TransactionalJdbc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Logging
@ApplicationScoped
@Stereotype
@Named
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceStereotype {
}
