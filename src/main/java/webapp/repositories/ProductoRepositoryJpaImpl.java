package webapp.repositories;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import webapp.configs.RepositoryStereotype;
import webapp.models.entities.Producto;

import java.util.List;

@RepositoryJpa
@RepositoryStereotype
public class ProductoRepositoryJpaImpl implements Repository<Producto>{
    @Inject
    private EntityManager em;

    @Override
    public List<Producto> listar() throws Exception {
        return em.createQuery("select p from Producto p left outer join fetch p.categoria", Producto.class).getResultList();
    }

    @Override
    public Producto porId(Long id) throws Exception {
        return em.find(Producto.class, id);
    }

    @Override
    public void guardar(Producto producto) throws Exception {
        if(producto.getId() != null && producto.getId() > 0){
            em.merge(producto);
        }else{
            em.persist(producto);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        em.remove(porId(id));
    }
}
