package webapp.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.configs.ProductoServicePrincipal;
import webapp.models.Carro;
import webapp.models.ItemCarro;
import webapp.models.entities.Producto;
import webapp.service.ProductoService;

import java.io.IOException;
import java.util.Optional;


@WebServlet("/carro/agregar")
public class AgregarCarroServlet extends HttpServlet {

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    // Cada carro esta asociado a una session, por lo que nunca "chocará" con otra session diferente de otro usuario
    @Inject // El carro es inyectado
    private Carro carro;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id")); // Obtiene el parametro id por get
        // Connection conn = (Connection) req.getAttribute("conn");
        // ProductoService service = new ProductoServiceJdbcImpl(conn);
        Optional<Producto> producto = service.porId(id); // Obtiene el producto por id
        if(producto.isPresent()){
            ItemCarro item = new ItemCarro(1, producto.get()); // Crea un producto dentro del carro

            // Las siguientes líneas ya no son necesarias por el cdi, ya que obtiene la session y el carro de forma
            // automática, y en la parte superior con anotaciones indicamos que el carro de esta clase será inyectado,
            // dentro de la variable carro del session

            // HttpSession session = req.getSession(); // Obtiene la session
            // Carro carro = (Carro) session.getAttribute("carro"); // Obtiene el atributo carro

            carro.addItemCarro(item); // Lo guarda dentro de carro (List)
        }
        resp.sendRedirect(req.getContextPath() + "/carro/ver"); // Redirige la página
    }
}
