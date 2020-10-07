package com.kevingomez.springbootbackendapirest.models.DAO.Services;


import com.kevingomez.springbootbackendapirest.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteServiceInterface {
    List<Cliente> findAll();
    Page<Cliente> findAll(Pageable pageable);
    Cliente findById(int id);
    Cliente save (Cliente cliente);
    void delete(int id);

}
