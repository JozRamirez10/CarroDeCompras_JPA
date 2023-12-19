package webapp.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import webapp.models.Carro;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/carro/actualizar")
public class ActualizarCarroServlet extends HttpServlet {

    @Inject
    private Carro carro;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Líneas inválidadas por el CDI
        // HttpSession session = req.getSession();
        // if(session.getAttribute("carro") != null){
        //    Carro carro = (Carro) session.getAttribute("carro");
            updateProductos(req, carro);
            updateCantidades(req, carro);
        // }
        resp.sendRedirect(req.getContextPath() + "/carro/ver");
    }

    private void updateProductos(HttpServletRequest req, Carro carro){
        String[] deleteIds = req.getParameterValues("deleteProductos");
        if(deleteIds != null && deleteIds.length > 0){
            List<String> productosIds = Arrays.asList(deleteIds);
            carro.removeProductos(productosIds);
        }
    }

    private void updateCantidades(HttpServletRequest req, Carro carro){
        Enumeration<String> enumer = req.getParameterNames();
        while(enumer.hasMoreElements()){
            String paramName = enumer.nextElement();
            if(paramName.startsWith("cant_")){
                String id = paramName.substring(5);
                String cantidad = req.getParameter(paramName);
                if(cantidad != null)
                    carro.updateCantidad(id, Integer.parseInt(cantidad));
            }
        }
    }
}
