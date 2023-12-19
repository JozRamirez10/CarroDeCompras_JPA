package webapp.service;

import jakarta.inject.Inject;
import webapp.configs.ProductoServicePrincipal;
import webapp.configs.ServiceStereotype;
import webapp.interceptors.TransactionalJpa;
import webapp.models.entities.Categoria;
import webapp.models.entities.Producto;
import webapp.repositories.Repository;
import webapp.repositories.RepositoryJpa;

import java.util.List;
import java.util.Optional;

// @Logging // Clase interceptada, es decir, intercepta todos los métodos
// @ApplicationScoped
@ServiceStereotype
@ProductoServicePrincipal
@TransactionalJpa
public class ProductoServiceImpl implements ProductoService{

    // Inyeccion por atributos
    @Inject
    @RepositoryJpa
    private Repository<Producto> repositoryJdbc;

    @Inject
    @RepositoryJpa
    private Repository<Categoria> repositoryCategoriaJdbc;

    /*
    public ProductoServiceJdbcImpl(Connection conn){
        this.repositoryJdbc = new ProductoRepositoryJdbcImpl(conn);
        this.repositoryCategoriaJdbc = new CategoriaRepositoryJdbcImpl(conn);
    }
     */
    @Override
    // @Logging // Método interceptado por LoggingInterceptor
    public List<Producto> listar() {
        try {
            return repositoryJdbc.listar();
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    // @Logging
    public Optional<Producto> porId(Long id) {
        try {
            return Optional.ofNullable(repositoryJdbc.porId(id));
            // ofNullable -> Si es nulo devuelve Optional.empty
            // -> si no es nulo, devuelve Optional<Producto>
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void guardar(Producto producto) {
        try {
            repositoryJdbc.guardar(producto);
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            repositoryJdbc.eliminar(id);
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Categoria> listarCategorias() {
        try {
            return repositoryCategoriaJdbc.listar();
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Categoria> porIdCategoria(Long id) {
        try {
            return Optional.ofNullable(repositoryCategoriaJdbc.porId(id));
        } catch (Exception e) {
            throw new ServiceJdbcException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> buscarProducto(String nombre) {
        return Optional.empty();
    }
}
