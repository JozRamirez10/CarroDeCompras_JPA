package webapp.service;

import webapp.models.entities.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Optional<Usuario> login(String username, String password);
}
