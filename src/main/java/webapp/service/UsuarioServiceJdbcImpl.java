package webapp.service;

import jakarta.inject.Inject;
import webapp.configs.ServiceStereotype;
import webapp.interceptors.TransactionalJpa;
import webapp.models.entities.Usuario;
import webapp.repositories.RepositoryJpa;
import webapp.repositories.UsuarioRepository;

import java.sql.SQLException;
import java.util.Optional;

@ServiceStereotype
@TransactionalJpa
public class UsuarioServiceJdbcImpl implements UsuarioService{
    private UsuarioRepository usuarioRepository;

    /*
    public UsuarioServiceJdbcImpl(Connection conn) {
        this.usuarioRepository = new UsuarioRepositoryJdbcImpl(conn);
    }
     */

    // La conexion a la bd se inyecta en UsuarioRepository, por lo que en este constructor
    // pasamos ese parámetro "UsuarioRepository" como inyección
    @Inject
    public UsuarioServiceJdbcImpl(@RepositoryJpa UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        try {
            return Optional.ofNullable(usuarioRepository.porUsername(username))
                    .filter(u -> u.getPassword().equals(password));
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }
}
