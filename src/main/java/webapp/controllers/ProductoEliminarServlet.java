package webapp.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.configs.ProductoServicePrincipal;
import webapp.models.entities.Producto;
import webapp.service.ProductoService;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/productos/eliminar")
public class ProductoEliminarServlet extends HttpServlet {
    @Inject
    @ProductoServicePrincipal
    private ProductoService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Connection conn = (Connection) req.getAttribute("conn");
        // ProductoService service = new ProductoServiceJdbcImpl(conn);
        long id;
        try{
            id = Long.parseLong(req.getParameter("id"));
        }catch (NumberFormatException e){
            id = 0L;
        }
        if(id > 0){
            Optional<Producto> o = service.porId(id);
            if(o.isPresent()){
                service.eliminar(id);
                resp.sendRedirect(req.getContextPath() + "/productos");
            }else{
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "No existe el producto");
            }
        }else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Error: El id se debe enviar como parámetro en la url");
        }
    }
}
