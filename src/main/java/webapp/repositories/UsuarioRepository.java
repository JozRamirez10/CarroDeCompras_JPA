package webapp.repositories;

import webapp.models.entities.Usuario;


public interface UsuarioRepository extends Repository<Usuario>{
    Usuario porUsername(String username) throws Exception;
}
