package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDAOInterface extends CrudRepository<Cliente, Integer> {

}
