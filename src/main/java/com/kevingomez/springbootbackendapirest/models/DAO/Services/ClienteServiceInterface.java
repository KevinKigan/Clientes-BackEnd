package com.kevingomez.springbootbackendapirest.models.DAO.Services;

import com.kevingomez.springbootbackendapirest.models.entity.Cliente;

import java.util.List;

public interface ClienteServiceInterface {
    List<Cliente> findAll();
    Cliente findById(int id);
    Cliente save (Cliente cliente);
    void delete(int id);

}
