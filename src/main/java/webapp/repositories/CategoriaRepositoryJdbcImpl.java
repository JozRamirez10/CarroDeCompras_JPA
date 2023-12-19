package webapp.repositories;

import jakarta.inject.Inject;
import webapp.configs.MysqlConn;
import webapp.configs.RepositoryStereotype;
import webapp.models.entities.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// @ApplicationScoped
@RepositoryJdbc
@RepositoryStereotype
public class CategoriaRepositoryJdbcImpl implements Repository<Categoria>{
    private Connection conn;

    // Inyección por método constructor
    @Inject
    //public CategoriaRepositoryJdbcImpl(@Named("conn") Connection conn) {
    public CategoriaRepositoryJdbcImpl(@MysqlConn Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Categoria> listar() throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        try(Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categoria")){
            while(rs.next()){
                categorias.add(getCategoria(rs));
            }
        }
        return categorias;
    }


    @Override
    public Categoria porId(Long id) throws SQLException {
        Categoria categoria = null;
        try(PreparedStatement stmt = conn.prepareStatement("select * from categoria as c where c.id=?")){
            stmt.setLong(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    categoria = getCategoria(rs);
                }
            }
        }
        return categoria;
    }

    @Override
    public void guardar(Categoria categoria) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    private Categoria getCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getLong("id"));
        categoria.setNombre(rs.getString("nombre"));
        return categoria;
    }
}
