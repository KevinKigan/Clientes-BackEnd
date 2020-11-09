package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

public interface FacturaDAOInterface extends CrudRepository<Factura, Integer> {

}
