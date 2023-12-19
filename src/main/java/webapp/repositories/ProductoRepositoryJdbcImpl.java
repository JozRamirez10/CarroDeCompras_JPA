package webapp.repositories;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import webapp.configs.MysqlConn;
import webapp.configs.RepositoryStereotype;
import webapp.models.entities.Categoria;
import webapp.models.entities.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

// @ApplicationScoped // Esta clase es compartida con toda la aplicacion
@RepositoryJdbc
@RepositoryStereotype
public class ProductoRepositoryJdbcImpl implements Repository<Producto>{

    @Inject
    private Logger log;

    @Inject
    // @Named("conn")
    @MysqlConn
    private Connection conn;

    // Para inyecciones al inicializar se puede declarar 3 formas:
    // - Inyectar en el método constructor
    // - Inyectar en un atributo para inicializar
    // - Con el método set
    // En este caso la conexión se está inyectando por atributo, por lo que
    // es mejor quitar el constructor

    /*
    public ProductoRepositoryJdbcImpl(Connection conn) {
        this.conn = conn;
    }
    */

    @PostConstruct
    public void inicializar(){
        log.info("Inicializando el beans " + this.getClass().getName());
    }

    @PreDestroy
    public void destroy(){
        log.info("Destuyendo el beans " + this.getClass().getName());
    }

    @Override
    public List<Producto> listar() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select p.*, c.nombre as categoria from producto as p " +
                    " inner join categoria as c on (p.id_categoria = c.id) order by p.id asc")){
            while(rs.next()){
                Producto p = getProducto(rs);
                productos.add(p);
            }
        }
        return productos;
    }

    @Override
    public Producto porId(Long id) throws SQLException {
        Producto producto = null;
        try(PreparedStatement stmt = conn.prepareStatement("select p.*, c.nombre as categoria from producto as p " +
                " inner join categoria as c on (p.id_categoria = c.id) where p.id = ?")){
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next())
                    producto = getProducto(rs);
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql;
        if(producto.getId() != null && producto.getId() > 0){
            sql = "update producto set nombre=?, precio=?, sku=?, id_categoria=? where id=?";
        }else{
            sql = "insert into producto(nombre, precio, sku, id_categoria, fecha_registro) values(?,?,?,?,?)";
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, producto.getNombre());
            stmt.setInt(2, producto.getPrecio());
            stmt.setString(3, producto.getSku());
            stmt.setLong(4, producto.getCategoria().getId());
            if(producto.getId() != null && producto.getId() > 0){
                stmt.setLong(5, producto.getId());
            }else{
                stmt.setDate(5, Date.valueOf(producto.getFechaRegistro()));
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from producto where id = ?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    private Producto getProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getInt("precio"));
        p.setSku(rs.getString("sku"));
        p.setFechaRegistro(rs.getDate("fecha_registro").toLocalDate());
        Categoria c = new Categoria();
        c.setId(rs.getLong("id_categoria"));
        c.setNombre(rs.getString("categoria"));
        p.setCategoria(c);
        return p;
    }
}
