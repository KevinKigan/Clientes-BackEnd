package com.kevingomez.springbootbackendapirest.models.DAO;

import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDAOInterface extends JpaRepository<Cliente, Integer> {

}
