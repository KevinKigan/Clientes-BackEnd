package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Producto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductoDAOInterface extends CrudRepository<Producto,Integer> {
//    @Query("select p from Producto p where p.productName like %?1%")
    // Busca por el nombre del producto que contenga la cadena term ignorando si es mayusculas o no
    List<Producto> findByProductNameContainingIgnoreCase(String term);
}
