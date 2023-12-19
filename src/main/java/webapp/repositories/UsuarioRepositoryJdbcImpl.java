package webapp.repositories;

import jakarta.inject.Inject;
import webapp.configs.MysqlConn;
import webapp.configs.RepositoryStereotype;
import webapp.models.entities.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// @ApplicationScoped
@RepositoryJdbc
@RepositoryStereotype
public class UsuarioRepositoryJdbcImpl implements UsuarioRepository{
    // Inyeccion por atributo
    @Inject
    //@Named("conn")
    @MysqlConn
    private Connection conn;

    /*
    public UsuarioRepositoryJdbcImpl(Connection conn) {
       this.conn = conn;
    }
    */

    @Override
    public List<Usuario> listar() throws SQLException {
        return null;
    }

    @Override
    public Usuario porId(Long id) throws SQLException {
        return null;
    }

    @Override
    public void guardar(Usuario usuario) throws SQLException {

    }

    @Override
    public void eliminar(Long id) throws SQLException {

    }

    @Override
    public Usuario porUsername(String username) throws SQLException {
        Usuario usuario = null;
        try(PreparedStatement stmt = conn.prepareStatement("select * from usuario where username=?")){
            stmt.setString(1, username);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    usuario = getUsuario(rs);
                }
            }
        }
        return usuario;
    }

    private Usuario getUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setUsername(rs.getString("username"));
        usuario.setPassword(rs.getString("password"));
        usuario.setEmail(rs.getString("email"));
        return usuario;
    }
}
