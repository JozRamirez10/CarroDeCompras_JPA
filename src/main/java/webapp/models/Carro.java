package webapp.models;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;
import webapp.configs.CarroStereotype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

// Al instanciar por inyeccion de dependencias se vuelve necesario tener un constructor
// vacío o sin parametros
// @SessionScoped // Contexto de sesión
// @Named("carro") // Si no se especifica el nombre, toma el nombre de la clase
// Las anotaciones anteriores se omiten por incluirse en el @Stereotype
@CarroStereotype
public class Carro implements Serializable { // Al usario SessionScoped se tiene que implementar la interfaz Serializable
    @Inject
    private transient Logger log; // No forma parte de serializable ni sessionScoped, por lo que se usa transient para poder
                                // ser usado

    private List<ItemCarro> items;
    /*
    public Carro() {
        this.items = new ArrayList<>();
    }
    */
    @PostConstruct // El método se ejcuta despues del constructor
    public void inicializar(){
        this.items = new ArrayList<>();
        log.info("Inicializando el carro de compras");
    }

    @PreDestroy
    public void destuir(){
        log.info("Destruyendo carro de compras");
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    public void addItemCarro(ItemCarro itemCarro){
        if(items.contains(itemCarro)){
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if(optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        }else{
            this.items.add(itemCarro);
        }
    }

    public int getTotal(){
        return items.stream().mapToInt(ItemCarro::getImporte).sum();
    }

    public void removeProductos(List<String> productosIds){
        if(productosIds != null){
            productosIds.forEach(this::removeProducto);
        }
    }

    public void removeProducto(String productoId){
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(itemCarro -> items.remove(itemCarro));
    }

    private Optional<ItemCarro> findProducto(String productoId){
        return items.stream()
            .filter(itemCarro -> productoId.equals(Long.toString(itemCarro.getProducto().getId())))
            .findAny();
    }

    public void updateCantidad(String productoId, int cantidad){
        Optional<ItemCarro> producto = findProducto(productoId);
        producto.ifPresent(itemCarro -> itemCarro.setCantidad(cantidad));
    }
}
