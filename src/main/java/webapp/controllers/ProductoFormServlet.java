package webapp.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import webapp.configs.ProductoServicePrincipal;
import webapp.models.entities.Categoria;
import webapp.models.entities.Producto;
import webapp.service.ProductoService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/form")
public class ProductoFormServlet extends HttpServlet {

    @Inject
    @ProductoServicePrincipal
    private ProductoService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Connection conn = (Connection) req.getAttribute("conn");
        // ProductoService service = new ProductoServiceJdbcImpl(conn);
        // --> Editar producto
        long id;
        try {
            id = Long.parseLong(req.getParameter("id")); // Obtiene el id del producto que se quiere editar y lo valida
        }catch (NumberFormatException e){
            id = 0L;
        }
        Producto producto = new Producto();
        producto.setCategoria(new Categoria());
        if( id > 0){ // Si el id es válido
            Optional<Producto> optionalProducto = service.porId(id); // Busca el producto por id
            if(optionalProducto.isPresent()){ // Si existe
                producto = optionalProducto.get(); // Lo asigna a la variable producto
            }
        }
        req.setAttribute("categorias", service.listarCategorias());
        req.setAttribute("producto", producto); // Lo coloca como un parámetro en el request
        // --> Editar producto
        req.setAttribute("title", req.getAttribute("title") + ": Formulario de productos");
        getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Connection conn = (Connection) req.getAttribute("conn");
        // ProductoService service = new ProductoServiceJdbcImpl(conn);
        String nombre = req.getParameter("nombre");
        Integer precio;
        try{
            precio = Integer.parseInt(req.getParameter("precio"));
        }catch (NumberFormatException e){
            precio = 0;
        }
        String sku = req.getParameter("sku");
        String fechaStr = req.getParameter("fecha_registro");
        Long categoriaId;
        try{
            categoriaId = Long.parseLong(req.getParameter("categoria"));
        }catch (NumberFormatException e){
            categoriaId = 0L;
        }

        Map<String, String> errores = new HashMap<>();
        if(nombre == null || nombre.isBlank())
            errores.put("nombre", "El nombre es requerido");
        if(sku == null || sku.isBlank()) {
            errores.put("sku", "El SKU es requerido");
        }else if(sku.length() > 10){
            errores.put("sku", "El SKU debe tener máximo 10 carácteres");
        }
        if(fechaStr == null || fechaStr.isBlank())
            errores.put("fecha_registro", "La fecha es requerida");
        if(precio.equals(0))
            errores.put("precio", "El precio es requerido");
        if(categoriaId.equals(0L))
            errores.put("categoria", "La categoría es requerida");

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (DateTimeParseException e){
            fecha = null;
        }
        Long id;
        try{
            id = Long.valueOf(req.getParameter("id"));
        }catch(NumberFormatException e){
            id = null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setSku(sku);
        producto.setFechaRegistro(fecha);

        Categoria categoria = new Categoria();
        categoria.setId(categoriaId);
        producto.setCategoria(categoria);
        if(errores.isEmpty()) {
            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos");
        }else{
            req.setAttribute("errores", errores);
            req.setAttribute("categorias", service.listarCategorias());
            req.setAttribute("producto", producto);
            req.setAttribute("title", req.getAttribute("title") + ": Formulario de productos");
            getServletContext().getRequestDispatcher("/form.jsp").forward(req, resp);
        }
    }
}
