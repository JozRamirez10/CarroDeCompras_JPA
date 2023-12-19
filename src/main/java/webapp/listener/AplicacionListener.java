package webapp.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import webapp.models.Carro;

// ServletContextListener -> Listener de la aplicación en general
// ServletRequestListener -> Listener para los request
// HttpSessionListener -> Listener para las sessions

@WebListener
public class AplicacionListener implements ServletContextListener, ServletRequestListener, HttpSessionListener {

    private ServletContext servletcontext;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().log("Inicializando la aplicación"); // log() -> Muestra por consola
        servletcontext = sce.getServletContext();
        servletcontext.setAttribute("mensaje", "Algún valor global de la app!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sce.getServletContext().log("Destruyendo la aplicación");
        servletcontext = sce.getServletContext();
    }
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        servletcontext.log("Inicializando el request");
        sre.getServletRequest().setAttribute("mensaje", "Guardando algún valor para el request");
        sre.getServletRequest().setAttribute("title", "Catalogo Servlet");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        servletcontext.log("Destruyendo el request");
    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        servletcontext.log("Inicializando la session http");
        // Por el CDI ya no es necesario declarar las siguientes líneas de código, ya que el mismo CDI
        // con las anotaciones crea la instancia del carro de forma automática al iniciarse una session

        // Cada que se crea una session
        // Carro carro = new Carro(); // Crea un carro
        // HttpSession session = se.getSession();
        // session.setAttribute("carro", carro); // Le da el atributo carro
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        servletcontext.log("Destruyendo la session http");
    }
}
