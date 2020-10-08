package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import com.kevingomez.springbootbackendapirest.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteDAOInterface extends JpaRepository<Cliente, Integer> {
    @Query("from Region") // No es el nombre de la tabla, sino el nombre de la clase Entity
    List<Region> findAllRegions();
}
