package webapp.filters;

// Los filter se diferencian de los listener al actuar solo en el ciclo de vida del request
// Se puede mapear su uso en las diferentes páginas, de modo que podemos especificar en que
// páginas va actuar el filter y en cuales no, cosa que no se puede realizar con los listener

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.service.LoginService;
import webapp.service.LoginServiceSessionImpl;

import java.io.IOException;
import java.util.Optional;

// En las siguientes rutas es donde se aplicará el WebFilter -> Hará que las páginas sean privadas
// @WebFilter({"/ver-carro", "/agregar-carro", "/actualizar-carro"}) -> Vamos a remplazar estas rutas por la siguiente instrucción
@WebFilter({"/carro/*", "/productos/form/*", "/productos/eliminar/*"}) // Actua sobre todas las rutas dentro de "carro"
public class LoginFilter  implements Filter { // Importar de jakarta.servlet

    // Al iniciarse al request. No es necesario que se implemente
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LoginService service = new LoginServiceSessionImpl();
        Optional<String> username = service.getUsername((HttpServletRequest) servletRequest);
        if(username.isPresent()){ // Si esta autenticado
            filterChain.doFilter(servletRequest, servletResponse); // Continua con la ejecución del servlet (request)
        }else{ // Si no
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, // Da un error
                    "No estas autorizado para ingresar a esta página");
        }
    }

    // Al destruirse el request. No es necesario que se implemente
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
